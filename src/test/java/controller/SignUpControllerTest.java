package controller;

import project.controller.Controller;
import project.controller.SignUpController;
import project.http.HttpMethod;
import project.jpa.repository.UserRepository;
import project.jpa.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.http.HttpRequest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@Transactional
public class SignUpControllerTest {

    private UserRepository userRepository;
    private Controller controller;
    private SignUp signUp;

    @BeforeEach
    void beforeEach() {
        userRepository = new UserRepository();
        controller = new SignUpController(userRepository);
        signUp = new SignUp("testId", "password", "taki", "taki@abcd.com");
    }

    @Test
    @DisplayName("회원가입 시 회원 정보가 User 클래스에 저장되어야 한다")
    void signup() {
        //given
        HttpRequest httpRequest = createHttpReq(signUp);

        //when
        controller.process(httpRequest);

        //then
        User sut = userRepository.findById(signUp.userId);
        assertThat(sut.getName()).isEqualTo(signUp.name);
        assertThat(sut.getEmail()).isEqualTo(signUp.email);
    }

    private HttpRequest createHttpReq(SignUp signUp) {
        String inputData = input(signUp.userId, signUp.password, signUp.name, signUp.email);
        return new HttpRequest.Builder(HttpMethod.POST)
                .body(inputData)
                .build();
    }

    private String input(String userId, String password, String name, String email) {
        return "userId=" + userId +
                "&password=" + password +
                "&name=" + name +
                "&email=" + email;
    }

    private class SignUp {
        String userId;
        String password;
        String name;
        String email;

        public SignUp(String userId, String password, String name, String email) {
            this.userId = userId;
            this.password = password;
            this.name = name;
            this.email = email;
        }
    }

}
