package entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;
import project.jpa.entity.User;
import project.jpa.repository.UserRepository;
import java.util.List;
import static org.assertj.core.api.Assertions.*;

@Transactional
public class UserRepositoryTest {

    UserRepository userRepository;

    @BeforeEach
    void beforeEach() {
        userRepository = new UserRepository();
    }

    @Test
    @DisplayName("유저를 저장할 수 있어야 한다")
    void save() {
        //given
        User user = createUser();

        //when
        String userId = userRepository.save(user);

        //then
        User sut = userRepository.findById(userId);
        assertThat(sut.getName()).isEqualTo("taki");
        assertThat(sut.getEmail()).isEqualTo("taki@abcd.com");
    }

    @Test
    @DisplayName("없는 유저 조회 시 null이 반환되어야 한다")
    void findByIdTest() {
        //given
        String fakeId = "fake-id";

        //when
        User sut = userRepository.findById(fakeId);

        //then
        assertThat(sut).isNull();
    }

    @Test
    @DisplayName("모든 유저를 조회할 수 있어야 한다")
    void findAll() {
        //given
        User user = createUser();
        userRepository.save(user);
        User user2 = createUser2();
        userRepository.save(user2);

        //when
        List<User> sut = userRepository.findAll();

        //then
        assertThat(sut.size()).isEqualTo(2);
    }

    private User createUser() {
        return new User("testId", "password", "taki", "taki@abcd.com");
    }

    private User createUser2() {
        return new User("testId2", "password2", "taki2", "taki2@abcd.com");
    }
}
