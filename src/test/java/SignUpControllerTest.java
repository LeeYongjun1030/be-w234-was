import model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import requestHandler.controller.SignUpController;
import webserver.ProcessedRequest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class SignUpControllerTest {

    SignUpController signUpController;

    @BeforeEach
    void beforeEach() {
       signUpController = new SignUpController();
    }

    @Test
    @DisplayName("회원가입 시 회원 정보가 User 클래스에 저장되어야 한다")
    void signup() {
        //given
        Map<String, String> params = new HashMap<>();
        params.put("userId", "testId");
        params.put("password", "password");
        params.put("name", "taki");
        params.put("email", "taki@abcd.com");
        ProcessedRequest pr = new ProcessedRequest("", "", params);

        //when
        byte[] sut = signUpController.process(pr);

        //then
        User user = new User("testId", "password", "taki", "taki@abcd.com");
        assertThat(new String(sut)).isEqualTo(user.toString());
    }
}
