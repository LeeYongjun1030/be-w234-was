package controller;

import http.HttpMethod;
import http.HttpRequest;
import http.HttpResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class StyleSheetControllerTest {

    Controller controller;

    @BeforeEach
    void beforeEach() {
        controller = new StyleSheetController();
    }

    @Test
    @DisplayName("css 파일 요청을 처리하여 응답 메시지를 만들 수 있어야 한다")
    void styleSheetProcess() {
        //given
        HttpRequest httpRequest = new HttpRequest.Builder(HttpMethod.GET).path("/css/styles.css").build();

        //when
        HttpResponse sut = controller.process(httpRequest);

        //then
        assertThat(sut.getHttpStatus().getCode()).isEqualTo(200);
        assertThat(sut.getHeaders().get("Content-Type")).isEqualTo("text/css");
        assertThat(sut.getBody()).isNotEmpty();

    }
}
