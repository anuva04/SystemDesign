package org.anuva04;

class Message {
    private final String topicId;
    private final String content;
    private final long timestamp;

    public Message(String topicId, String content) {
        this.topicId = topicId;
        this.content = content;
        this.timestamp = System.currentTimeMillis();
    }

    public String getTopicId() {
        return topicId;
    }

    public String getContent() {
        return content;
    }
}