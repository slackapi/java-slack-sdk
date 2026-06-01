package com.slack.api.methods;

class ChatStreamBuffer {

    private final int bufferSize;
    private final StringBuilder buffer = new StringBuilder();

    ChatStreamBuffer(int bufferSize) {
        if (bufferSize <= 0) {
            throw new IllegalArgumentException("bufferSize must be greater than 0");
        }
        this.bufferSize = bufferSize;
    }

    int getBufferSize() {
        return bufferSize;
    }

    int length() {
        return buffer.length();
    }

    void append(String markdownText) {
        buffer.append(markdownText);
    }

    void appendIfNotNull(String markdownText) {
        if (markdownText != null) {
            append(markdownText);
        }
    }

    boolean isReadyToFlush() {
        return length() >= bufferSize;
    }

    String getMarkdownText() {
        return buffer.toString();
    }

    String drain() {
        String markdownText = getMarkdownText();
        clear();
        return markdownText;
    }

    void restore(String markdownText) {
        buffer.insert(0, markdownText);
    }

    void clear() {
        buffer.setLength(0);
    }
}
