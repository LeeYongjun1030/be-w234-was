package requestHandler.controller;

import http.HttpRequest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class StaticHtmlController implements Controller {

    @Override
    public byte[] process(HttpRequest httpRequest) throws IOException {
        return Files.readAllBytes(new File("./webapp" + httpRequest.getPath()).toPath());
    }
}
