package project.controller;

import project.http.HttpRequest;
import project.http.HttpResponse;
import project.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class StaticHtmlController implements Controller {

    private static final Logger logger = LoggerFactory.getLogger(StaticHtmlController.class);

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
                .header("Content-Type", "text/html;charset=utf-8")
                .header("Content-Length", String.valueOf(data.length))
                .body(data)
                .build();
    }
}
