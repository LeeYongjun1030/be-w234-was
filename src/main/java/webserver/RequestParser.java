package webserver;

import java.util.HashMap;
import java.util.Map;

public class RequestParser {

    private String startLine;

    public RequestParser(String startLine) {
        this.startLine = startLine;
    }

    public ProcessedRequest parse() {
        String url = extractUrl(startLine);
        String path = extractPath(url);
        Map<String, String> params = extractParams(url);

        return new ProcessedRequest(url, path, params);
    }

    private String extractUrl(String startLine) {
        String[] tokens = startLine.split(" ");
        return tokens[1];
    }

    private String extractPath(String url) {
        String[] tokens = url.split("\\?");
        return tokens[0];
    }

    private Map<String, String> extractParams(String url) {
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
}
