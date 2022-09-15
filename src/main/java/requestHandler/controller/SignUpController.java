package requestHandler.controller;


import model.User;
import webserver.ProcessedRequest;
import webserver.RequestParser;

import java.util.Map;

public class SignUpController implements Controller {

    @Override
    public byte[] process(ProcessedRequest processedRequest) {
        User user = createUser(processedRequest.getParams());
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
