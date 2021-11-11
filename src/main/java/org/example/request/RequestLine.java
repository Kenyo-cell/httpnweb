package org.example.request;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.Collectors;

public class RequestLine {
    private final String method;
    private final String protocol;
    private final String path;
    private List<NameValuePair> queryParameters;

    public RequestLine(String line) {
        final var splitRequestLine = line.split(" ");

        if (splitRequestLine.length != 3)
            throw new IllegalArgumentException("Invalid request line found %s".formatted(line));

        method = splitRequestLine[0];
        protocol = splitRequestLine[2];

        final String[] pathSplit = splitRequestLine[1].split("\\?");

        path = pathSplit[0];
        if (pathSplit.length > 1)
            queryParameters = URLEncodedUtils.parse(pathSplit[1], Charset.defaultCharset());
    }

    public String getMethod() {
        return method;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getPath() {
        return path;
    }

    public List<NameValuePair> getQueryParameters() throws NullPointerException {
        return queryParameters;
    }

    public List<NameValuePair> getQueryParameter(String name) throws NullPointerException {
        return queryParameters.stream()
                .filter(pair -> pair.getName().equals(name))
                .collect(Collectors.toList());
    }
}
