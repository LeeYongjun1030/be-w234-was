package controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;
import project.controller.Controller;
import project.controller.MemoFormController;
import project.http.*;
import project.jpa.entity.Memo;
import project.jpa.entity.User;
import project.jpa.repository.MemoRepository;
import project.jpa.repository.UserRepository;
import java.util.List;
import static org.assertj.core.api.Assertions.*;

@Transactional
public class MemoFormControllerTest {

    private UserRepository userRepository = new UserRepository();
    private MemoRepository memoRepository = new MemoRepository();
    private Controller controller = new MemoFormController(userRepository, memoRepository);

    private User user;

    @BeforeEach
    void beforeEach() {
        user = new User("test1", "password", "taki", "taki@abc.com");
        userRepository.save(user);
    }

    @Test
    @DisplayName("로그인 사용자의 경우 메모하기 버튼을 눌렀을 때 메모 폼이 보여야 한다")
    void creatMemoForm() {
        //given
        HttpRequest httpRequest = new HttpRequest.Builder(HttpMethod.GET)
                .path("/qna")
                .cookie(new Cookie("logined", user.getUserId(), "/"))
                .build();

        //when
        HttpResponse sut = controller.process(httpRequest);

        //then
        assertThat(sut.getHttpStatus()).isEqualTo(HttpStatus.SUCCESSFUL);
    }
    @Test
    @DisplayName("비로그인 사용자인 경우 메모하기 버튼을 눌렀을 때 로그인 화면으로 이동해야 한다")
    void loginForm() {
        //given
        HttpRequest httpRequest = new HttpRequest.Builder(HttpMethod.GET)
                .path("/qna")
                .build();

        //when
        HttpResponse sut = controller.process(httpRequest);

        //then
        assertThat(sut.getHttpStatus()).isEqualTo(HttpStatus.REDIRECT);
    }

    @Test
    @DisplayName("메모를 작성하면 메모 정보가 메모 클래스에 저장되어야 한다")
    void createMemo() {
        //given
        String memo = "test-memo";
        HttpRequest httpRequest = createHttpReq(memo);

        //when
        controller.process(httpRequest);

        //then
        List<Memo> sut = memoRepository.findAll();
        assertThat(sut.size()).isEqualTo(1);
    }

    private HttpRequest createHttpReq(String memo) {
        return new HttpRequest.Builder(HttpMethod.POST)
                .path("qna")
                .cookie(new Cookie("logined", user.getUserId(), "/"))
                .body("contents=" + memo)
                .build();
    }
}
