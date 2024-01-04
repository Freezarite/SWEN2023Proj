package baseClasses.Server;

import baseClasses.HTTP.HTTPRequestHandler;
import baseClasses.HTTP.Router;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private final int port;
    private final int threads;
    private final Router router;
    private volatile boolean isRunning;

    public Server(final int port, final int threads, final Router router) {
        this.port = port;
        this.threads = threads;
        this.router = router;
        this.isRunning = true;
    }

    public void start() {
        try(ServerSocket listener = new ServerSocket(this.port))  {

            ExecutorService executorService = Executors.newFixedThreadPool(this.threads);

            System.out.println("HTTP Server is up and running! Accessible on: http://localhost:" + this.port);

            while (true) {
                final Socket serviceSocket = listener.accept();
                final HTTPRequestHandler requestHandler = new HTTPRequestHandler(serviceSocket, this.router);

                System.out.println("Received request");
                executorService.submit(requestHandler);
            }

        } catch (IOException e) {
            System.out.println("Could not create socket");
            e.printStackTrace(); // Consider using a logging framework for production
        }
    }
}