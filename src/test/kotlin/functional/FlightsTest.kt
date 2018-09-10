package functional

import com.google.gson.JsonParser
import com.logbook.koin_modules.stubRepositories
import com.logbook.model.Flight
import com.logbook.model.Flights
import com.logbook.repositories.StubFlightsRepository
import com.logbook.root
import io.ktor.application.Application
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.withCharset
import io.ktor.server.testing.contentType
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.standalone.StandAloneContext.closeKoin
import org.koin.standalone.StandAloneContext.startKoin
import org.skyscreamer.jsonassert.JSONAssert
import java.nio.charset.Charset

class FlightsTest {
    @Before
    fun setup() {
        startKoin(listOf(stubRepositories))
        StubFlightsRepository.clear()
    }

    @After
    fun tearDown() {
        closeKoin()
    }

    @Test
    fun `should return empty list of flights`() = withTestApplication(Application::root) {
        with(handleRequest(HttpMethod.Get, "/pilots/12345/flights")) {
            val expectedResponse = "{ flights: [] }"

            assertEquals(HttpStatusCode.OK, response.status())
            JSONAssert.assertEquals(expectedResponse, response.content, true)
            assertEquals(ContentType.Application.Json.withCharset(Charset.forName("UTF-8")), response.contentType())
        }
    }

    @Test
    fun `should return all flights for a pilot`() = withTestApplication(Application::root) {
        //given
        StubFlightsRepository.flights = Flights(listOf(Flight("abcde"), Flight("defghi")))

        //when
        with(handleRequest(HttpMethod.Get, "/pilots/12345/flights")) {
            //then
            val expectedResponse = """{ flights: [{id: "abcde"}, {id: "defghi"}] }"""

            assertEquals(HttpStatusCode.OK, response.status())
            JSONAssert.assertEquals(expectedResponse, response.content, true)
            assertEquals(ContentType.Application.Json.withCharset(Charset.forName("UTF-8")), response.contentType())
        }
    }

    @Test
    fun `should store a new flight in the repository and allocate an id`(): Unit = withTestApplication(Application::root) {
        handleRequest(HttpMethod.Post, "/pilots/12345/flights") {

        }.apply {
            assertEquals(HttpStatusCode.Created, response.status())

            val idFromResponse = JsonParser().parse(response.content).asJsonObject["id"].asString
            assertEquals(36, idFromResponse.length)

            assertEquals(idFromResponse, StubFlightsRepository.flights.flights[0].id)
        }
    }


}