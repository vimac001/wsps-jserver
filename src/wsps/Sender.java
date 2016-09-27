package wsps;

public enum Sender {
    Server ("server"),
    Client ("client");

    protected String value;

    /**
     * Defines data is published by server or client.
     * @param sender Sender value (server, client)
     */
    Sender(String sender) {
        this.value = sender;
    }

}
