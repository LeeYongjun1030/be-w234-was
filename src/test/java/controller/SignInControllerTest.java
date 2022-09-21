package controller;

import db.Database;
import http.HttpRequest;
import http.HttpResponse;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SignInControllerTest {

    Controller controller;

    @BeforeEach
    void beforeEach() {
        controller = new SignInController();
    }

    @AfterEach
    void afterEach() {
        Database.clear();
    }

    @Test
    @DisplayName("로그인 입력 정보가 올바르면 로그인에 성공해야 한다")
    void signInSuccess() {
        //given
        createAndSaveUser();

        //when
        HttpRequest loginInput = createHttpRequestFromInput("testId", "1234");
        HttpResponse sut = controller.process(loginInput);

        //then
        assertThat(sut.getHeaders().get("Set-Cookie")).isEqualTo("logined=true; Path=/");
    }

    @Test
    @DisplayName("존재하지 않는 로그인 아이디를 입력하면 로그인에 실패해야 한다")
    void signInFail() {
        //given
        createAndSaveUser();

        //when
        HttpRequest loginInput = createHttpRequestFromInput("xxxx", "1234");
        HttpResponse sut = controller.process(loginInput);

        //then
        assertThat(sut.getHeaders().get("Set-Cookie")).isEqualTo("logined=false; Path=/");
    }

    @Test
    @DisplayName("아이디는 올바르나 비밀번호가 올바르지 않으면 로그인에 실패해야 한다")
    void signInFail2() {
        //given
        createAndSaveUser();

        //when
        HttpRequest loginInput = createHttpRequestFromInput("testId", "4321");
        HttpResponse sut = controller.process(loginInput);

        //then
        assertThat(sut.getHeaders().get("Set-Cookie")).isEqualTo("logined=false; Path=/");
    }

    private void createAndSaveUser() {
        User user = new User("testId", "1234", "taki", "taki@abcd.com");
        Database.addUser(user);
    }

    private HttpRequest createHttpRequestFromInput(String userId, String password) {
        String loginInput = String.format("userId=%s&password=%s", userId, password);
        return new HttpRequest(null, null, null, null, loginInput);
    }
}
