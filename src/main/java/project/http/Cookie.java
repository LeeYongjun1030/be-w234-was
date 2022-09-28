package project.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.Map;

public class Cookie {

    private static final Logger logger = LoggerFactory.getLogger(Cookie.class);

    private Map<String, String> properties = new HashMap<>();

    public Cookie(String query) {
        String[] keyAndValuePairs = query.split(";");
        for (String keyAndValuePair : keyAndValuePairs) {
            String[] keyAndVal = keyAndValuePair.split("=");
            properties.put(keyAndVal[0].trim(), keyAndVal[1].trim());
        }
    }

    public Cookie(String name, String val, String path) {
        properties.put(name, val);
        properties.put("Path", path);
    }

    public String get(String key) {
        return properties.get(key);
    }
}
