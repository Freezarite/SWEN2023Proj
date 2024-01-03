package baseClasses.Services;

import baseClasses.HTTP.HTTPRequest;
import baseClasses.HTTP.HTTPResponse;

public interface Service {

    HTTPResponse handleRequest(HTTPRequest request);
}
