package controller;

import http.HttpRequest;
import http.HttpResponse;
import http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class StyleSheetController implements Controller {

    private static final Logger logger = LoggerFactory.getLogger(StyleSheetController.class);
    @Override
    public HttpResponse process(HttpRequest httpRequest) {
        try {
            return createHttpResponse(httpRequest);
        } catch (IOException e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    private HttpResponse createHttpResponse(HttpRequest httpRequest) throws IOException {
        byte[] data = Files.readAllBytes(new File("./webapp" + httpRequest.getPath()).toPath());

        return new HttpResponse.Builder(HttpStatus.SUCCESSFUL)
                .header("Content-Type", "text/css")
                .header("Content-Length", String.valueOf(data.length))
                .body(data)
                .build();
    }

}
