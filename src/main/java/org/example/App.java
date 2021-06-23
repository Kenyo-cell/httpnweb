package org.example;

import java.io.*;

public class App {
    public static void main(String[] args) throws IOException {
        Server server = new Server();

        server.addHandler("GET", "/messages", (request, out) -> { });
        server.addHandler("POST", "/messages", (request, out) -> { });

        server.start();
    }
}