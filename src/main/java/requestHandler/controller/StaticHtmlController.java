package requestHandler.controller;

import webserver.ProcessedRequest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class StaticHtmlController implements Controller {

    @Override
    public byte[] process(ProcessedRequest processedRequest) throws IOException {
        return Files.readAllBytes(new File("./webapp" + processedRequest.getPath()).toPath());
    }
}
