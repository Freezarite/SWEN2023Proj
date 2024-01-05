package baseClasses.Services;

import baseClasses.Card.Card;
import baseClasses.Card.CardData;
import baseClasses.DB.CardDBHandler;
import baseClasses.DB.UserDBHandler;
import baseClasses.HTTP.HTTPMethod;
import baseClasses.HTTP.HTTPRequest;
import baseClasses.HTTP.HTTPResponse;
import baseClasses.HTTP.HTTPStatusCode;
import baseClasses.Server.SessionHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.UUID;

public class CardService implements Service{

    @Override
    public HTTPResponse handleRequest(HTTPRequest request) {
        CardDBHandler cardDBHandler = new CardDBHandler();
        UserDBHandler userDBHandler = new UserDBHandler();
        try {
            if(request.getPath().split("/")[1].equals("cards") && request.getMethod().equals(HTTPMethod.GET)) {
                if (request.getHTTPHeaders("Authorization") == null)
                    return new HTTPResponse(HTTPStatusCode.UNAUTHORIZED);

                List<CardData> output = userDBHandler.getAllCardsFromUser(SessionHandler.getInstance().
                        getUserFromSession(UUID.fromString(request.getHTTPHeaders("Authorization")
                                .replaceFirst("^Bearer ", ""))));

                if(output.isEmpty())
                    return new HTTPResponse(HTTPStatusCode.NO_CONTENT);

                return new HTTPResponse(HTTPStatusCode.OK, "application/json", new ObjectMapper().writeValueAsString(output));
            }

            if(request.getPath().split("/")[1].equals("deck")) {
                return switch (request.getMethod()) {
                    case GET -> new HTTPResponse(HTTPStatusCode.NOT_IMPLEMENTED);
                    case PUT -> new HTTPResponse(HTTPStatusCode.NOT_IMPLEMENTED);
                    default -> new HTTPResponse(HTTPStatusCode.NOT_FOUND);
                };
            }

            return new HTTPResponse(HTTPStatusCode.NOT_FOUND);

        } catch (ArrayIndexOutOfBoundsException e) {
            return new HTTPResponse(HTTPStatusCode.BAD_REQUEST);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
