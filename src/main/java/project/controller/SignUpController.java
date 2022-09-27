package project.controller;


import project.jpa.repository.UserRepository;
import project.http.HttpResponse;
import project.http.HttpStatus;
import project.jpa.entity.User;
import project.http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.Map;

public class SignUpController implements Controller {

    private static final Logger logger = LoggerFactory.getLogger(SignUpController.class);

    private UserRepository userRepository;

    public SignUpController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public HttpResponse process(HttpRequest httpRequest) {
        User user = createUser(httpRequest.getBody());
        userRepository.save(user);

        return new HttpResponse.Builder(HttpStatus.REDIRECT)
                .header("Location", "/index.html")
                .header("Content-Type", "text/html;charset=utf-8")
                .body(user.toString().getBytes())
                .build();
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
}
