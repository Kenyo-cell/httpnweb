package org.example;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private final List<String> validPaths =
            List.of("/index.html", "/spring.svg", "/spring.png", "/resources.html", "/styles.css", "/app.js",
                    "/links.html", "/forms.html", "/classic.html", "/events.html", "/events.js");
    private final int poolSize = 64;
    private ExecutorService pool;
    private final Map<Pair<String, String>, Handler> handlers;

    public Server() {
        pool = Executors.newFixedThreadPool(poolSize);
        handlers = new HashMap<>();
    }

    public void start() {
        try (final var serverSocket = new ServerSocket(9999)) {
            while (true) {
                try (final var socket = serverSocket.accept()) {
                    pool.submit(new Thread(() -> handleConnection(socket)));
                }
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
            final var request = new Request(in);

            if (request.getRequestLine().length != 3) {
                socket.close();
                return;
            }


            if (!validPaths.contains(request.getPath())) {
                out.write((
                        "HTTP/1.1 404 Not Found\r\n" +
                                "Content-Length: 0\r\n" +
                                "Connection: close\r\n" +
                                "\r\n"
                ).getBytes());
                out.flush();
                return;
            }

            handlers.get(new Pair<String, String>(request.getMethod(), request.getPath())).handle(new Request(in), out);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addHandler(String method, String path, Handler handler) {
        handlers.put(new Pair(method, path), handler);
    }
}
