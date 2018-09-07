package functional

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
import org.junit.Test
import org.skyscreamer.jsonassert.JSONAssert
import java.nio.charset.Charset

class FlightsTest {
    @Test
    fun `should return empty list of flights`() = withTestApplication(Application::root) {
        with(handleRequest(HttpMethod.Get, "/pilot/12345/flights")) {
            val expectedResponse = "{ flights: [] }"

            assertEquals(HttpStatusCode.OK, response.status())
            JSONAssert.assertEquals(expectedResponse, response.content, true)
            assertEquals(ContentType.Application.Json.withCharset(Charset.forName("UTF-8")), response.contentType())
        }
    }
}