
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.ProcessedRequest;
import webserver.RequestParser;

import static org.assertj.core.api.Assertions.*;

public class RequestParserTest {

    @Test
    @DisplayName("request로부터 필요한 데이터를 추출할 수 있어야 한다")
    void parsingTest() {
        //given
        String startLine = "GET /test?name=james&age=28 HTTP/1.1";
        RequestParser requestParser = new RequestParser(startLine);

        //when
        ProcessedRequest sut = requestParser.parse();

        //then
        assertThat(sut.getUrl()).isEqualTo("/test?name=james&age=28");
        assertThat(sut.getPath()).isEqualTo("/test");
        assertThat(sut.getParams().get("name")).isEqualTo("james");
        assertThat(sut.getParams().get("age")).isEqualTo("28");
    }


    @Test
    @DisplayName("url에 파라미터가 없는 경우, params는 null을 반환해야 한다")
    void getParamsNullTest() {
        //given
        String startLine = "GET /test HTTP/1.1";
        RequestParser requestParser = new RequestParser(startLine);

        //when
        ProcessedRequest sut = requestParser.parse();

        //then
        assertThat(sut.getParams()).isNull();
    }
}
