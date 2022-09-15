package requestHandler.controller;

import webserver.ProcessedRequest;

import java.io.IOException;

public interface Controller {

    byte[] process(ProcessedRequest processedRequest) throws IOException;
}
