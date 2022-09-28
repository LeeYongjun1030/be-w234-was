package project.controller;

import project.jpa.repository.MemoRepository;
import project.jpa.repository.UserRepository;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ControllerMapper {

    private Map<String, Controller> handlers = new HashMap<>();
    private static ControllerMapper instance;

    private ControllerMapper() {
        UserRepository userRepository = new UserRepository();
        MemoRepository memoRepository = new MemoRepository();

        handlers.put("/user/create", new SignUpController(userRepository));
        handlers.put("/user/login", new SignInController(userRepository));
        handlers.put("/user/list", new UserListController(userRepository));
        handlers.put("/qna", new MemoFormController(userRepository, memoRepository));
    }

    public static ControllerMapper getInstance() {
        if (instance == null) {
            instance = new ControllerMapper();
        }
        return instance;
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
