package org.anuva04;

import java.util.concurrent.ConcurrentHashMap;

class MessageQueueService {
    private final ConcurrentHashMap<String, Topic> topics;
    private final ConcurrentHashMap<String, TopicMessageService> topicServices;

    public MessageQueueService() {
        this.topics = new ConcurrentHashMap<>();
        this.topicServices = new ConcurrentHashMap<>();
        System.out.println("MessageQueueService initialized");
    }

    public Topic createTopic(String topicId) {
        Topic topic = topics.computeIfAbsent(topicId, Topic::new);
        topicServices.computeIfAbsent(topicId, k -> new TopicMessageService(topic));
        return topic;
    }

    public void publish(String topicId, String content) {
        Topic topic = topics.get(topicId);
        if(topic == null) {
            System.out.println("Error: Topic '" + topicId + "' does not exist");
        } else {
            Message message = new Message(topicId, content);
            System.out.println("Publisher publishing message to topic " + topicId + " ...");
            topic.publish(message);
        }
    }

    public void close() {
        System.out.println("Shutting down all topic services...");
        for(TopicMessageService service : topicServices.values()) {
            service.shutdown();
        }
        System.out.println("MessageQueue shut down successfully");
    }
}