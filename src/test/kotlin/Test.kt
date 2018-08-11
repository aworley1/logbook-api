import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertFalse
import org.junit.Test

//class ApplicationTest {
//    @Test
//    fun testRequest() = withTestApplication(Main::main) {
//        with(handleRequest(HttpMethod.Get, "/")) {
//            assertEquals(HttpStatusCode.OK, response.status())
//            assertEquals("Test String", response.content)
//        }
//        with(handleRequest(HttpMethod.Get, "/index.html")) {
//            assertFalse(requestHandled)
//        }
//    }
//}