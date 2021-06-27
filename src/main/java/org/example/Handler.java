package org.example;

import java.io.OutputStream;

@FunctionalInterface
public interface Handler {
    public void handle(Request request, OutputStream out);
}
