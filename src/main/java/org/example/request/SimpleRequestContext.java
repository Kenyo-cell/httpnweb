package org.example.request;

import org.apache.commons.fileupload.RequestContext;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public class SimpleRequestContext implements RequestContext {
    private final Charset charset;
    private final String contentType;
    private final byte[] content;

    public SimpleRequestContext(String contentType, byte[] content) {
        charset = Charset.defaultCharset();
        this.contentType = contentType;
        this.content = content;
    }

    @Override
    public String getCharacterEncoding() {
        return charset.toString();
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Deprecated
    @Override
    public int getContentLength() {
        return content.length;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(content);
    }
}
