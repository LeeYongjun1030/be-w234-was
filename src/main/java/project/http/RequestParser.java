package project.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import project.util.IOUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class RequestParser {

    private static final Logger logger = LoggerFactory.getLogger(RequestParser.class);

    private static RequestParser instance;

    private BufferedReader reader;
    private String startLine;
    private String url;

    private HttpMethod httpMethod;

    private String path;

    private Map<String, String> params = new HashMap<>();

    private Map<String, String> headers = new HashMap<>();

    private Cookie cookie;

    private String body;

    private RequestParser() {}

    public static RequestParser getInstance() {
        if (instance == null) {
            instance = new RequestParser();
        }
        return instance;
    }

    public HttpRequest parse(BufferedReader reader) throws IOException {
        this.reader = reader;

        parseStartLine();
        parseHeaders();
        parseBody();

        HttpRequest.Builder product = new HttpRequest.Builder(httpMethod).path(path);
        for (String key : params.keySet())  product = product.param(key, params.get(key));
        for (String key : headers.keySet())  product = product.header(key, headers.get(key));
        return product.cookie(cookie).body(body).build();
    }

    private void parseStartLine() throws IOException {
        startLine = reader.readLine();
        String[] tokens = startLine.split(" ");
        httpMethod = HttpMethod.valueOf(tokens[0].toUpperCase());
        url = tokens[1];

        String[] s = url.split("\\?");
        path = s[0];
        if (hasParams(s)) {
            String[] params = s[1].split("&");
            putParam(params);
        }
    }

    private boolean hasParams(String[] tokens) {
        return tokens.length > 1;
    }

    private void putParam(String[] pairs) {
        for (String pair : pairs) {
            String[] keyAndVal = pair.split("=");
            String key = keyAndVal[0];
            String val = keyAndVal[1];
            params.put(key, val);
        }
    }

    private void parseHeaders() throws IOException {
        String line;
        while (!"".equals((line = reader.readLine()))&& line != null) {
            String[] keyVal = line.split(":", 2);
            if(keyVal[0].equals("Cookie")) {
                cookie = new Cookie(keyVal[1]);
                continue;
            }
            headers.put(keyVal[0].trim(), keyVal[1].trim());
        }
    }

    private void parseBody() throws IOException {
        if (hasBody()) {
            int contentLength = Integer.parseInt(headers.get("Content-Length"));
            String data = IOUtils.readData(reader, contentLength);
            body = URLDecoder.decode(data, StandardCharsets.UTF_8);
        }
    }

    private boolean hasBody() {
        return headers.containsKey("Content-Length");
    }
}
