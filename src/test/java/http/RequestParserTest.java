package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import static org.assertj.core.api.Assertions.*;

public class RequestParserTest {

    RequestParser requestParser = new RequestParser();
    BufferedReader br;

    @Test
    @DisplayName("request message로부터 HttpRequest 객체를 만들어낼 수 있어야 한다.")
    void parsingTest() throws IOException {
        //given
        String msg = "GET /test?name=james&age=28 HTTP/1.1\n" +
                "Connection: keep-alive";
        br = new BufferedReader(new StringReader(msg));

        //when
        HttpRequest sut = requestParser.parse(br);

        //then
        assertThat(sut.getHttpMethod()).isEqualTo(HttpMethod.GET);
        assertThat(sut.getPath()).isEqualTo("/test");
        assertThat(sut.getParams().get("name")).isEqualTo("james");
        assertThat(sut.getParams().get("age")).isEqualTo("28");
    }

    @Test
    @DisplayName("url에 파라미터가 없는 경우, params는 null을 반환해야 한다")
    void getParamsNullTest() throws IOException {
        //given
        String msg = "GET /test HTTP/1.1";
        br = new BufferedReader(new StringReader(msg));

        //when
        HttpRequest sut = requestParser.parse(br);

        //then
        assertThat(sut.getParams()).isNull();
    }
}
