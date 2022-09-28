package controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;
import project.controller.MemoListController;
import project.http.HttpMethod;
import project.http.HttpRequest;
import project.http.HttpResponse;
import project.http.HttpStatus;
import project.jpa.entity.Memo;
import project.jpa.entity.User;
import project.jpa.repository.MemoRepository;
import project.jpa.repository.UserRepository;

import static org.assertj.core.api.Assertions.*;

@Transactional
public class MemoListControllerTest {

    private UserRepository userRepository = new UserRepository();
    private MemoRepository memoRepository = new MemoRepository();

    private MemoListController controller = new MemoListController(memoRepository);

    private User user1 = new User("userId1", "password", "taki", "taki@abcd.com");
    private User user2 = new User("userId2", "password2", "roki", "roki@abcd.com");

    private Memo memo1 = new Memo(user1, "test-memo-1-from-user1");
    private Memo memo2 = new Memo(user1, "test-memo-2-from-user1");
    private Memo memo3 = new Memo(user2, "test-memo-3-from-user2");

    @BeforeEach
    void beforeEach() {
        userRepository.save(user1);
        userRepository.save(user2);
        memoRepository.save(memo1);
        memoRepository.save(memo2);
        memoRepository.save(memo3);
    }

    @Test
    @DisplayName("GET /qna/list 요청 시 전체 메모 목록을 반환할 수 있어야 한다")
    void memoList() {
        //given
        HttpRequest httpRequest = new HttpRequest.Builder(HttpMethod.GET)
                .path("/qna/list")
                .build();

        //when
        HttpResponse sut = controller.process(httpRequest);

        //then
        assertThat(sut.getHttpStatus()).isEqualTo(HttpStatus.SUCCESSFUL);
    }
}
