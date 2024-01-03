package baseClasses.HTTP;

import baseClasses.Services.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class HTTPRequestHandler implements Runnable {

    private final Socket serviceSocket;

    private final Router router;

    public HTTPRequestHandler(Socket serviceSocket, Router router) {
        this.serviceSocket = serviceSocket;
        this.router = router;
    }

    @Override
    public void run() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(serviceSocket.getInputStream()));

            System.out.println("creating Request!");
            HTTPRequest request = new HTTPRequest(bufferedReader);

            HTTPResponse response;
            responseBuilder: {
                if(request.getMethod() == null)
                    return;

                System.out.println("creating Service!");
                Service service = router.resolveRoute(request.getMethod(), request.getPath());

                if(service == null) {
                    System.out.println("Service does not exist!");
                    response = new HTTPResponse(HTTPStatusCode.NOT_FOUND, "text/plain", "Service not found!");
                    break responseBuilder;
                }

                System.out.println("creating Response!");
                response = service.handleRequest(request);
            }

            OutputStream outputStream = this.serviceSocket.getOutputStream();
            outputStream.write(response.getRequest().getBytes());
            outputStream.flush();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
