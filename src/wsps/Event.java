package wsps;

public class Event {
    protected Data data;
    protected Object sender;
    protected Sender sentBy;
    protected Range range;

    /**
     * Initialize new event object.
     * @param data Published data to contain.
     * @param sender Publishing object.
     * @param range The data reach range.
     */
    public Event(Data data, Object sender, Range range) {
        this.data = data;
        this.sender = sender;
        this.sentBy = (sender instanceof Client ? Sender.Client : Sender.Server);
        this.range = range;
    }

    /**
     * Return containing published data.
     * @return Published data.
     */
    public Data getData() {
        return this.data;
    }

    /**
     * Return publishing object.
     * @return Publishing object.
     */
    public Object getSender() {
        return this.sender;
    }

    /**
     * Return published data was sent by client or server.
     * @return Sender flag.
     */
    public Sender sendBy() {
        return this.sentBy;
    }

    /**
     * Return the range.
     * @return Range
     */
    public Range getRange() {
        return range;
    }
}

