package HTTP;

import baseClasses.HTTP.HTTPRequest;
import baseClasses.HTTP.HTTPResponse;
import baseClasses.HTTP.HTTPStatusCode;
import org.junit.Test;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertTrue;

public class HTTPResponseTest {

    String validHttpResponsePattern = "HTTP/1.1 \\d{3} .+\\r\\n" +
            "Connection: close\\r\\n" +
            "Date: .+\\r\\n" +
            "Content-Type: .*\\r\\n" +
            "Content-Length: \\d+\\r\\n" +
            "\\r\\n" +
            ".*";

    Pattern pattern = Pattern.compile(validHttpResponsePattern, Pattern.DOTALL);

    @Test
    public void checkForCorrectResponseWithNoContent() {
        HTTPResponse testResponse = new HTTPResponse(HTTPStatusCode.I_AM_A_TEAPOT);

        Matcher matcher = pattern.matcher(testResponse.getRequest());

        assertTrue(matcher.matches());
    }

    @Test
    public void checkForCorrectResponseWithContent() {
        HTTPResponse testResponse = new HTTPResponse(HTTPStatusCode.I_AM_A_TEAPOT, "application/json", "You cannot make coffee with a teapot!");

        Matcher matcher = pattern.matcher(testResponse.getRequest());

        assertTrue(matcher.matches());
    }
}
