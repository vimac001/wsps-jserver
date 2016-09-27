package wsps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Manager {
    protected static Map<String, Channel> channels = new HashMap<>();
    protected static ArrayList<Client> clients = new ArrayList<>();
    protected static ArrayList<String> channelList = new ArrayList<>();

    /**
     * Subscribe a channel.
     * @param channel Channel name to subscribe.
     * @param subscriber Subscribing object to notify at.
     */
    public static void subscribe(String channel, Subscriber subscriber) {
        if(!channels.containsKey(channel)) {
            channels.put(channel, new Channel(channel));
        }

        if(channels.get(channel).getSubscribers().size() <= 0) {
            channelList.add(channel);
            for (Client c : clients) {
                if(c != (Client)subscriber) {
                    c.subscribe(channel);
                }
            }
        }

        channels.get(channel).addSubscriber(subscriber);
    }

    /**
     * Subscribe channels.
     * @param channelsList Channel names to subscribe.
     * @param subscriber Subscribing object to notify at.
     */
    public static void subscribe(ArrayList<String> channelsList, Subscriber subscriber) {
        ArrayList<String> newChannels = new ArrayList<>();
        for(String channel : channelsList) {
            if (!channels.containsKey(channel)) {
                channels.put(channel, new Channel(channel));

            }

            if(channels.get(channel).getSubscribers().size() <= 0) {
                channelList.add(channel);
                newChannels.add(channel);
            }

            channels.get(channel).addSubscriber(subscriber);
        }

        if(newChannels.size() > 0) {
            for (Client c : clients) {
                if (c != (Client) subscriber) {
                    c.subscribe(newChannels);
                }
            }
        }
    }

    /**
     * Unsubscribe a channel.
     * @param channel Channel name to unsubscribe.
     * @param subscriber Subscribed object to identify and unsubscribe.
     */
    public static void unsubscribe(String channel, Subscriber subscriber) {
        if(channels.containsKey(channel)) {
            channels.get(channel).removeSubscriber(subscriber);
            if(channels.get(channel).getSubscribers().size() <= 0) {
                channelList.remove(channelList.indexOf(channel));

                for (Client c : clients) {
                    c.unsubscribe(channel);
                }
            }
        }
    }

    /**
     * Unsubscribe channels.
     * @param channelsList Channel names to unsubscribe.
     * @param subscriber Subscribed object to identify and unsubscribe.
     */
    public static void unsubscribe(ArrayList<String> channelsList, Subscriber subscriber) {
        ArrayList<String> emptyChannels = new ArrayList<>();
        for(String channel : channelsList) {
            if (channels.containsKey(channel)) {
                channels.get(channel).removeSubscriber(subscriber);
                if(channels.get(channel).getSubscribers().size() <= 0) {
                    channelList.remove(channelList.indexOf(channel));
                    emptyChannels.add(channel);
                }
            }
        }

        for (Client c : clients) {
            c.unsubscribe(emptyChannels);
        }
    }

    /**
     * Publish data to a channel.
     * @param channel Channel name to publish at.
     * @param eventData Data to publish to subscribers.
     * @param sender Publishing object.
     * @param range How far to publish the data.
     */
    public static void publish(String channel, Data eventData, Object sender, Range range) {
        if(channels.containsKey(channel) && channels.get(channel).getSubscribers().size() > 0) {
            channels.get(channel).notify(eventData, sender, range);
        }
    }

    /**
     * Publish data to a channel.
     * @param channel Channel name to publish at.
     * @param val Data to publish to subscribers.
     * @param sender Publishing object.
     * @param range How far to publish the data.
     */
    public static void publish(String channel, long val, Object sender, Range range) {
        Manager.publish(channel, new Data(val), sender, range);
    }

    /**
     * Publish data to a channel.
     * @param channel Channel name to publish at.
     * @param val Data to publish to subscribers.
     * @param sender Publishing object.
     * @param range How far to publish the data.
     */
    public static void publish(String channel, int val, Object sender, Range range) {
        Manager.publish(channel, new Data(val), sender, range);
    }

    /**
     * Publish data to a channel.
     * @param channel Channel name to publish at.
     * @param val Data to publish to subscribers.
     * @param sender Publishing object.
     * @param range How far to publish the data.
     */
    public static void publish(String channel, short val, Object sender, Range range) {
        Manager.publish(channel, new Data(val), sender, range);
    }

    /**
     * Publish data to a channel.
     * @param channel Channel name to publish at.
     * @param val Data to publish to subscribers.
     * @param sender Publishing object.
     * @param range How far to publish the data.
     */
    public static void publish(String channel, byte val, Object sender, Range range) {
        Manager.publish(channel, new Data(val), sender, range);
    }

    /**
     * Publish data to a channel.
     * @param channel Channel name to publish at.
     * @param val Data to publish to subscribers.
     * @param sender Publishing object.
     * @param range How far to publish the data.
     */
    public static void publish(String channel, String val, Object sender, Range range) {
        Manager.publish(channel, new Data(val), sender, range);
    }

    /**
     * Publish data to a channel.
     * @param channel Channel name to publish at.
     * @param val Data to publish to subscribers.
     * @param sender Publishing object.
     * @param range How far to publish the data.
     */
    public static void publish(String channel, JObject val, Object sender, Range range) {
        Manager.publish(channel, new Data(val), sender, range);
    }

    /**
     * Publish data to a channel.
     * @param channel Channel name to publish at.
     * @param val Data to publish to subscribers.
     * @param sender Publishing object.
     * @param range How far to publish the data.
     */
    public static void publish(String channel, float val, Object sender, Range range) {
        Manager.publish(channel, new Data(val), sender, range);
    }

    /**
     * Publish data to a channel.
     * @param channel Channel name to publish at.
     * @param val Data to publish to subscribers.
     * @param sender Publishing object.
     * @param range How far to publish the data.
     */
    public static void publish(String channel, double val, Object sender, Range range) {
        Manager.publish(channel, new Data(val), sender, range);
    }

    /**
     * Publish data to channels.
     * @param channelsList Channel names to publish at.
     * @param val Data to publish to subscribers.
     * @param sender Publishing object.
     * @param range How far to publish the data.
     */
    public static void publish(ArrayList<String> channelsList, long val, Object sender, Range range) {
        Manager.publish(channelsList, new Data(val), sender, range);
    }

    /**
     * Publish data to channels.
     * @param channelsList Channel names to publish at.
     * @param val Data to publish to subscribers.
     * @param sender Publishing object.
     * @param range How far to publish the data.
     */
    public static void publish(ArrayList<String> channelsList, int val, Object sender, Range range) {
        Manager.publish(channelsList, new Data(val), sender, range);
    }

    /**
     * Publish data to channels.
     * @param channelsList Channel names to publish at.
     * @param val Data to publish to subscribers.
     * @param sender Publishing object.
     * @param range How far to publish the data.
     */
    public static void publish(ArrayList<String> channelsList, short val, Object sender, Range range) {
        Manager.publish(channelsList, new Data(val), sender, range);
    }

    /**
     * Publish data to channels.
     * @param channelsList Channel names to publish at.
     * @param val Data to publish to subscribers.
     * @param sender Publishing object.
     * @param range How far to publish the data.
     */
    public static void publish(ArrayList<String> channelsList, byte val, Object sender, Range range) {
        Manager.publish(channelsList, new Data(val), sender, range);
    }

    /**
     * Publish data to channels.
     * @param channelsList Channel names to publish at.
     * @param val Data to publish to subscribers.
     * @param sender Publishing object.
     * @param range How far to publish the data.
     */
    public static void publish(ArrayList<String> channelsList, String val, Object sender, Range range) {
        Manager.publish(channelsList, new Data(val), sender, range);
    }

    /**
     * Publish data to channels.
     * @param channelsList Channel names to publish at.
     * @param val Data to publish to subscribers.
     * @param sender Publishing object.
     * @param range How far to publish the data.
     */
    public static void publish(ArrayList<String> channelsList, JObject val, Object sender, Range range) {
        Manager.publish(channelsList, new Data(val), sender, range);
    }

    /**
     * Publish data to channels.
     * @param channelsList Channel names to publish at.
     * @param val Data to publish to subscribers.
     * @param sender Publishing object.
     * @param range How far to publish the data.
     */
    public static void publish(ArrayList<String> channelsList, float val, Object sender, Range range) {
        Manager.publish(channelsList, new Data(val), sender, range);
    }

    /**
     * Publish data to channels.
     * @param channelsList Channel names to publish at.
     * @param val Data to publish to subscribers.
     * @param sender Publishing object.
     * @param range How far to publish the data.
     */
    public static void publish(ArrayList<String> channelsList, double val, Object sender, Range range) {
        Manager.publish(channelsList, new Data(val), sender, range);
    }

    /**
     * Publish data to a channel.
     * @param channel Channel name to publish at.
     * @param eventData Data to publish to subscribers.
     * @param sender Publishing object.
     */
    public static void publish(String channel, Data eventData, Object sender) {
        if(channels.containsKey(channel) && channels.get(channel).getSubscribers().size() > 0) {
            channels.get(channel).notify(eventData, sender, Range.ServerOnly);
        }
    }

    /**
     * Publish data to a channel.
     * @param channel Channel name to publish at.
     * @param eventData Data to publish to subscribers.
     */
    public static void publish(String channel, Data eventData) {
        if(channels.containsKey(channel) && channels.get(channel).getSubscribers().size() > 0) {
            channels.get(channel).notify(eventData, null, Range.ServerOnly);
        }
    }

    /**
     * Publish data to channels.
     * @param channelsList Channel names to publish at.
     * @param eventData Data to publish to subscribers.
     * @param sender Publishing object.
     * @param range How far to publish the data.
     */
    public static void publish(ArrayList<String> channelsList, Data eventData, Object sender, Range range) {
        for(String channel : channelsList) {
            if (channels.containsKey(channel) && channels.get(channel).getSubscribers().size() > 0) {
                channels.get(channel).notify(eventData, sender, range);
            }
        }
    }

    /**
     * Publish data to channels.
     * @param channelsList Channel names to publish at.
     * @param eventData Data to publish to subscribers.
     * @param sender Publishing object.
     */
    public static void publish(ArrayList<String> channelsList, Data eventData, Object sender) {
        for(String channel : channelsList) {
            if (channels.containsKey(channel) && channels.get(channel).getSubscribers().size() > 0) {
                channels.get(channel).notify(eventData, sender, Range.ServerOnly);
            }
        }
    }

    /**
     * Publish data to channels.
     * @param channelsList Channel names to publish at.
     * @param eventData Data to publish to subscribers.
     */
    public static void publish(ArrayList<String> channelsList, Data eventData) {
        for(String channel : channelsList) {
            if (channels.containsKey(channel) && channels.get(channel).getSubscribers().size() > 0) {
                channels.get(channel).notify(eventData, null, Range.ServerOnly);
            }
        }
    }

    public static void onNewClient(Client client) {
        clients.add(client);
        client.subscribe(channelList);
    }

    public static void onClientClose(Client client) {
        clients.remove(client);
    }
}
