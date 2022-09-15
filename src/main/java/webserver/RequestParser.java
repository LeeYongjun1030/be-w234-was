package webserver;

import http.HttpMethod;
import http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RequestParser {
    private static final Logger logger = LoggerFactory.getLogger(RequestParser.class);

    public HttpRequest parse(BufferedReader reader) throws IOException {

        //start-line
        String startLine = reader.readLine();
        HttpMethod httpMethod = extractHttpMethod(startLine);
        String url = extractUrl(startLine);
        String path = extractPath(url);
        Map<String, String> params = extractParams(url);

        //headers
        Map<String, String> headers = new HashMap<>();
        String line;
        while (!"".equals((line = reader.readLine()))&& line != null) {
            String[] keyVal = line.split(":", 2);
            headers.put(keyVal[0], keyVal[1]);
        }
//            //body
//            int contentLength = Integer.parseInt(headers.get("contentLength"));
//            IOUtils.readData(reader, contentLength)

        return new HttpRequest(httpMethod, path, params, headers);
    }

    private HttpMethod extractHttpMethod(String startLine) {
        String[] tokens = startLine.split(" ");
        return HttpMethod.valueOf(tokens[0].toUpperCase());
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
