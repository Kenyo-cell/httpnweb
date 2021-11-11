package org.example.request;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Request {
    private final String CRLF = "\r\n";

    private final RequestLine requestLine;
    private final Map<String, String> headers;
    private Map<String, String> body;


    public Request(List<String> lines) {
        requestLine = new RequestLine(lines.remove(0));

        headers = lines.stream()
                .takeWhile(line -> !line.equals(""))
                .collect(Collectors.toMap(
                        line -> line.split(":")[0],
                        line -> line.split(":")[1]
                ));

        List<String> body = lines.stream()
                .dropWhile(line -> !line.equals(""))
                .filter(line -> !line.equals(""))
                .collect(Collectors.toList());
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public String getMethod() {
        return requestLine.getMethod();
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
}
