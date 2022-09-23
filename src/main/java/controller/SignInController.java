package controller;

import db.Database;
import http.HttpRequest;
import http.HttpResponse;
import http.HttpStatus;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.LoginInfo;
import java.util.HashMap;
import java.util.Map;

public class SignInController implements Controller{

    private static final Logger logger = LoggerFactory.getLogger(SignInController.class);

    @Override
    public HttpResponse process(HttpRequest httpRequest) {
        LoginInfo loginInput = getLoginIdAndPassword(httpRequest.getBody());
        return verify(loginInput) ? loginSuccess() : loginFail();
    }

    private LoginInfo getLoginIdAndPassword(String body) {
        String userId = "";
        String password = "";
        try {
            String[] keyAndValues = body.split("&");
            for (String keyVal : keyAndValues) {
                String[] pair = keyVal.split("=");
                if(pair[0].equals("userId")) userId = pair[1];
                if(pair[0].equals("password")) password = pair[1];
            }
        } catch (RuntimeException e) {
            logger.error("입력 정보가 올바르지 않습니다.");
        }
        return new LoginInfo(userId, password);
    }

    private boolean verify(LoginInfo loginInput) {
        String inputUserId = loginInput.getUserId();
        String inputPassword = loginInput.getPassword();

        boolean login;
        try {
            User findUser = Database.findUserById(inputUserId);
            login = findUser.getPassword().equals(inputPassword);
        } catch (RuntimeException e) {
            login = false;
        }

        logger.debug("InputUserId {}", inputUserId);
        logger.debug("InputPassword {}", inputPassword);
        logger.debug("LoginSuccess? {}", login);
        return login;
    }

    private HttpResponse loginSuccess() {
        HttpStatus httpStatus = HttpStatus.REDIRECT;
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "text/html");
        headers.put("Location", "/index.html");
        headers.put("Set-Cookie", "logined=true; Path=/");
        return new HttpResponse(httpStatus, headers, null);
    }

    private HttpResponse loginFail() {
        HttpStatus httpStatus = HttpStatus.REDIRECT;
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "text/html");
        headers.put("Location", "/user/login_failed.html");
        headers.put("Set-Cookie", "logined=false; Path=/");
        return new HttpResponse(httpStatus, headers, null);
    }
}
