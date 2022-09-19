package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class HttpResponse {

    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private HttpStatus httpStatus;

    private Map<String, String> headers;

    private byte[] body;

    public HttpResponse(HttpStatus httpStatus, Map<String, String> headers, byte[] body) {
        this.httpStatus = httpStatus;
        this.headers = headers;
        this.body = body;

        logger.debug("Http Status : {} {}", httpStatus.getCode(), httpStatus.getReasonPhrase());
        logger.debug("Headers : {}", headers);
        logger.debug("Body : {}", new String(body));
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public byte[] getBody() {
        return body;
    }

    public ArrayList<byte[]> toHttpResponseMessage(){
        ArrayList<byte[]> httpMessage = new ArrayList<>();
        String startLine = "HTTP/1.1 " + httpStatus.getCode() + " " + httpStatus.getReasonPhrase() + "\r\n";
        httpMessage.add(startLine.getBytes());
        headers.forEach((k, v) -> httpMessage.add((k + ": " + v + "\r\n").getBytes()));
        httpMessage.add("\r\n".getBytes());
        httpMessage.add(body);
        return httpMessage;
    }
}
