package controller;

import http.HttpMethod;
import http.HttpResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import http.HttpRequest;

import java.io.IOException;

import static org.assertj.core.api.Assertions.*;

public class StaticHtmlControllerTest {
    Controller staticHtmlController;

    @BeforeEach
    void beforeEach() {
        staticHtmlController = new StaticHtmlController();
    }

    @Test
    @DisplayName("/index.html 접속 시 webapp/index.html 파일을 반환해야 한다")
    void test() {
        //given
        HttpRequest httpRequest = new HttpRequest(HttpMethod.GET, "/index.html", null, null);

        //when
        HttpResponse sut = staticHtmlController.process(httpRequest);

        //then
        assertThat(sut.getHttpStatus().getCode()).isEqualTo(200);
        assertThat(sut.getBody()).isNotEmpty();
    }
}
