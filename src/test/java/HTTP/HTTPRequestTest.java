package HTTP;

import baseClasses.HTTP.HTTPMethod;
import baseClasses.HTTP.HTTPRequest;
import baseClasses.HTTP.HTTPResponse;
import baseClasses.HTTP.HTTPStatusCode;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class HTTPRequestTest {
    @Test
    public void testCorrectParsing() throws IOException {
        String inputStream = "POST /sessions HTTP/1.1\n" +
                "Content-Type: application/json\n" +
                "User-Agent: PostmanRuntime/7.36.0\n" +
                "Accept: */*\n" +
                "Postman-Token: 7d5349ac-338d-4a07-b348-01fb57e112ed\n" +
                "Host: localhost:10001\n" +
                "Accept-Encoding: gzip, deflate, br\n" +
                "Connection: keep-alive\n" +
                "Content-Length: 54\n" +
                "\n{" +
                "\n    \"username\": \"admin\"," +
                "\n    \"password\": \"test\"" +
                "\n}";

        HTTPRequest testRequest = new HTTPRequest(new BufferedReader(new StringReader(inputStream)));

        assertEquals(testRequest.getMethod(), HTTPMethod.POST);
        assertEquals("\n{\n    \"username\": \"admin\",\n    \"password\": \"test\"\n}".trim(), testRequest.getBody().trim());
        assertEquals("HTTP/1.1" ,testRequest.getVersion());
        assertEquals("/sessions",testRequest.getPath());
    }

    @Test
    public void testEmptyInput() throws IOException {
        String inputStream = "";

        HTTPRequest testRequest = new HTTPRequest(new BufferedReader(new StringReader(inputStream)));

        assertNull(testRequest.getPath());
        assertNull(testRequest.getBody());
        assertNull(testRequest.getVersion());
        assertNull(testRequest.getMethod());
    }
}
