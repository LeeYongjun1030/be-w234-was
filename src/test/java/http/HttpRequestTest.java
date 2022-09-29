package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.http.Cookie;
import project.http.HttpMethod;
import project.http.HttpRequest;
import static org.assertj.core.api.Assertions.*;

public class HttpRequestTest {

    @Test
    @DisplayName("HttpRequest 클래스 생성 테스트")
    void createHttpReq() {
        //given

        //when
        HttpRequest sut = new HttpRequest.Builder(HttpMethod.GET)
                .path("/test.html")
                .param("test-key", "test-val")
                .cookie(new Cookie("name", "taki", "/"))
                .header("Content-Type", "text/html")
                .body("test-body")
                .build();

        //then
        assertThat(sut.getHttpMethod()).isEqualTo(HttpMethod.GET);
        assertThat(sut.getPath()).isEqualTo("/test.html");
        assertThat(sut.getParam("test-key")).isEqualTo("test-val");
        assertThat(sut.getCookie().get("name")).isEqualTo("taki");
        assertThat(sut.getHeader("Content-Type")).isEqualTo("text/html");
        assertThat(sut.getBody()).isEqualTo("test-body");
    }
}
