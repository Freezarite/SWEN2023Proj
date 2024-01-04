package baseClasses.HTTP;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class HTTPResponse {

    private final HTTPStatusCode httpStatusCode;

    private final String contentType;

    private final String content;

    public HTTPResponse(HTTPStatusCode httpStatusCode, String contentType, String content) {
        this.httpStatusCode = httpStatusCode;
        this.contentType = contentType;
        this.content = content;
    }

    public HTTPResponse(HTTPStatusCode httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
        if(httpStatusCode == HTTPStatusCode.NO_CONTENT) {
            this.content = null;
            this.contentType = null;
            return ;
        }
        this.content = "";
        this.contentType = "";
    }

    public String getRequest() {

        String localDatetime = DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now(ZoneId.of("UTC")));
        return "HTTP/1.1 " + this.httpStatusCode.getCode() + " " + this.httpStatusCode.getCode() + "\r\n" +
                "Connection: close\r\n" +
                "Date: " + localDatetime + "\r\n" +
                "Content-Type: " + this.contentType + "\r\n" +
                "Content-Length: " + this.content.length() + "\r\n" +
                "\r\n" +
                this.content;
    }
}
