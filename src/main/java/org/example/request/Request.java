package org.example.request;

import org.apache.commons.fileupload.FileUploadException;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Request {
    private final String CRLF = "\r\n";

    private final RequestLine requestLine;
    private final Map<String, String> headers;
    private final Body body;


    public Request(List<String> lines) throws IOException, FileUploadException {
        requestLine = new RequestLine(lines.remove(0));

        headers = lines.stream()
                .takeWhile(line -> !line.equals(""))
                .collect(Collectors.toMap(
                        line -> line.split(":")[0].trim(),
                        line -> line.split(":")[1].trim()
                ));

        List<String> body = lines.stream()
                .dropWhile(line -> !line.equals(""))
                .collect(Collectors.toList());

        if (!body.isEmpty())
            body.remove(0);

        this.body = new Body(body, ContentType.valueOfOrDefault(headers.get("Content-Type")));
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

    public Body getBody() {
        return body;
    }
}
