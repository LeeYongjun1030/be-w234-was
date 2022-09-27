package project.controller;

import project.http.HttpRequest;
import project.http.HttpResponse;
import project.http.HttpStatus;
import project.jpa.repository.UserRepository;
import project.jpa.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import project.util.LoginInfo;

public class SignInController implements Controller{

    private static final Logger logger = LoggerFactory.getLogger(SignInController.class);
    private UserRepository userRepository;

    public SignInController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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
            User findUser = userRepository.findById(inputUserId);
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
        return new HttpResponse.Builder(HttpStatus.REDIRECT)
                .header("Location", "/index.html")
                .header("Content-Type", "text/html")
                .header("Set-Cookie", "logined=true; Path=/")
                .build();
    }

    private HttpResponse loginFail() {
        return new HttpResponse.Builder(HttpStatus.REDIRECT)
                .header("Location", "/user/login_failed.html")
                .header("Content-Type", "text/html")
                .header("Set-Cookie", "logined=false; Path=/")
                .build();
    }
}
