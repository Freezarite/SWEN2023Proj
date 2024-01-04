package baseClasses.Services;

import baseClasses.Card.Card;
import baseClasses.DB.CardDBHandler;
import baseClasses.HTTP.HTTPMethod;
import baseClasses.HTTP.HTTPRequest;
import baseClasses.HTTP.HTTPResponse;
import baseClasses.HTTP.HTTPStatusCode;

public class CardService implements Service{

    @Override
    public HTTPResponse handleRequest(HTTPRequest request) {
        try {
            if(request.getPath().split("/")[1].equals("cards") && request.getMethod().equals(HTTPMethod.POST)) {
                CardDBHandler cardDBHandler = new CardDBHandler();

            }

            if(request.getPath().split("/")[1].equals("deck")) {
                return switch (request.getMethod()) {
                    case GET -> new HTTPResponse(HTTPStatusCode.NOT_IMPLEMENTED);
                    case PUT -> new HTTPResponse(HTTPStatusCode.NOT_IMPLEMENTED); // String[] array = new ObjectMapper().readValue(request.getBody(), String[].class)
                    default -> new HTTPResponse(HTTPStatusCode.NOT_FOUND);
                };
            }

            return new HTTPResponse(HTTPStatusCode.NOT_FOUND);

        } catch (ArrayIndexOutOfBoundsException e) {
            return new HTTPResponse(HTTPStatusCode.BAD_REQUEST);
        }
    }
}
