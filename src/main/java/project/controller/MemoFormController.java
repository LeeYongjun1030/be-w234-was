package project.controller;

import project.http.*;
import project.jpa.entity.Memo;
import project.jpa.entity.User;
import project.jpa.repository.MemoRepository;
import project.jpa.repository.UserRepository;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class MemoFormController implements Controller {

    private HttpRequest httpRequest;
    private UserRepository userRepository;
    private MemoRepository memoRepository;

    public MemoFormController(UserRepository userRepository, MemoRepository memoRepository) {
        this.userRepository = userRepository;
        this.memoRepository = memoRepository;
    }

    @Override
    public HttpResponse process(HttpRequest httpRequest) {
        try {
            this.httpRequest = httpRequest;
            HttpMethod httpMethod = httpRequest.getHttpMethod();

            if (httpMethod.equals(HttpMethod.POST))
                return createAndSaveMemo();

            return isLogin() ? createMemoForm() : loginForm() ;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isLogin() {
        Cookie cookie = httpRequest.getCookie();
        return cookie != null && cookie.get("logined") != null;
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

    private HttpResponse createAndSaveMemo() {
        String contents = readContentsFromReq();
        Memo memo = createMemo(contents);
        memoRepository.save(memo);

        return new HttpResponse.Builder(HttpStatus.REDIRECT)
                .header("Location", "/index.html")
                .build();
    }

    private String readContentsFromReq() {
        String[] s = httpRequest.getBody().split("=");
        return s[1];
    }

    private Memo createMemo(String contents) {
        User user = findUser();
        return new Memo(user, contents);
    }

    private User findUser() {
        Cookie cookie = httpRequest.getCookie();
        String userId = cookie.get("logined");
        return userRepository.findById(userId);
    }

}
