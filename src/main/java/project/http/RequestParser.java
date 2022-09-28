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

public class RequestParser {
    private static final Logger logger = LoggerFactory.getLogger(RequestParser.class);

    private BufferedReader reader;
    private String startLine;
    private String url;

    private HttpMethod httpMethod;

    private String path;

    private Map<String, String> params = new HashMap<>();

    private Map<String, String> headers = new HashMap<>();

    private Cookie cookie;

    private String body;

    public RequestParser(BufferedReader reader) {
        this.reader = reader;
    }

    public HttpRequest parse() throws IOException {
        parseStartlineAndUrl();
        parseHttpMethod();
        parsePath();
        parseParams();
        parseHeaders();
        parseBody();
        return createHttpRequestFromParsedData();
    }

    private void parseStartlineAndUrl() throws IOException {
        this.startLine = reader.readLine();
        String[] tokens = startLine.split(" ");
        this.url = tokens[1];
    }

    private void parseHttpMethod() {
        String[] tokens = startLine.split(" ");
        this.httpMethod = HttpMethod.valueOf(tokens[0].toUpperCase());
    }

    private void parsePath() {
        String[] tokens = url.split("\\?");
        this.path = tokens[0];
    }

    private void parseParams() {
        String[] tokens = url.split("\\?");
        if (hasParams(tokens)) {
            String[] pairs = getParamPairs(tokens);
            putParam(pairs);
        }
    }

    private boolean hasParams(String[] tokens) {
        return tokens.length > 1;
    }

    private String[] getParamPairs(String[] tokens) {
        String queryParams = tokens[1];
        String[] pairs = queryParams.split("&");
        return pairs;
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
                parseCookie(keyVal[1]);
                continue;
            }
            headers.put(keyVal[0].trim(), keyVal[1].trim());
        }
    }

    private void parseCookie(String cookieQuery) {
        cookie = new Cookie(cookieQuery);
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

    private HttpRequest createHttpRequestFromParsedData() {
        HttpRequest.Builder intermediateProduct = new HttpRequest.Builder(httpMethod).path(path);
        intermediateProduct = setParams(intermediateProduct);
        intermediateProduct = setHeaders(intermediateProduct);
        intermediateProduct = setCookie(intermediateProduct);
        intermediateProduct = setBody(intermediateProduct);
        HttpRequest httpRequest = intermediateProduct.build();
        return httpRequest;
    }

    private HttpRequest.Builder setParams(HttpRequest.Builder b) {
        for (String key : params.keySet()) {
            String val = params.get(key);
            b = b.param(key, val);
        }
        return b;
    }

    private HttpRequest.Builder setHeaders(HttpRequest.Builder b) {
        for (String key : headers.keySet()) {
            String val = headers.get(key);
            b = b.header(key, val);
        }
        return b;
    }

    private HttpRequest.Builder setCookie(HttpRequest.Builder b) {
        b.cookie(cookie);
        return b;
    }

    private HttpRequest.Builder setBody(HttpRequest.Builder b) {
        return b.body(body);
    }
}
