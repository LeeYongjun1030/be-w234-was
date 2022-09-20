package controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ControllerMapper {

    private Map<String, Controller> handlers = new HashMap<>();

    public ControllerMapper() {
        handlers.put("/user/create", new SignUpController());
        handlers.put("/user/login", new SignInController());
    }

    public Controller controllerMapping(String path) {
        File file = new File("./webapp" + path);
        if (file.isFile()) {
            String[] s = path.split("\\.");
            if (s[s.length - 1].equals("css")) {
                return new StyleSheetController();
            }
            return new StaticHtmlController();
        }

        return handlers.get(path);
    }
}
