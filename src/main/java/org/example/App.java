package org.example;


import org.example.request.ContentType;

import java.io.*;

public class App {
    public static void main(String[] args) throws IOException {
        Server server = new Server();

        server.addHandler("GET", "/messages", (request, out) -> {
            System.out.printf("%s%n%s%n", request.getPath(), request.getRequestLine().getQueryParameters());
            out.write(("HTTP/1.1 200 OK\r\n" +
                       "Content-Length: 43\r\n" +
                       "Connection: close\r\n" +
                       "Content-Type: text/html\r\n" +
                       "\r\n" +
                       "<html><head></head><body>JAVA</body></html>\n"
            ).getBytes());
            out.flush();
        });
        server.addHandler("POST", "/messages", (request, out) -> {
        });

        server.listen(9999);
    }
}