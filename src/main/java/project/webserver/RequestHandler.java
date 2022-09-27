package project.webserver;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import project.http.HttpRequest;
import project.http.HttpResponse;
import project.http.RequestParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import project.controller.ControllerMapper;
import project.controller.Controller;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    private RequestParser requestParser = new RequestParser();
    private ControllerMapper controllerMapper = ControllerMapper.getInstance();

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            HttpRequest httpRequest = requestParser.parse(br);
            Controller controller = controllerMapper.controllerMapping(httpRequest.getPath());
            HttpResponse httpResponse = controller.process(httpRequest);
            writeResponse(out, httpResponse);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void writeResponse(OutputStream out, HttpResponse httpResponse) {
        try {
            DataOutputStream dos = new DataOutputStream(out);
            byte[] data = httpResponse.convertToByteData();
            dos.write(data, 0, data.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
