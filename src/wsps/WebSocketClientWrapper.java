package wsps;

public interface WebSocketClientWrapper {
    /**
     * Send message through ws-client handle to client.
     * @param msg Message to be sent to client.
     */
    public void send(String msg);

    /**
     * Returns the ws-client handle object.
     * @return Handle object.
     */
    public Object getHandle();
}
