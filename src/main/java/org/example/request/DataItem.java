package org.example.request;

public class DataItem {

    static class DataItemBuilder {
        private String fileExtension = null;
        private String filename = null;
        private long size = -1;
        private byte[] content = null;

        public DataItemBuilder() {
        }

        public DataItemBuilder setFileExtension(String fileExtension) {
            this.fileExtension = fileExtension;
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
                    fileExtension,
                    filename,
                    size,
                    content
            );
        }
    }

    private String fileExtension;
    private String filename;
    private long size;
    private byte[] content;

    private DataItem(String name, String filename, long size, byte[] content) {
        this.fileExtension = name;
        this.filename = filename;
        this.size = size;
        this.content = content;
    }

    public static DataItemBuilder newBuilder() {
        return new DataItemBuilder();
    }

    public String getFileExtension() {
        return fileExtension;
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
               ", stringifyContent=" + new String(content) +
               "}";
    }
}
