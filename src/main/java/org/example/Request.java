package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;

public class Request {
    private final String CRLF = "\r\n";

    private String method;
    private String path;
    private String[] requestLine;
    private String[] headers;
    private String[] body;


    public Request(BufferedReader in) {
        try {
            requestLine = in.readLine().split(" ");

            StringBuilder headersBuilder = new StringBuilder();
            String str;
            while (!(str = in.readLine()).equals("")) {
                headersBuilder.append(str + CRLF);
            }
            headers = headersBuilder.toString().split(CRLF);

            StringBuilder bodyBuilder = new StringBuilder();
            while ((str = in.readLine()) != null) {
                bodyBuilder.append(str);
            }
            body = bodyBuilder.toString().split(CRLF);

            method = requestLine[0];
            path = requestLine[1];
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public String toString() {
        return "RL: %s\nH: %s\nB: %s\n".formatted(
                Arrays.toString(requestLine), Arrays.toString(headers), Arrays.toString(body));
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String[] getRequestLine() {
        return requestLine;
    }

    public String[] getHeaders() {
        return headers;
    }

    public String[] getBody() {
        return body;
    }
}
