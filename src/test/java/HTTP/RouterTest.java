package HTTP;

import baseClasses.HTTP.HTTPMethod;
import baseClasses.HTTP.Router;
import baseClasses.Services.Service;
import baseClasses.Services.UserService;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class RouterTest {
    @Test
    public void testRouting() {
        String testRoute = "/testing";

        Service testService1 = Mockito.mock(Service.class);
        Service testService2 = Mockito.mock(Service.class);

        Router router = new Router();

        router.addRoute(HTTPMethod.GET, "/testing", testService1, new int[]{});
        router.addRoute(HTTPMethod.GET, "/wrongroute", testService2, new int[]{});

        Service result = router.resolveRoute(HTTPMethod.GET, testRoute);

        verify(testService1).equals(result);
    }

    @Test
    public void testRoutingWithParameters() {
        String testString1 = "/testing/testUsersldkfjlskdlf";
        String testString2 = "/testing";
        Service mockService = mock(Service.class);

        Router router = new Router();

        router.addRoute(HTTPMethod.GET, "/testing/{username}", mockService, new int[]{2});

        assertEquals((router.resolveRoute(HTTPMethod.GET, testString1)), mockService);
        verify(mockService, Mockito.never()).equals(router.resolveRoute(HTTPMethod.GET, testString2));
    }
}
