package org.anuva04;

interface ISubscriber {
    void consume(Message message);
    String getId();
}