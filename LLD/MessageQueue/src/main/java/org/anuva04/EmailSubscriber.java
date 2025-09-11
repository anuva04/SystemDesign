package org.anuva04;

class EmailSubscriber implements ISubscriber {
    private final String id;

    public EmailSubscriber(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void consume(Message message) {
        try {
            Thread.sleep(2000);
            System.out.println("[Subscriber: " + id + "] received message for topic '" + message.getTopicId() + "': '" + message.getContent() + "'. Sending email...");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object that) {
        if(this == that) return true;
        if(that == null || this.getClass() != that.getClass()) return false;
        return id.equals(((EmailSubscriber) that).id);
    }
}