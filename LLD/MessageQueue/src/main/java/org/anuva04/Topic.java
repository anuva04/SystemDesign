package org.anuva04;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

class Topic {
    private final String topicId;
    private final Set<ISubscriber> subscribers;
    private final Map<String, AtomicInteger> offsets;
    private final List<Message> queue;

    public Topic(String topicId) {
        this.topicId = topicId;
        subscribers = new HashSet<>();
        offsets = new ConcurrentHashMap<>();
        queue = new ArrayList<>();
        System.out.println("New topic created: ID - " + topicId);
    }

    public Set<ISubscriber> getSubscribers() {
        return this.subscribers;
    }

    public void subscribe(ISubscriber subscriber) {
        synchronized (this) {
            if(!subscribers.contains(subscriber)) {
                subscribers.add(subscriber);
                offsets.put(subscriber.getId(), new AtomicInteger(0));
                System.out.println("Subscriber " + subscriber.getId() + " subscribed to topic " + topicId);
                this.notifyAll();
            } else {
                System.out.println("Subscriber " + subscriber.getId() + " is already subscribed to topic " + topicId);
                this.notifyAll();
            }
        }
    }

    public void unsubscribe(ISubscriber subscriber) {
        synchronized (this) {
            if(subscribers.contains(subscriber)) {
                subscribers.remove(subscriber);
                offsets.remove(subscriber);
                System.out.println("Subscriber '" + subscriber.getId() + "' unsubscribed from topic '" + topicId);
            } else {
                System.out.println("Error: Subscriber " + subscriber.getId() + " is not subscribed to topic " + topicId);
            }
        }
    }

    public void publish(Message message) {
        synchronized (this) {
            queue.add(message);
            this.notifyAll();
        }
    }

    public void resetOffset(ISubscriber subscriber, int newOffset) {
        AtomicInteger currOffset = offsets.get(subscriber.getId());
        if(currOffset == null) {
            System.out.println("Error: Subscriber '" + subscriber.getId() + "' is not subscribed to topic " + topicId);
        } else {
            System.out.println("Resetting offset for subscriber '" + subscriber.getId() + "' from " + currOffset.get() + " to " + newOffset);
            currOffset.set(newOffset);

            synchronized (this) {
                this.notifyAll();
            }
        }
    }

    public boolean updateOffset(ISubscriber subscriber, int expectedOffset, int newOffset) {
        AtomicInteger currOffset = offsets.get(subscriber.getId());
        return currOffset != null && currOffset.compareAndSet(expectedOffset, newOffset);
    }

    public int getOffset(ISubscriber subscriber) {
        AtomicInteger currOffset = offsets.get(subscriber.getId());
        return currOffset == null ? 0 : currOffset.get();
    }

    public int getQueueSize() {
        return queue.size();
    }

    public Message getMessage(int offset) {
        if(offset >= 0 && offset < queue.size()) return queue.get(offset);
        return null;
    }

    public String getId() {
        return topicId;
    }
}