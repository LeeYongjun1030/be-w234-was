package controller;

import project.controller.Controller;
import project.controller.StaticHtmlController;
import project.http.HttpMethod;
import project.http.HttpResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.http.HttpRequest;
import static org.assertj.core.api.Assertions.*;

public class StaticHtmlControllerTest {
    Controller controller = new StaticHtmlController();

    @Test
    @DisplayName("/index.html 요청 시 응답으로 webapp/index.html 파일을 반환해야 한다")
    void staticHtmlTest() {
        //given
        HttpRequest httpRequest = new HttpRequest.Builder(HttpMethod.GET).path("/index.html").build();

        //when
        HttpResponse sut = controller.process(httpRequest);

        //then
        assertThat(sut.getHttpStatus().getCode()).isEqualTo(200);
        assertThat(sut.getHeader("Content-Type")).isEqualTo("text/html;charset=utf-8");
        assertThat(sut.getBody()).isNotEmpty();
    }
}
