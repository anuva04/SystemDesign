/**
 * This pattern is used when multiple objects needs to be updated (called subscribers) when a certain change occurs in an object's state (called observable).
 * Instead of updating all subscribers of the change, or letting subscribers poll the publisher continuously,
 * the subscribers can subscribe to a system (called publisher) which will notify them whenever the observable's state changes.
 * In this example, channels are the observables.
 * YoutubeNotification class is the publisher which stores a list of all Subscribers for each channel.
 * User class implements Subscriber so that its notify() method can be used to notify it.
 * Whenever a new video is added to the channel, publisher notifies all subscribers.
 */

import java.util.*;
class ObserverPattern {
    public static void main(String[] args) {
        YoutubeNotification ytNotification = new YoutubeNotification();
        User user1 = new User("user1");
        User user2 = new User("user2");
        ytNotification.addSubscriber("channel1", user1);
        ytNotification.addSubscriber("channel1", user2);

        User user3 = new User("user3");
        ytNotification.addSubscriber("channel2", user3);

        ytNotification.addVideo("channel1");
        ytNotification.addVideo("channel2");

        ytNotification.removeSubscriber("channel1", user1);
        ytNotification.addVideo("channel1");
    }
}

class YoutubeNotification {
    private Map<String, List<Subscriber>> channelSubsMap;

    public YoutubeNotification() {
        this.channelSubsMap = new HashMap<>();
    }
    public void addSubscriber(String channel, Subscriber subscriber) {
        if(!channelSubsMap.containsKey(channel)) {
            channelSubsMap.put(channel, new ArrayList<>());
        }
        channelSubsMap.get(channel).add(subscriber);
    }

    public void removeSubscriber(String channel, Subscriber subscriber) {
        channelSubsMap.get(channel).remove(subscriber);
    }

    public void addVideo(String channel) {
        for(Subscriber s : channelSubsMap.get(channel)) s.notify(channel);
    }
}

interface Subscriber {
    public void notify(String channel);
}

class User implements Subscriber {
    public String name;

    public User(String name) {
        this.name = name;
    }

    @Override
    public void notify(String channel) {
        System.out.println(this.name + ", " + channel + " has posted a new video!");
    }
}