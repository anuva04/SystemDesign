package org.anuva04;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.ExecutorService;

class TopicMessageService implements Runnable {
    private final Topic topic;
    private final Map<ISubscriber, ExecutorService> subscriberExecutors;
    private final Thread serviceThread;
    private final AtomicBoolean running = new AtomicBoolean(true);

    public TopicMessageService(Topic topic) {
        this.topic = topic;
        this.subscriberExecutors = new ConcurrentHashMap<>();
        this.serviceThread = new Thread(this, "TopicService-" + topic.getId());
        this.serviceThread.start();
        System.out.println("TopicMessageService for '" + topic.getId() + "' initialized");
    }

    @Override
    public void run() {
        while(running.get()) {
            synchronized (topic) {
                try {
                    while(hasConsumedAll()) {
                        System.out.println("TopicMessageService for " + topic.getId() + " is waiting for new messages...");
                        topic.wait();
                    }
                } catch(InterruptedException e) {
                    Thread.currentThread().interrupt();
                    running.set(false);
                    break;
                }
            }

            Set<ISubscriber> subscriberList = topic.getSubscribers();
            for(ISubscriber subscriber : subscriberList) {
                subscriberExecutors.computeIfAbsent(subscriber, k -> Executors.newSingleThreadExecutor());
            }
            subscriberExecutors.keySet().retainAll(subscriberList);

            for(ISubscriber subscriber : subscriberList) {
                subscriberExecutors.get(subscriber).submit(() -> {
                    int offset = topic.getOffset(subscriber);
                    while(offset < topic.getQueueSize()) {
                        final Message message = topic.getMessage(offset);
                        final int currentOffset = offset;
                        if(message != null) {
                            subscriber.consume(message);
                            if (topic.updateOffset(subscriber, currentOffset, currentOffset + 1)) {
                                // Success
                            } else {
                                System.out.println("Offset was reset by another thread, this message will be re-consumed on the next loop.");
                            }
                        }
                        offset++;
                    }
                });
            }
        }
    }

    private boolean hasConsumedAll() {
        Set<ISubscriber> subscribers = topic.getSubscribers();
        if (subscribers.isEmpty()) return true;

        for (ISubscriber subscriber : subscribers) {
            if (topic.getOffset(subscriber) < topic.getQueueSize()) {
                return false;
            }
        }
        return true;
    }

    public void shutdown() {
        running.set(false);
        serviceThread.interrupt();

        for(ExecutorService exec : subscriberExecutors.values()) {
            exec.shutdown();
        }

        try {
            for(ExecutorService exec : subscriberExecutors.values()) {
                if(!exec.awaitTermination(5, TimeUnit.SECONDS)) {
                    exec.shutdownNow();
                }
            }
        } catch (InterruptedException e) {
            for(ExecutorService exec : subscriberExecutors.values()) {
                exec.shutdownNow();
            }
            Thread.currentThread().interrupt();
        }

        System.out.println("TopicMessageService for '" + topic.getId() + "' shut down.");
    }
}