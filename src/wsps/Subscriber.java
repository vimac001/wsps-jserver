package wsps;

public interface Subscriber {
    /**
     * Notify published data to object.
     * @param channel Channel name data was published at.
     * @param event Event object containing the data.
     */
    public void notify(String channel, Event event);
}
