package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpResponseTest {

    @Test
    @DisplayName("HttpResponse 객체로부터 http 응답 메시지를 만들어낼 수 있어야 한다")
    void parsingTest() {
        //given
        HttpResponse httpResponse = getHttpResponse();

        //when
        ArrayList<byte[]> sut = httpResponse.toHttpResponseMessage();

        //then
        assertThat(sut.get(0)).isEqualTo("HTTP/1.1 200 OK\r\n".getBytes());
        assertThat(sut.get(1)).isEqualTo("Content-Length: 22\r\n".getBytes());
        assertThat(sut.get(2)).isEqualTo("Content-Type: text/html;charset=utf-8\r\n".getBytes());
        assertThat(sut.get(3)).isEqualTo("\r\n".getBytes());
        assertThat(sut.get(4)).isEqualTo("This is response body.".getBytes());
    }

    private HttpResponse getHttpResponse() {
        HttpStatus httpStatus = new HttpStatus(HttpStatusCode.SUCCESSFUL, "OK");
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "text/html;charset=utf-8");
        String bodyOfString = "This is response body.";
        byte[] body = bodyOfString.getBytes();
        headers.put("Content-Length", String.valueOf(body.length));
        return new HttpResponse(httpStatus, headers, body);
    }
}
