package controller;

import http.HttpRequest;
import http.HttpResponse;
import http.HttpStatus;
import http.HttpStatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class StyleSheetController implements Controller {

    private static final Logger logger = LoggerFactory.getLogger(StyleSheetController.class);
    @Override
    public HttpResponse process(HttpRequest httpRequest) {
        try {
            return getHttpResponse(httpRequest);
        } catch (IOException e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    private HttpResponse getHttpResponse(HttpRequest httpRequest) throws IOException {
        HttpStatus httpStatus = new HttpStatus(HttpStatusCode.SUCCESSFUL, "OK");
        byte[] body = Files.readAllBytes(new File("./webapp/css/styles.css").toPath());
        Map<String, String> headers = createHeaders(body);
        return new HttpResponse(httpStatus, headers, body);
    }

    private Map<String, String> createHeaders(byte[] body) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "text/css;charset=utf-8");
        headers.put("Content-Length", String.valueOf(body.length));
        return headers;
    }
}
