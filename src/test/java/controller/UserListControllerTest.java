package controller;

import project.controller.Controller;
import project.controller.UserListController;
import project.jpa.repository.UserRepository;
import project.http.HttpMethod;
import project.http.HttpRequest;
import project.http.HttpResponse;
import project.http.HttpStatus;
import project.jpa.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.Map;
import static org.assertj.core.api.Assertions.*;
@Transactional
public class UserListControllerTest {

    private UserRepository userRepository;
    private Controller controller;

    @BeforeEach
    void beforeEach() {
        userRepository = new UserRepository();
        controller = new UserListController(userRepository);
        userRepository.save(new User("test1", "password", "taki", "taki@abc.com"));
        userRepository.save(new User("test2", "password", "roki", "roki@abc.com"));
    }

    @Test
    @DisplayName("로그인 사용자가 /user/list에 접근하면 유저 목록을 반환해주어야 한다")
    void getUserList() {
        //given
        Map<String, String> headers = new HashMap<>();
        headers.put("Cookie", "logined=true");
        HttpRequest httpRequest = new HttpRequest.Builder(HttpMethod.GET).path("/user/list").headers(headers).build();

        //when
        HttpResponse sut = controller.process(httpRequest);

        //then
        assertThat(sut.getHttpStatus()).isEqualTo(HttpStatus.SUCCESSFUL);
    }

    @Test
    @DisplayName("비로그인 사용자가 /user/list에 접근하면 로그인 페이지로 이동해야 한다")
    void redirectLoginPage() {
        //given
        Map<String, String> headers = new HashMap<>();
        headers.put("Cookie", "logined=false");
        HttpRequest httpRequest = new HttpRequest.Builder(HttpMethod.GET).path("/user/list").headers(headers).build();

        //when
        HttpResponse sut = controller.process(httpRequest);

        //then
        assertThat(sut.getHttpStatus()).isEqualTo(HttpStatus.REDIRECT);
    }


}
