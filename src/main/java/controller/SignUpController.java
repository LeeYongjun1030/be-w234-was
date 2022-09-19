package controller;


import model.User;
import http.HttpRequest;

import java.util.Map;

public class SignUpController implements Controller {

    @Override
    public byte[] process(HttpRequest httpRequest) {
        User user = createUser(httpRequest.getParams());
        return user.toString().getBytes();
    }

    private User createUser(Map<String, String> params) {
        String userId = params.get("userId");
        String password = params.get("password");
        String name = params.get("name");
        String email = params.get("email");
        return new User(userId, password, name, email);
    }
}
