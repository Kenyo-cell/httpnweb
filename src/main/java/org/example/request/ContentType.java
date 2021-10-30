package org.example.request;

public enum ContentType {
    X_WWW_FORM_URLENCODED("application/x-www-form-urlencoded"),
    MULTIPART_FORM_DATA("multipart/form-data"),
    TEXT_PLAIN("text/plain");

    private final String name;
    private ContentType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ContentType valueOfOrDefault(String name) {
        for (ContentType contentType : ContentType.class.getEnumConstants()) {
            if (contentType.name.equals(name)) return contentType;
        }
        return TEXT_PLAIN;
    }

}
