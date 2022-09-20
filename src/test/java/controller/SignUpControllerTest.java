package controller;

import http.HttpResponse;
import http.HttpStatusCode;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import controller.SignUpController;
import http.HttpRequest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class SignUpControllerTest {

    Controller signUpController;

    @BeforeEach
    void beforeEach() {
       signUpController = new SignUpController();
    }

    @Test
    @DisplayName("회원가입 시 회원 정보가 User 클래스에 저장되어야 한다")
    void signup() {
        //given
        String body = getBody("testId", "password", "taki", "taki@abcd.com");
        HttpRequest httpRequest = new HttpRequest(null, null, null, null, body);

        //when
        HttpResponse sut = signUpController.process(httpRequest);

        //then
        User user = new User("testId", "password", "taki", "taki@abcd.com");
        assertThat(sut.getHttpStatus().getCode()).isEqualTo(HttpStatusCode.REDIRECT);
        assertThat(sut.getBody()).isEqualTo(user.toString().getBytes());
    }

    private String getBody(String userId, String password, String name, String email) {
        return String.format("userId=%s&password=%s&name=%s&email=%s", userId, password, name, email);
    }
}
