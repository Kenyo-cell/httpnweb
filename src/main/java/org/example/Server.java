package org.example;

import org.apache.commons.fileupload.FileUploadException;
import org.example.pair.HandlerKeyPair;
import org.example.request.Request;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.CharBuffer;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private final List<String> validPaths =
            List.of("/index.html", "/spring.svg", "/spring.png", "/resources.html", "/styles.css", "/app.js",
                    "/links.html", "/forms.html", "/classic.html", "/events.html", "/events.js");
    private final int poolSize = 64;
    private final ExecutorService threadPool;
    // First map String in pair key is Method name and second is path for this method
    private final Map<HandlerKeyPair, Handler> methodsHandlers;

    public Server() {
        threadPool = Executors.newFixedThreadPool(poolSize);
        methodsHandlers = new HashMap<>();
    }

    public void listen(int port) {
        try (final var serverSocket = new ServerSocket(port)) {
            while (true) {
                final var socket = serverSocket.accept();
                threadPool.submit(() -> handleConnection(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleConnection(Socket socket) {
        try (final var in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             final var out = new BufferedOutputStream(socket.getOutputStream())) {

            // read only request line for simplicity
            // must be in form GET /path HTTP/1.1
            final Request request;

            try {
                request = new Request(readAllStreamInfo(in));
            } catch (IllegalArgumentException | FileUploadException e) {
                System.out.println(e.getMessage());
                socket.close();
                return;
            }

            if (!validPaths.contains(request.getPath())
                && !methodsHandlers.containsKey(new HandlerKeyPair(request.getMethod(), request.getPath()))) {
                out.write((
                        "HTTP/1.1 404 Not Found\r\n" +
                        "Content-Length: 48\r\n" +
                        "Connection: close\r\n" +
                        "Content-Type: text/html\r\n" +
                        "\r\n" +
                        "<html><head></head><body>Not Found</body></html>\n"
                ).getBytes());
                out.flush();
                return;
            }

            methodsHandlers.get(new HandlerKeyPair(request.getMethod(), request.getPath()))
                    .handle(request, out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<String> readAllStreamInfo(BufferedReader in) throws IOException {
        int bufferSize = 2 << 9;
        CharBuffer buffer = CharBuffer.allocate(bufferSize);
        int readed;
        StringBuilder builder = new StringBuilder();

        while ((readed = in.read(buffer)) > 0) {
            buffer.flip();

            char[] dst = new char[readed];
            buffer.get(dst);

            builder.append(dst);

            if (readed != bufferSize) break;

            buffer.clear();
        }

        return new LinkedList<>(Arrays.asList(builder.toString().split("\r\n")));
    }

    public void addHandler(String method, String path, Handler handler) {
        methodsHandlers.put(new HandlerKeyPair(method, path), handler);
    }
}
