import http.HttpMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import requestHandler.controller.StaticHtmlController;
import http.HttpRequest;

import java.io.IOException;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.*;

public class StaticHtmlHandlerTest {

    StaticHtmlController staticHtmlHandler;

    @BeforeEach
    void beforeEach() {
        staticHtmlHandler = new StaticHtmlController();
    }

    @Test
    @DisplayName("/index.html 접속 시 webapp/index.html 파일을 반환해야 한다")
    void test() throws IOException {
        //given
        HttpRequest httpRequest = new HttpRequest(HttpMethod.GET, "/index.html", null, null);

        //when
        byte[] sut = staticHtmlHandler.process(httpRequest);

        //then
        assertThat(sut).isNotEmpty();
    }
}
