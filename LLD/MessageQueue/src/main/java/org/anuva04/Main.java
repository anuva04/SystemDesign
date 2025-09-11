package org.anuva04;

class Main {
    public static void main(String[] args) throws InterruptedException {
        MessageQueueService mq = new MessageQueueService();

        Topic sportsTopic = mq.createTopic("sports");
        Topic financeTopic = mq.createTopic("finance");

        ISubscriber userA = new EmailSubscriber("UserA_Email");
        ISubscriber userB = new PushNotificationSubscriber("UserB_Push");
        ISubscriber userC = new EmailSubscriber("UserC_Email");
        ISubscriber userCDupe = new EmailSubscriber("UserC_Email");

        sportsTopic.subscribe(userA);
        sportsTopic.subscribe(userB);
        financeTopic.subscribe(userC);
        financeTopic.subscribe(userCDupe);

        System.out.println("\n--- Publishing initial messages ---");
        mq.publish("sports", "Final score: Team Alpha 3 - Team Beta 1!");
        mq.publish("sports", "Player of the game is Sarah Jones!");
        mq.publish("finance", "Stock market closed at an all-time high.");

        System.out.println("\nMain thread waiting for initial messages to be consumed...");
        Thread.sleep(5000);

        System.out.println("\n--- Resetting offset for UserA and publishing new message ---");
        sportsTopic.resetOffset(userA, 0);
        mq.publish("sports", "New transfer rumors for the upcoming season.");

        System.out.println("\nMain thread waiting for re-consumption and new message consumption...");
        Thread.sleep(5000);

        mq.publish("sports", "New transfer rumors for the upcoming season again.");

        Thread.sleep(10000);

        System.out.println("\n--- UserB unsubscribing from Sports ---");
        sportsTopic.unsubscribe(userB);

        System.out.println("\n--- Publishing to Sports Topic again (UserB should not receive it) ---");
        mq.publish("sports", "Important announcement from the league.");

        Thread.sleep(5000);
        mq.close();
    }
}