package controller;


import http.HttpResponse;
import http.HttpStatus;
import http.HttpStatusCode;
import model.User;
import http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class SignUpController implements Controller {

    private static final Logger logger = LoggerFactory.getLogger(SignUpController.class);

    @Override
    public HttpResponse process(HttpRequest httpRequest) {
        User user = createUser(httpRequest.getParams());
        return getHttpResponse(user);
    }

    private User createUser(Map<String, String> params) {
        String userId = params.get("userId");
        String password = params.get("password");
        String name = params.get("name");
        String email = params.get("email");
        return new User(userId, password, name, email);
    }

    private HttpResponse getHttpResponse(User user) {
        HttpStatus httpStatus = new HttpStatus(HttpStatusCode.SUCCESSFUL, "OK");
        Map<String, String> headers = createHeaders(user.toString().getBytes());
        return new HttpResponse(httpStatus, headers, user.toString().getBytes());
    }

    private Map<String, String> createHeaders(byte[] body) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "text/html;charset=utf-8");
        headers.put("Content-Length", String.valueOf(body.length));
        return headers;
    }
}
