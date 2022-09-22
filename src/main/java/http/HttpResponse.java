package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    public byte[] convertToHttpResponseMessage(){

        byte[] startLine = createStartLine();
        byte[] headers = createHeaders();

        int totalLengthOfMsg = startLine.length + headers.length;
        totalLengthOfMsg += (body != null) ? body.length : 0;

        byte[] httpRespMessage = new byte[totalLengthOfMsg];
        System.arraycopy(startLine, 0, httpRespMessage, 0, startLine.length);
        System.arraycopy(headers, 0, httpRespMessage, startLine.length, headers.length);
        if (body != null) {
            System.arraycopy(body, 0, httpRespMessage, startLine.length + headers.length, body.length);
        }
        return httpRespMessage;
    }

    private byte[] createStartLine() {
        return new StringBuilder()
                .append("HTTP/1.1 ")
                .append(httpStatus.getCode())
                .append(" ")
                .append(httpStatus.getReasonPhrase())
                .append("\r\n")
                .toString().getBytes();
    }

    private byte[] createHeaders() {
        StringBuilder sb = new StringBuilder();
        headers.forEach((k, v) -> sb.append(k).append(": ").append(v).append("\r\n"));
        sb.append("\r\n");
        return sb.toString().getBytes();
    }

}
