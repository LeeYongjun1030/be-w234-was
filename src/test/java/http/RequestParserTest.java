package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.http.HttpMethod;
import project.http.HttpRequest;
import project.http.RequestParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import static org.assertj.core.api.Assertions.*;

public class RequestParserTest {

    @Test
    @DisplayName("http 요청 메시지를 파싱하여 HttpRequest 객체를 만들어낼 수 있어야 한다.")
    void parsingTest() throws IOException {
        //given
        String msg = "GET /test?name=james&age=28 HTTP/1.1\n" +
                "Connection: keep-alive";
        RequestParser requestParser = createRequestParser(msg);

        //when
        HttpRequest sut = requestParser.parse();

        //then
        assertThat(sut.getHttpMethod()).isEqualTo(HttpMethod.GET);
        assertThat(sut.getPath()).isEqualTo("/test");
        assertThat(sut.getParams().get("name")).isEqualTo("james");
        assertThat(sut.getParams().get("age")).isEqualTo("28");
    }

    @Test
    @DisplayName("url에 파라미터가 없는 경우, 생성된 HttpRequest의 params 속성은 빈 객체여야 한다")
    void getParamsNullTest() throws IOException {
        //given
        String msg = "GET /test HTTP/1.1";
        RequestParser requestParser = createRequestParser(msg);

        //when
        HttpRequest sut = requestParser.parse();

        //then
        assertThat(sut.getParams().size()).isEqualTo(0);
    }

    private RequestParser createRequestParser(String msg) {
        BufferedReader br = new BufferedReader(new StringReader(msg));
        return new RequestParser(br);
    }
}
