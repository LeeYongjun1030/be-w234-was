package controller;

import http.HttpMethod;
import http.HttpResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import http.HttpRequest;
import static org.assertj.core.api.Assertions.*;

public class StaticHtmlControllerTest {
    Controller controller;

    @BeforeEach
    void beforeEach() {
        controller = new StaticHtmlController();
    }

    @Test
    @DisplayName("/index.html 요청 시 응답으로 webapp/index.html 파일을 반환해야 한다")
    void staticHtmlTest() {
        //given
        HttpRequest httpRequest = new HttpRequest.Builder(HttpMethod.GET).path("/index.html").build();

        //when
        HttpResponse sut = controller.process(httpRequest);

        //then
        assertThat(sut.getHttpStatus().getCode()).isEqualTo(200);
        assertThat(sut.getHeaders().get("Content-Type")).isEqualTo("text/html;charset=utf-8");
        assertThat(sut.getBody()).isNotEmpty();
    }
}
