package functional

import com.google.gson.JsonParser
import com.logbook.koin_modules.stubRepositories
import com.logbook.ktor_modules.pilots
import com.logbook.model.Flights
import com.logbook.repositories.StubFlightsRepository
import io.ktor.application.Application
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.withCharset
import io.ktor.server.testing.contentType
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.setBody
import io.ktor.server.testing.withTestApplication
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.StandAloneContext.stopKoin
import org.koin.standalone.inject
import org.koin.test.KoinTest
import org.skyscreamer.jsonassert.JSONAssert
import test_helpers.createFlight
import java.nio.charset.Charset
import java.time.Instant

class FlightsTest : KoinTest {
    val stubFlightsRepository: StubFlightsRepository by inject()

    @Before
    fun setup() {
        startKoin(listOf(stubRepositories))
        stubFlightsRepository.clear()
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `should return empty list of flights`() = withTestApplication(Application::pilots) {
        with(handleRequest(HttpMethod.Get, "/pilots/12345/flights")) {
            val expectedResponse = "{ flights: [] }"

            assertEquals(HttpStatusCode.OK, response.status())
            JSONAssert.assertEquals(expectedResponse, response.content, true)
            assertEquals(ContentType.Application.Json.withCharset(Charset.forName("UTF-8")), response.contentType())
        }
    }

    @Test
    fun `should return all flights for a pilot`() = withTestApplication(Application::pilots) {
        //given
        val flight1 = createFlight(id = "abcde", pilotId = "12345", departureInstant = Instant.parse("2018-01-01T00:00:00Z"))
        val flight2 = createFlight(id = "defghi", pilotId = "12345", departureInstant = Instant.parse("2018-01-01T19:00:00Z"))
        val flight3 = createFlight(id = "hijkl", pilotId = "67890")
        stubFlightsRepository.flights = Flights(listOf(flight1, flight2, flight3))

        //when
        with(handleRequest(HttpMethod.Get, "/pilots/12345/flights")) {
            //then
            val expectedResponse = """{ flights: [{id: "abcde", pilotId: "12345", departureInstant: "2018-01-01T00:00:00Z"}, {id: "defghi", pilotId: "12345", departureInstant: "2018-01-01T19:00:00Z"}] }"""

            assertEquals(HttpStatusCode.OK, response.status())
            JSONAssert.assertEquals(expectedResponse, response.content, true)
            assertEquals(ContentType.Application.Json.withCharset(Charset.forName("UTF-8")), response.contentType())
        }
    }

    @Test
    fun `should store a new flight in the repository and allocate an id`(): Unit = withTestApplication(Application::pilots) {
        //when
        handleRequest(HttpMethod.Post, "/pilots/12345/flights") {
            addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            setBody("""{departureInstant: "2018-01-01T14:00:00Z"} """)
        }.apply {
            //then
            assertEquals(HttpStatusCode.Created, response.status())

            val idFromResponse = JsonParser().parse(response.content).asJsonObject["id"].asString

            assertEquals(36, idFromResponse.length)
            assertEquals(idFromResponse, stubFlightsRepository.flights.flights[0].id)
            assertEquals("12345", stubFlightsRepository.flights.flights[0].pilotId)
            assertEquals(Instant.parse("2018-01-01T14:00:00Z"), stubFlightsRepository.flights.flights[0].departureInstant)
        }
    }
}