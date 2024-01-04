package baseClasses.HTTP;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class HTTPRequest {

    private final HTTPMethod method;

    private final String path;

    private final String version;

    private final Map<String, String> HTTPHeaders = new HashMap<>();

    private final String body;

    public HTTPRequest(BufferedReader buffer) throws IOException {

        String line = buffer.readLine();

        if (line != null) {
            String[] requestLine = line.split(" ");
            this.method = HTTPMethod.strToMethod(requestLine[0]);
            this.path = requestLine[1];
            this.version = requestLine[2];

            for (line = buffer.readLine(); !line.isEmpty(); line = buffer.readLine()) {
                String[] headerEntry = line.split(": ", 2);
                this.HTTPHeaders.put(headerEntry[0], headerEntry[1]);
            }

            int contentLength = this.HTTPHeaders.containsKey("Content-Length") ? Integer.parseInt(this.HTTPHeaders.get("Content-Length")) : 0;
            char[] charBuffer = new char[contentLength];
            this.body = buffer.read(charBuffer, 0, contentLength) > 0 ? new String(charBuffer) : null;
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
