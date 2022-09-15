package webserver;

import java.util.Map;

public class ProcessedRequest {

    private String url;
    private String path;
    private Map<String, String> params;

    public ProcessedRequest(String url, String path, Map<String, String> params) {
        this.url = url;
        this.path = path;
        this.params = params;
    }

    public String getUrl() {
        return url;
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getParams() {
        return params;
    }
}
