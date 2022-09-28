package controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.controller.Controller;
import project.controller.MemoFormController;
import project.http.HttpMethod;
import project.http.HttpRequest;
import project.http.HttpResponse;
import project.http.HttpStatus;
import static org.assertj.core.api.Assertions.*;

public class MemoFormControllerTest {

    private Controller controller = new MemoFormController();

    @Test
    @DisplayName("로그인 사용자의 경우 메모하기 버튼을 눌렀을 때 메모 폼이 보여야 한다")
    void creatMemoForm() {
        //given
        HttpRequest httpRequest = createHttpRequestOf("logined=true");

        //when
        HttpResponse sut = controller.process(httpRequest);

        //then
        assertThat(sut.getHttpStatus()).isEqualTo(HttpStatus.SUCCESSFUL);
    }

    @Test
    @DisplayName("비로그인 사용자인 경우 메모하기 버튼을 눌렀을 때 로그인 화면으로 이동해야 한다")
    void loginForm() {
        //given
        HttpRequest httpRequest = createHttpRequestOf("logined=false");

        //when
        HttpResponse sut = controller.process(httpRequest);

        //then
        assertThat(sut.getHttpStatus()).isEqualTo(HttpStatus.REDIRECT);
    }

    private HttpRequest createHttpRequestOf(String val) {
        return new HttpRequest.Builder(HttpMethod.GET)
                .path("/qna")
                .header("Cookie", val)
                .build();
    }
}
