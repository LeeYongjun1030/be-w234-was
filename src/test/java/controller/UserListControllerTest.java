package controller;

import project.controller.Controller;
import project.controller.UserListController;
import project.http.*;
import project.jpa.repository.UserRepository;
import project.jpa.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.*;
@Transactional
public class UserListControllerTest {

    private UserRepository userRepository = new UserRepository();
    private Controller controller = new UserListController(userRepository);

    @BeforeEach
    void beforeEach() {
        userRepository.save(new User("test1134344ddddddasawdfdfdsdsddfdddsssdsss", "password", "taki", "taki@abc.com"));
        userRepository.save(new User("test2", "password", "roki", "roki@abc.com"));
    }

    @Test
    @DisplayName("로그인 사용자가 /user/list에 접근하면 유저 목록을 반환해주어야 한다")
    void getUserList() {
        //given
        HttpRequest httpRequest = new HttpRequest.Builder(HttpMethod.GET)
                .path("/user/list")
                .cookie(new Cookie("logined", "test-id", "/"))
                .build();

        //when
        HttpResponse sut = controller.process(httpRequest);

        //then
        assertThat(sut.getHttpStatus()).isEqualTo(HttpStatus.SUCCESSFUL);
    }

    @Test
    @DisplayName("비로그인 사용자가 /user/list에 접근하면 로그인 페이지로 이동해야 한다")
    void redirectLoginPage() {
        //given
        HttpRequest httpRequest = new HttpRequest.Builder(HttpMethod.GET)
                .path("/user/list")
                .build();

        //when
        HttpResponse sut = controller.process(httpRequest);

        //then
        assertThat(sut.getHttpStatus()).isEqualTo(HttpStatus.REDIRECT);
    }

}
