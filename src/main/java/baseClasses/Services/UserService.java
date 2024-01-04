package baseClasses.Services;

import baseClasses.DB.UserDBHandler;
import baseClasses.HTTP.HTTPMethod;
import baseClasses.HTTP.HTTPRequest;
import baseClasses.HTTP.HTTPResponse;
import baseClasses.HTTP.HTTPStatusCode;
import baseClasses.Server.SessionHandler;
import baseClasses.User.User;
import baseClasses.User.UserCredentials;
import baseClasses.User.UserData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.SQLException;
import java.util.UUID;

public class UserService implements Service{
    @Override
    public HTTPResponse handleRequest(HTTPRequest request) {
        UserDBHandler userDBHandler = new UserDBHandler();
        try {
            if (request.getPath().split("/")[1].equals("sessions") && request.getMethod() == HTTPMethod.POST) {
                // login
                //System.out.println(request.getBody());
                UUID uuid = SessionHandler.getInstance().login(new ObjectMapper().readValue(request.getBody(), UserCredentials.class));
                return uuid != null ?
                        new HTTPResponse(HTTPStatusCode.OK, "application/json", uuid.toString()) :
                        new HTTPResponse(HTTPStatusCode.UNAUTHORIZED);
            }

            if (request.getPath().split("/")[1].equals("users")) {
                System.out.println(request.getMethod());
                return switch (request.getMethod()) {
                    case GET -> {
                        String username = request.getPath().split("/")[2];
                        if (request.getHTTPHeaders("Authorization") == null ||  !SessionHandler.getInstance().verifySession(UUID.fromString(request.getHTTPHeaders("Authorization").replaceFirst("^Bearer ", "")), username, true))
                            yield new HTTPResponse(HTTPStatusCode.UNAUTHORIZED);
                        UserData userData = userDBHandler.getUserDataFromDB(username);
                        if (userData.equals(null))
                            yield new HTTPResponse(HTTPStatusCode.NOT_FOUND);

                        yield new HTTPResponse(HTTPStatusCode.OK, "application/json", new ObjectMapper().writeValueAsString(userData));
                    }

                    //register new user
                    case POST -> {
                        UserCredentials userCredentials = new ObjectMapper().readValue(request.getBody(), UserCredentials.class);
                        boolean success = userDBHandler.createUser(userCredentials.username(), userCredentials.password());

                        if(!success)
                            yield new HTTPResponse(HTTPStatusCode.CONFLICT);

                        yield new HTTPResponse(HTTPStatusCode.OK);
                    }

                    //update user
                    case PUT -> {
                        //System.out.println("Made it here");
                        String username = request.getPath().split("/")[2];
                        UserData userData = new ObjectMapper().readValue(request.getBody(), UserData.class);

                        //System.out.println("Yeet " + userData + username);

                        if (request.getHTTPHeaders("Authorization") == null ||  !SessionHandler.getInstance().verifySession(UUID.fromString(request.getHTTPHeaders("Authorization").replaceFirst("^Bearer ", "")), username, true))
                            yield new HTTPResponse(HTTPStatusCode.UNAUTHORIZED);
                        System.out.println(username);
                        userDBHandler.updateUserDataOfSpecificUser(username, userData);

                        yield new HTTPResponse(HTTPStatusCode.OK);
                    }

                    default -> new HTTPResponse(HTTPStatusCode.NOT_FOUND);

                    };
                }

            return new HTTPResponse(HTTPStatusCode.NOT_FOUND);
        }
        catch (ArrayIndexOutOfBoundsException e) {
            return new HTTPResponse(HTTPStatusCode.BAD_REQUEST);
        }
        catch (SQLException | JsonProcessingException e) {
            System.out.println(e.getMessage());
            return new HTTPResponse(HTTPStatusCode.INTERNAL_SERVER_ERROR);
        }
    }
}
