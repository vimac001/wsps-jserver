package wsps;

import java.util.ArrayList;

public class Channel {
    protected ArrayList<Subscriber> subscribers;
    protected String name;

    /**
     * Initializes a new channel object.
     * @param name Channel name.
     */
    public Channel(String name) {
        this.name = name;
        this.subscribers = new ArrayList<>();
    }

    /**
     * Returns the name of this channel object.
     * @return Channel name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the amount of subscribing objects subscribed to this channel.
     * @return Amount of subscribers.
     */
    public ArrayList<Subscriber> getSubscribers() {
        return this.subscribers;
    }

    /**
     * Add a new subscriber. (Adds the same subscribing object only once)
     * @param subscriber Subscribing object to notify.
     */
    public void addSubscriber(Subscriber subscriber) {
        if(!this.subscribers.contains(subscriber)) {
            this.subscribers.add(subscriber);
        }
    }

    /**
     * Removes the subscriber if is subscribing.
     * @param subscriber Subscribing object to remove from subscribers.
     */
    public void removeSubscriber(Subscriber subscriber) {
        if(this.subscribers.contains(subscriber)) {
            this.subscribers.remove(subscriber);
        }
    }

    /**
     * Publish data to all subscribing object calling the notify method.
     * @param eventData Published data.
     * @param sender Publishing object.
     * @param range How far the data was published.
     */
    public void notify(Data eventData, Object sender, Range range) {
        Event event = new Event(eventData, sender, range);
        for(Subscriber sub : this.subscribers) {
            sub.notify(this.name, event);
        }
    }

    public static ArrayList<String> parseChannelNames(String channels) {
        return null; //TODO: Make me
    }

    public static String stringifyChannelNames(ArrayList<String> channels) {
        return null; //TODO: Make me
    }
}
