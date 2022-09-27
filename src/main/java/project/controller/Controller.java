package project.controller;

import project.http.HttpRequest;
import project.http.HttpResponse;

public interface Controller {

    HttpResponse process(HttpRequest httpRequest);
}
