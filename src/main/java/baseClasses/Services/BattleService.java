package baseClasses.Services;

import baseClasses.HTTP.HTTPRequest;
import baseClasses.HTTP.HTTPRequestHandler;
import baseClasses.HTTP.HTTPResponse;
import baseClasses.HTTP.HTTPStatusCode;

public class BattleService implements Service{
    @Override
    public HTTPResponse handleRequest(HTTPRequest request) {
        return new HTTPResponse(HTTPStatusCode.OK, "application/json", "");
    }
}
