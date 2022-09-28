package controller;

import project.controller.Controller;
import project.controller.SignInController;
import project.jpa.repository.UserRepository;
import project.http.HttpMethod;
import project.http.HttpRequest;
import project.http.HttpResponse;
import project.jpa.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

@Transactional
public class SignInControllerTest {

    private UserRepository userRepository = new UserRepository();
    private Controller controller = new SignInController(userRepository);
    private User user = new User("testId", "1234", "taki", "taki@abcd.com");

    @BeforeEach
    void beforeEach() {
        userRepository.save(user);
    }

    @Test
    @DisplayName("로그인 입력 정보가 올바르면 로그인에 성공해야 한다")
    void signInSuccess() {
        //given
        HttpRequest httpRequest = createHttpRequestFrom(user.getUserId(), user.getPassword());

        //when
        HttpResponse sut = controller.process(httpRequest);

        //then
        assertThat(sut.getHeaders().get("Set-Cookie")).isEqualTo("logined=true; Path=/");
    }

    @Test
    @DisplayName("존재하지 않는 로그인 아이디를 입력하면 로그인에 실패해야 한다")
    void signInFail() {
        //given
        String userId = "wrong-userId";
        HttpRequest httpRequest = createHttpRequestFrom(userId, user.getPassword());

        //when
        HttpResponse sut = controller.process(httpRequest);

        //then
        assertThat(sut.getHeaders().get("Set-Cookie")).isEqualTo("logined=false; Path=/");
    }

    @Test
    @DisplayName("비밀번호가 올바르지 않으면 로그인에 실패해야 한다")
    void signInFail2() {
        //given
        String password = "wrong-password";
        HttpRequest httpRequest = createHttpRequestFrom(user.getUserId(), password);

        //when
        HttpResponse sut = controller.process(httpRequest);

        //then
        assertThat(sut.getHeaders().get("Set-Cookie")).isEqualTo("logined=false; Path=/");
    }

    private HttpRequest createHttpRequestFrom(String userId, String password) {
        String loginInput = String.format("userId=%s&password=%s", userId, password);
        return new HttpRequest.Builder(HttpMethod.GET)
                .body(loginInput)
                .build();
    }
}
