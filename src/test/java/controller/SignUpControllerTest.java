package controller;

import db.Database;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import http.HttpRequest;
import static org.assertj.core.api.Assertions.*;

public class SignUpControllerTest {

    Controller signUpController;

    @BeforeEach
    void beforeEach() {
       signUpController = new SignUpController();
    }

    @AfterEach
    void afterEach() {
        Database.clear();
    }

    @Test
    @DisplayName("회원가입 시 회원 정보가 User 클래스에 저장되어야 한다")
    void signup() {
        //given
        String body = getBody("testId", "password", "타키", "taki@abcd.com");
        HttpRequest httpRequest = new HttpRequest(null, null, null, null, body);
        signUpController.process(httpRequest);

        //when
        User sut = Database.findUserById("testId");

        //then
        assertThat(sut.getName()).isEqualTo("타키");
        assertThat(sut.getEmail()).isEqualTo("taki@abcd.com");
    }

    private String getBody(String userId, String password, String name, String email) {
        return String.format("userId=%s&password=%s&name=%s&email=%s", userId, password, name, email);
    }
}
