package project.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);

    private HttpMethod httpMethod;
    private String path;
    private Map<String, String> params;
    private Map<String, String> headers;
    private String body;


    private HttpRequest(Builder builder) {
        this.httpMethod = builder.httpMethod;
        this.path = builder.path;
        this.params = builder.params;
        this.headers = builder.headers;
        this.body = builder.body;

        logger.debug("Http method : {}", httpMethod);
        logger.debug("Path : {}", path);
        logger.debug("Params : {}", params);
        logger.debug("Headers : {}", headers);
        logger.debug("Body : {}", body);
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public String getPath() {
        return path;
    }

    public String getParam(String key) {
        return params.get(key);
    }

    public String getHeader(String key) {
        return headers.get(key);
    }

    public String getBody() {
        return body;
    }


    //Builder class
    public static class Builder {

        private HttpMethod httpMethod;
        private String path;
        private Map<String, String> params = new HashMap<>();
        private Map<String, String> headers = new HashMap<>();
        private String body;

        public Builder(HttpMethod httpMethod) {
            this.httpMethod = httpMethod;
        }

        public Builder path(String path) {
            this.path = path;
            return this;
        }

        public Builder param(String key, String val) {
            this.params.put(key, val);
            return this;
        }

        public Builder header(String key, String val) {
            this.headers.put(key, val);
            return this;
        }

        public Builder body(String body) {
            this.body = body;
            return this;
        }

        public HttpRequest build() {
            return new HttpRequest(this);
        }
    }
}
