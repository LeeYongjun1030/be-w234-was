package entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;
import project.jpa.entity.Memo;
import project.jpa.entity.User;
import project.jpa.repository.MemoRepository;
import project.jpa.repository.UserRepository;
import java.util.List;
import static org.assertj.core.api.Assertions.*;

@Transactional
public class MemoRepositoryTest {

    private UserRepository userRepository = new UserRepository();
    private MemoRepository memoRepository = new MemoRepository();
    private User user = new User("testId", "password", "taki", "taki@abcd.com");
    private Memo memo = new Memo(user, "test-memo");
    private Memo memo2 = new Memo(user, "test-memo-2");

    @BeforeEach
    void beforeEach() {
        userRepository.save(user);
    }

    @Test
    @DisplayName("메모 객체를 저장할 수 있어야 한다")
    void saveMemoTest() {
        //when
        Long memoId = memoRepository.save(memo);

        //then
        Memo sut = memoRepository.findById(memoId);
        assertThat(sut.getUsername()).isEqualTo(user.getName());
        assertThat(sut.getContent()).isEqualTo(memo.getContent());

    }

    @Test
    @DisplayName("모든 메모를 조회할 수 있다")
    void findAllTest() {
        //given
        memoRepository.save(memo);
        memoRepository.save(memo2);

        //when
        List<Memo> sut = memoRepository.findAll();

        //then
        assertThat(sut.size()).isEqualTo(2);
    }
}
