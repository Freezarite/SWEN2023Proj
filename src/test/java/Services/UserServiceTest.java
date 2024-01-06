package Services;

import baseClasses.DB.UserDBHandler;
import baseClasses.HTTP.HTTPRequest;
import baseClasses.HTTP.HTTPResponse;
import baseClasses.HTTP.HTTPStatusCode;
import baseClasses.Server.SessionHandler;
import baseClasses.Services.UserService;
import baseClasses.User.UserCredentials;
import baseClasses.User.UserStats;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.sql.SQLException;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    @Mock
    private SessionHandler sessionMock;

    @Mock
    private UserDBHandler sessionUserDB;

    @InjectMocks
    private UserService userService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void TestCorrectUserLogin() throws SQLException, IOException {
        String inputData = "POST /sessions HTTP/1.1\n" +
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

        UUID testId = UUID.randomUUID();
        HTTPRequest testRequest = new HTTPRequest(new BufferedReader(new StringReader(inputData)));

        mockStatic(SessionHandler.class);
        when(SessionHandler.getInstance()).thenReturn(sessionMock);

        when(sessionMock.login(new ObjectMapper().readValue(testRequest.getBody(), UserCredentials.class))).thenReturn(testId);

        HTTPResponse testResponse = userService.handleRequest(testRequest);
        assertEquals(HTTPStatusCode.OK, testResponse.getHttpStatusCode());
        assertEquals(testId.toString(), testResponse.getContent().replaceAll("\"", ""));
    }
}
