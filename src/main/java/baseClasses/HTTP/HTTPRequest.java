package baseClasses.HTTP;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class HTTPRequest {

    private final HTTPMethod method;

    private final String path;

    private final String version;

    private final Map<String, String> HTTPHeaders = new HashMap();

    private final String body;

    public HTTPRequest(BufferedReader buffer) throws IOException {

        String line = buffer.readLine();

        if (line != null) {
            String[] requestLine = line.split(" ");
            this.method = HTTPMethod.strToMethod(requestLine[0]);
            this.path = requestLine[1];
            this.version = requestLine[2];

            buffer.lines()
                    .takeWhile(lines -> !lines.isEmpty()) // does this till lines is empty
                    .map(lines -> lines.split(": ", 2)) // transforms each lines into an array of entries
                    .forEach(headerEntry -> this.HTTPHeaders.put(headerEntry[0], headerEntry[1])); // adds each entry to the map

            if(this.HTTPHeaders.containsKey("Content-Length"))
                this.body = new String(new char[Integer.parseInt(this.HTTPHeaders.get("Content-Length"))]);
            else
                this.body = null;

            return;
        }

        this.method = null;
        this.body = null;
        this.version = null;
        this.path = null;
    }

    public HTTPMethod getMethod() {
        return this.method;
    }

    public String getBody() {
        return this.body;
    }

    public String getPath() {
        return this.path;
    }

    public String getVersion() {
        return this.version;
    }

    public String getHTTPHeaders(String specificHeader) {
        return this.HTTPHeaders.get(specificHeader);
    }
}
