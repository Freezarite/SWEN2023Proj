package baseClasses.Services;

import baseClasses.Battle.BattleHandler;
import baseClasses.DB.UserDBHandler;
import baseClasses.HTTP.*;
import baseClasses.Server.SessionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.UUID;

public class BattleService implements Service{
    @Override
    public HTTPResponse handleRequest(HTTPRequest request) {

        try{
            if(request.getPath().split("/")[1].equals("battles") && request.getMethod().equals(HTTPMethod.POST)) {
                if (request.getHTTPHeaders("Authorization") == null)
                    return new HTTPResponse(HTTPStatusCode.UNAUTHORIZED);

                String log = BattleHandler.getInstance().createOrJoinBattle(SessionHandler.getInstance().
                        getUserFromSession(UUID.fromString(request.getHTTPHeaders("Authorization")
                                .replaceFirst("^Bearer ", ""))));

                return new HTTPResponse(HTTPStatusCode.OK, "", log);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return new HTTPResponse(HTTPStatusCode.NOT_IMPLEMENTED, "application/json", "");
    }
}
