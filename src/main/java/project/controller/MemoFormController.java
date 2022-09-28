package project.controller;

import project.http.HttpRequest;
import project.http.HttpResponse;
import project.http.HttpStatus;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class MemoFormController implements Controller {

    @Override
    public HttpResponse process(HttpRequest httpRequest) {
        try {
            return isLogin(httpRequest) ? createMemoForm() : loginForm() ;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isLogin(HttpRequest httpRequest) {
        String cookie = httpRequest.getHeader("Cookie");
        return cookie != null && cookie.equals("logined=true");
    }

    private HttpResponse createMemoForm() throws IOException {
        byte[] data = Files.readAllBytes(new File("./webapp/qna/form.html").toPath());
        return new HttpResponse.Builder(HttpStatus.SUCCESSFUL)
                .header("Content-Type", "text/html;charset=utf-8")
                .header("Content-Length", String.valueOf(data.length))
                .body(data)
                .build();
    }

    private HttpResponse loginForm() {
        return new HttpResponse.Builder(HttpStatus.REDIRECT)
                .header("Location", "/user/login.html")
                .build();
    }
}
