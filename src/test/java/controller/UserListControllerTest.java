package controller;

import db.Database;
import http.HttpRequest;
import http.HttpResponse;
import http.HttpStatusCode;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.assertj.core.api.Assertions.*;

public class UserListControllerTest {

    Controller controller;

    @BeforeEach
    void beforeEach() {
        controller = new UserListController();
        Database.addUser(new User("test1", "password", "taki", "taki@abc.com"));
        Database.addUser(new User("test2", "password", "roki", "roki@abc.com"));
    }

    @AfterEach
    void afterEach() {
        Database.clear();
    }

    @Test
    @DisplayName("로그인 사용자가 /user/list에 접근하면 유저 목록을 반환해주어야 한다")
    void getUserList() {
        //given
        Map<String, String> headers = new HashMap<>();
        headers.put("Cookie", "logined=true");
        HttpRequest httpRequest = new HttpRequest(null, "/user/list", null, headers, null);

        //when
        HttpResponse sut = controller.process(httpRequest);

        //then
        assertThat(sut.getHttpStatus().getCode()).isEqualTo(HttpStatusCode.SUCCESSFUL);
    }

    @Test
    @DisplayName("비로그인 사용자가 /user/list에 접근하면 로그인 페이지로 이동해야 한다")
    void redirectLoginPage() {
        //given
        Map<String, String> headers = new HashMap<>();
        headers.put("Cookie", "logined=false");
        HttpRequest httpRequest = new HttpRequest(null, "/user/list", null, headers, null);

        //when
        HttpResponse sut = controller.process(httpRequest);

        //then
        assertThat(sut.getHttpStatus().getCode()).isEqualTo(HttpStatusCode.REDIRECT);
    }


}
