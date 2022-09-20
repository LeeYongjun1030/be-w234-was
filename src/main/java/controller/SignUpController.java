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
        User user = createUser(httpRequest.getBody());
        return getHttpResponse(user);
    }

    private User createUser(String body) {
        Map<String, String> data = new HashMap<>();
        String[] keyAndValues = body.split("&");
        for (String keyVal : keyAndValues) {
            String[] pair = keyVal.split("=");
            data.put(pair[0], pair[1]);
        }

        String userId = data.get("userId");
        String password = data.get("password");
        String name = data.get("name");
        String email = data.get("email");
        return new User(userId, password, name, email);
    }

    private HttpResponse getHttpResponse(User user) {
        HttpStatus httpStatus = new HttpStatus(HttpStatusCode.REDIRECT, "Redirect");
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
