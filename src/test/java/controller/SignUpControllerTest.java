package controller;

import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import controller.SignUpController;
import http.HttpRequest;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class SignUpControllerTest {

    SignUpController signUpController;

    @BeforeEach
    void beforeEach() {
       signUpController = new SignUpController();
    }

    @Test
    @DisplayName("회원가입 시 회원 정보가 User 클래스에 저장되어야 한다")
    void signup() {
        //given
        Map<String, String> params = initParams("testId", "password", "taki", "taki@abcd.com");
        HttpRequest httpRequest = new HttpRequest(null, null, params, null);

        //when
        byte[] sut = signUpController.process(httpRequest);

        //then
        User user = new User("testId", "password", "taki", "taki@abcd.com");
        assertThat(new String(sut)).isEqualTo(user.toString());
    }

    private Map<String, String> initParams(String userId, String password, String name, String email) {
        Map<String, String> params = new HashMap<>();
        params.put("userId", userId);
        params.put("password", password);
        params.put("name", name);
        params.put("email", email);
        return params;
    }
}
