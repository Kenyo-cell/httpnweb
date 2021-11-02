package org.example.request;

import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.http.client.utils.URLEncodedUtils;
import org.example.pair.NameItemPair;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Body {
    private final ContentType contentType;
    private final List<NameItemPair> bodyContent = new LinkedList<>();

    public Body(List<String> lines) throws IOException, FileUploadException {
        this(lines, ContentType.TEXT_PLAIN);
    }

    public Body(List<String> lines, ContentType type) throws FileUploadException {
        contentType = type;
        switch (contentType) {
            case MULTIPART_FORM_DATA -> processMultipartData(lines);
            case X_WWW_FORM_URLENCODED -> processUrlEncodedData(lines);
            case TEXT_PLAIN -> processTextPlainData(lines);
            default -> System.out.println("Undefined Content-Type");
        }
    }

    private void processTextPlainData(List<String> lines) {
        String data = String.join("\r\n", lines);
        System.out.println(data);
    }

    private void processUrlEncodedData(List<String> lines) {
        URLEncodedUtils.parse(lines.get(0), Charset.defaultCharset())
                .forEach(pair -> bodyContent.add(new NameItemPair(
                                        pair.getName(),
                                        DataItem.newBuilder()
                                                .setContent(pair.getValue().getBytes(StandardCharsets.UTF_8))
                                                .setSize(pair.getValue().length())
                                                .build()
                                )
                        )
                );

    }

    private void processMultipartData(List<String> lines) throws FileUploadException {
        String l = String.join("\r\n", lines);

        FileUploadBase upload = new FileUpload();
        FileItemFactory factory = new DiskFileItemFactory();
        upload.setFileItemFactory(factory);

        var items = upload.parseRequest(new SimpleRequestContext(contentType.getName(), l.getBytes()));

        items.forEach(a -> {
            System.out.println(new String(a.get()));
            System.out.println(a.getContentType());
            System.out.println(a.getFieldName());
            System.out.println(a.getName());
            System.out.println(a.getSize());
            System.out.println(a.getString());
        });
    }

    private void collectByteData(List<String> lines) {

    }

    public boolean isEmpty() {
        return contentType.equals(ContentType.NULL);
    }

    public List<NameItemPair> getBodyParameter(String key) {
        return bodyContent.stream()
                .filter(pair -> pair.getFirst().equals(key))
                .collect(Collectors.toList());
    }

    public List<NameItemPair> getBodyParameters() {
        return bodyContent;
    }
}
