package org.example.request;

import java.util.List;

public class Body {
    private final ContentType contentType;

    public Body(List<String> lines) {
        contentType = ContentType.TEXT_PLAIN;
    }

    public Body(List<String> lines, ContentType type) {
        contentType = type;
//        URLEncodedUtils.parse();
    }
}
