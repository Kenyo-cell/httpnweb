package org.example.request;

import java.util.HashMap;
import java.util.Map;

public enum ContentType {
    X_WWW_FORM_URLENCODED("application/x-www-form-urlencoded"),
    MULTIPART_FORM_DATA("multipart/form-data"),
    TEXT_PLAIN("text/plain"),
    NULL(null);

    private final String enumName;
    private final Map<String, String> additionalInfo = new HashMap<>();
    private String name;

    ContentType(String name) {
        this.enumName = name;
    }

    public static ContentType valueOfOrDefault(String name) {
        if (name == null) return NULL;

        for (ContentType contentType : ContentType.class.getEnumConstants()) {
            if (name.contains(contentType.enumName)) {
                contentType.processAdditionalInfo(contentType, name);
                contentType.name = name;
                return contentType;
            }
        }
        return TEXT_PLAIN;
    }

    public String getEnumName() {
        return enumName;
    }

    public String getName() {
        return name;
    }

    private void processAdditionalInfo(ContentType contentType, String parameter) {
        if (contentType == ContentType.MULTIPART_FORM_DATA) {
            String boundary = parameter.split(";")[1].trim().split("=")[1];
            additionalInfo.put("boundary", boundary);
        }
    }

    public String getAdditionalInfoByKey(String key) {
        return additionalInfo.get(key);
    }
}
