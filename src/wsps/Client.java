package wsps;

import java.util.ArrayList;

/**
 * Handles the data transfer between client and server.
 */
public class Client implements Subscriber {
    protected WebSocketClientWrapper wscw;
    protected ArrayList<String> channels;
    protected boolean closed = false;

    /**
     * Initializes a new client representative.
     * @param wscw WebSocket server client object.
     */
    public Client(WebSocketClientWrapper wscw) {
        this.wscw = wscw;
        this.channels = new ArrayList<>();
        Manager.onNewClient(this);
    }

    /**
     * Returns the client identifying object.
     * @return WebSocket server client object.
     */
    public WebSocketClientWrapper getWebSocketClientWrapper() {
        return this.wscw;
    }

    /**
     * Subscribe server to client at channel.
     * @param channel Channel name.
     */
    public void subscribe(String channel) {
        String msg = "s" + channel;
        this.wscw.send(msg);
    }

    /**
     * Subscribe server to client at channels.
     * @param channelList Channel names.
     */
    public void subscribe(ArrayList<String> channelList) {
        String msg = "s" + Channel.stringifyChannelNames(channelList);
        this.wscw.send(msg);
    }

    /**
     * Unsubscribe server from client at channel.
     * @param channel Channel name.
     */
    public void unsubscribe(String channel) {
        String msg = "u" + channel;
        this.wscw.send(msg);
    }

    /**
     * Unsubscribe server from client at channels.
     * @param channelList Channel names.
     */
    public void unsubscribe(ArrayList<String> channelList) {
        String msg = "u" + Channel.stringifyChannelNames(channelList);
        this.wscw.send(msg);
    }

    /**
     * Publish data at channel to all subscribers. (Also to other clients)
     * @param channel Channel name.
     * @param eventData Published data.
     */
    public void onPublish(String channel, Data eventData) {
        Manager.publish(channel, eventData, this, Range.ServerOnly);
    }

    /**
     * Publish data at channel to all subscribers. (Also to other clients)
     * @param channel Channel name.
     * @param eventData Published data.
     * @param range The range, how far data was published.
     */
    public void onPublish(String channel, Data eventData, Range range) {
        Manager.publish(channel, eventData, this, range);
    }

    /**
     * Publish data at channels to all subscribers.
     * @param channelList Channel names.
     * @param eventData Published data.
     */
    public void onPublish(ArrayList<String> channelList, Data eventData) {
        for(String channel : channelList) {
            Manager.publish(channel, eventData, this, Range.ServerOnly);
        }
    }

    /**
     * Publish data at channels to all subscribers.
     * @param channelList Channel names.
     * @param eventData Published data.
     * @param range The range, how far data was published.
     */
    public void onPublish(ArrayList<String> channelList, Data eventData, Range range) {
        for(String channel : channelList) {
            Manager.publish(channel, eventData, this, range);
        }
    }

    /**
     * Send published data to WebSocket client. (Only if server-wide only flag is FALSE)
     * @param channel Channel name data was published at.
     * @param event Event object containing the data.
     */
    @Override
    public void notify(String channel, Event event) {
        if(event.getRange().value > Range.ServerOnly.value && event.sender != this) {
            String msg = "p" + String.valueOf(event.getRange().value) + channel.length() + ':' + channel + event.getData().toNetworkString();
            this.wscw.send(msg);
        }
    }

    /**
     * Subscribe the client to channel.
     * @param channel Channel name.
     */
    public void onSubscribe(String channel) {
        Manager.subscribe(channel, this);
        if(!this.channels.contains(channel)) {
            this.channels.add(channel);
        }

    }

    /**
     * Subscribe the client to channels.
     * @param channelList Channel names.
     */
    public void onSubscribe(ArrayList<String> channelList) {
        for(String channel : channelList) {
            Manager.subscribe(channel, this);
            if(!this.channels.contains(channel)) {
                this.channels.add(channel);
            }
        }
    }

    /**
     * Unsubscribe the client from channel.
     * @param channel Channel name.
     */
    public void onUnsubscribe(String channel) {
        Manager.unsubscribe(channel, this);
        if(this.channels.contains(channel)) {
            this.channels.remove(channel);
        }
    }

    /**
     * Unsubscribe the client from channels.
     * @param channelList Channel names.
     */
    public void onUnsubscribe(ArrayList<String> channelList) {
        for(String channel : channelList) {
            Manager.unsubscribe(channel, this);
            if (this.channels.contains(channel)) {
                this.channels.remove(channel);
            }
        }
    }

    /**
     * Call when WebSocket client receives new data.
     * @param msg Received data.
     */
    public void onNewMessage(String msg) {
        ArrayList<String> channels;
        if(msg.startsWith("p")) {
            byte range = Byte.parseByte(msg.substring(1, 2));
            int i = msg.indexOf(':', 3);
            int cnmLng = Integer.parseInt(msg.substring(2, i++));
            channels = Channel.parseChannelNames(msg.substring(i, i + cnmLng));
            i += cnmLng;
            String eventData = msg.substring(i);

            this.onPublish(channels, Data.fromDataString(eventData), Range.fromByte(range));
        } else if(msg.startsWith("s")) {
            channels = Channel.parseChannelNames(msg.substring(1));
            this.onSubscribe(channels);
        } else if(msg.startsWith("u")) {
            channels = Channel.parseChannelNames(msg.substring(1));
            this.onUnsubscribe(channels);
        }

        //TODO: Something impossible happens, or maybe someone trys to hack the protocol?
    }

    public boolean isClosed() {
        return closed;
    }

    /**
     * Call when WebSocket client closed the connection.
     * Unsubscribe from all channels.
     */
    public void onConnectionClosed() {
        closed = true;
        for(String channel : this.channels) {
            Manager.unsubscribe(channel, this);
        }
        Manager.onClientClose(this);
    }
}
