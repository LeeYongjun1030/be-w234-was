package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.IOUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class RequestParser {
    private static final Logger logger = LoggerFactory.getLogger(RequestParser.class);

    public HttpRequest parse(BufferedReader reader) throws IOException {
        String startLine = reader.readLine();
        String url = getUrl(startLine);

        // make http-request-object
        HttpMethod httpMethod = getHttpMethod(startLine);
        String path = getPath(url);
        Map<String, String> params = getParams(url);
        Map<String, String> headers = getHeaders(reader);
        String body = getBody(reader, headers);
        return new HttpRequest.Builder(httpMethod)
                .path(path)
                .params(params)
                .headers(headers)
                .body(body)
                .build();
    }

    private String getUrl(String startLine) {
        String[] tokens = startLine.split(" ");
        return tokens[1];
    }

    private HttpMethod getHttpMethod(String startLine) {
        String[] tokens = startLine.split(" ");
        return HttpMethod.valueOf(tokens[0].toUpperCase());
    }

    private String getPath(String url) {
        String[] tokens = url.split("\\?");
        return tokens[0];
    }

    private Map<String, String> getParams(String url) {
        Map<String, String> params = new HashMap<>();

        String[] tokens = url.split("\\?");

        if (tokens.length == 1) { // no params
            return null;
        }

        // queryParams에는 url의 ? 이후의 쿼리파라미터가 담긴다.
        String queryParams = tokens[1];
        String[] pairs = queryParams.split("&");
        for (String pair : pairs) {
            String[] keyAndVal = pair.split("=");
            String key = keyAndVal[0];
            String val = keyAndVal[1];
            params.put(key, val);
        }
        return params;
    }

    private Map<String, String> getHeaders(BufferedReader reader) throws IOException {
        Map<String, String> headers = new HashMap<>();
        String line;
        while (!"".equals((line = reader.readLine()))&& line != null) {
            String[] keyVal = line.split(": ", 2);
            headers.put(keyVal[0], keyVal[1]);
        }
        return headers;
    }

    private String getBody(BufferedReader reader, Map<String, String> headers) throws IOException {
        String body = null;
        if (headers.containsKey("Content-Length")) {
            int contentLength = Integer.parseInt(headers.get("Content-Length"));
            String data = IOUtils.readData(reader, contentLength);
            body = URLDecoder.decode(data, StandardCharsets.UTF_8);
        }
        return body;
    }
}
