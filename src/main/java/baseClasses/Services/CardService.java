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
import baseClasses.User.UserData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;

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
                    case PUT -> {

                        if (request.getHTTPHeaders("Authorization") == null)
                            yield new HTTPResponse(HTTPStatusCode.UNAUTHORIZED);

                        ObjectMapper objectMapper = new ObjectMapper();
                        JsonNode jsonNode = objectMapper.readTree(request.getBody());

                        List<UUID> cardList = new ArrayList<>();

                        if (jsonNode.isArray()) {
                            Iterator<JsonNode> elements = jsonNode.elements();
                            while (elements.hasNext()) {
                                JsonNode element = elements.next();
                                if (element.isTextual()) {
                                    UUID uuid = UUID.fromString(element.textValue());
                                    cardList.add(uuid);
                                }
                            }
                        }

                        if (cardList.size() != 4)
                            yield new HTTPResponse(HTTPStatusCode.BAD_REQUEST);

                        System.out.println(cardList);

                        if(userDBHandler.checkIfCardsBelongToUser(cardList, SessionHandler.getInstance().
                                getUserFromSession(UUID.fromString(request.getHTTPHeaders("Authorization")
                                        .replaceFirst("^Bearer ", "")))))
                            yield new HTTPResponse(HTTPStatusCode.FORBIDDEN);

                        System.out.println("far");

                        userDBHandler.updateStackOfUser(cardList, SessionHandler.getInstance().
                                getUserFromSession(UUID.fromString(request.getHTTPHeaders("Authorization")
                                        .replaceFirst("^Bearer ", ""))));

                        yield new HTTPResponse(HTTPStatusCode.OK);

                    }
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
