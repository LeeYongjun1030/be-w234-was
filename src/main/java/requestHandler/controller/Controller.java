package requestHandler.controller;

import http.HttpRequest;

import java.io.IOException;

public interface Controller {

    byte[] process(HttpRequest httpRequest) throws IOException;
}
