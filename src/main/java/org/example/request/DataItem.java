package org.example.request;

public class DataItem {

    private final String contentType;
    private final String filename;
    private final long size;
    private final byte[] content;

    private DataItem(String name, String filename, long size, byte[] content) {
        this.contentType = name;
        this.filename = filename;
        this.size = size;
        this.content = content;
    }

    public static DataItemBuilder newBuilder() {
        return new DataItemBuilder();
    }

    public String getContentType() {
        return contentType;
    }

    public String getFilename() {
        return filename;
    }

    public long getSize() {
        return size;
    }

    public byte[] getContent() {
        return content;
    }

    public String getStringifyContent() {
        return new String(content);
    }

    public boolean isFile() {
        return filename == null;
    }

    @Override
    public String toString() {
        return "DataItem{" +
               "filename='" + filename + '\'' +
               ", size=" + size +
               ", stringifyContent=" + new String(content).substring(0, size > 100 ? 100 : content.length) +
               "}";
    }

    static class DataItemBuilder {
        private String contentType = null;
        private String filename = null;
        private long size = -1;
        private byte[] content = null;

        public DataItemBuilder() {
        }

        public DataItemBuilder setContentType(String contentType) {
            this.contentType = contentType;
            return this;
        }

        public DataItemBuilder setFilename(String filename) {
            this.filename = filename;
            return this;
        }

        public DataItemBuilder setSize(long size) {
            this.size = size;
            return this;
        }

        public DataItemBuilder setContent(byte[] content) {
            this.content = content;
            return this;
        }

        public DataItem build() {
            return new DataItem(
                    contentType,
                    filename,
                    size,
                    content
            );
        }
    }
}
