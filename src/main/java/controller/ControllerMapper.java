package controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ControllerMapper {

    private Map<String, Controller> handlers = new HashMap<>();

    public ControllerMapper() {
        // init
        handlers.put("/user/create", new SignUpController());
    }

    public Controller controllerMapping(String path) {
        if (Objects.equals(path, "/css/style.css")) {
            return new StyleSheetController();
        }
        File file = new File("./webapp" + path);
        if (file.isFile()) { //정적 파일
            return new StaticHtmlController();
        }

        return handlers.get(path);
    }
}
