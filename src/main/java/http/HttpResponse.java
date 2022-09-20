package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
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
        if (body != null) {
            logger.debug("Body : {}", new String(body));
        }
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

    public ArrayList<byte[]> convertToHttpResponseMessage(){
        ArrayList<byte[]> httpMessage = new ArrayList<>();
        addStartLine(httpMessage);
        addHeaders(httpMessage);
        addBody(httpMessage);
        return httpMessage;
    }

    private void addStartLine(ArrayList<byte[]> httpMessage) {
        String startLine = String.format("HTTP/1.1 %s %s\r\n", httpStatus.getCode(), httpStatus.getReasonPhrase());
        httpMessage.add(startLine.getBytes());
    }

    private void addHeaders(ArrayList<byte[]> httpMessage) {
        headers.forEach((k, v) -> httpMessage.add(String.format("%s: %s\r\n", k, v).getBytes())); //headers
        httpMessage.add("\r\n".getBytes()); //empty line
    }

    private void addBody(ArrayList<byte[]> httpMessage) {
        httpMessage.add(body);
    }

}
