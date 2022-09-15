package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.util.Map;

public class HttpRequest {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);

    private HttpMethod httpMethod;
    private String path;
    private Map<String, String> params;
    private Map<String, String> headers;


    public HttpRequest(HttpMethod httpMethod, String path, Map<String, String> params, Map<String, String> headers) {
        this.httpMethod = httpMethod;
        this.path = path;
        this.params = params;
        this.headers = headers;

        logger.debug("Http method : {}", httpMethod);
        logger.debug("Path : {}", path);
        logger.debug("Params : {}", params);
        logger.debug("Headers : {}", headers);
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

}
