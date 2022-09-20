package controller;

import db.Database;
import http.HttpRequest;
import http.HttpResponse;
import http.HttpStatus;
import http.HttpStatusCode;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class SignInController implements Controller{

    private static final Logger logger = LoggerFactory.getLogger(SignInController.class);

    @Override
    public HttpResponse process(HttpRequest httpRequest) {
        Map<String, String> loginInput = getLoginIdAndPassword(httpRequest.getBody());
        String userId = loginInput.get("userId");
        String password = loginInput.get("password");

        boolean isLoginSucceed = verify(userId, password);
        return getHttpResponse(isLoginSucceed);
    }

    private Map<String, String> getLoginIdAndPassword(String body) {
        Map<String, String> loginInput = new HashMap<>();
        String[] keyAndValues = body.split("&");
        for (String keyVal : keyAndValues) {
            String[] pair = keyVal.split("=");
            loginInput.put(pair[0], pair[1]);
        }
        return loginInput;
    }

    private boolean verify(String userId, String password) {
        boolean login;
        try {
            User findUser = Database.findUserById(userId);
            if (findUser.getPassword().equals(password)) {
                login = true;
            } else {
                login = false;
            }
        } catch (RuntimeException e) {
            login = false;
        }

        logger.debug("userId {}", userId);
        logger.debug("password {}", password);
        logger.debug("login success? {}", login);
        return login;
    }

    private HttpResponse getHttpResponse(boolean login) {
        if (login) {
            return getLoginSuccessHttpResponse();
        } else {
            return getLoginFailHttpResponse();
        }
    }
    private HttpResponse getLoginSuccessHttpResponse() {
        HttpStatus httpStatus = new HttpStatus(HttpStatusCode.REDIRECT, "Found");
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "text/html");
        headers.put("Location", "/index.html");
        headers.put("Set-Cookie", "logined=true; Path=/");
        return new HttpResponse(httpStatus, headers, null);
    }
    private HttpResponse getLoginFailHttpResponse() {
        HttpStatus httpStatus = new HttpStatus(HttpStatusCode.REDIRECT, "Found");
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "text/html");
        headers.put("Location", "/user/login_failed.html");
        headers.put("Set-Cookie", "logined=false; Path=/");
        return new HttpResponse(httpStatus, headers, null);
    }
}
