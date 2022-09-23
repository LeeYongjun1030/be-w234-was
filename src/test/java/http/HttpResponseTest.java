package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class HttpResponseTest {

    HttpResponse httpResponse;

    @Test
    @DisplayName("HttpResponse 객체로부터 http 응답 메시지를 만들어낼 수 있어야 한다 - body가 없는 경우")
    void createMsgWithBodilessResponse() {
        //given
        httpResponse = new HttpResponse.Builder(HttpStatus.SUCCESSFUL)
                .header("Content-Type", "text/html;charset=utf-8")
                .build();

        //when
        byte[] sut = httpResponse.convertToHttpResponseMessage();

        //then
        String[] token = new String(sut).split("\r\n");
        assertThat(token[0]).isEqualTo("HTTP/1.1 200 OK");
        assertThat(token[1]).isEqualTo("Content-Type: text/html;charset=utf-8");
    }

    @Test
    @DisplayName("HttpResponse 객체로부터 http 응답 메시지를 만들어낼 수 있어야 한다 - body가 있는 경우")
    void createMsgWithResponse() {
        //given
        String dataOfString = "This is response body.";
        byte[] data = dataOfString.getBytes();

        httpResponse = new HttpResponse.Builder(HttpStatus.SUCCESSFUL)
                .header("Content-Type", "text/html;charset=utf-8")
                .header("Content-Length", String.valueOf(data.length))
                .body(data)
                .build();

        //when
        byte[] sut = httpResponse.convertToHttpResponseMessage();

        //then
        String[] token = new String(sut).split("\r\n");
        assertThat(token[0]).isEqualTo("HTTP/1.1 200 OK");
        assertThat(token[1]).isEqualTo("Content-Length: " + httpResponse.getBody().length);
        assertThat(token[2]).isEqualTo("Content-Type: text/html;charset=utf-8");

    }
}
