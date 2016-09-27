package wsps;

public enum Range {
    ClientOnly ((byte)0),
    ServerOnly ((byte)1),
    All ((byte)2);

    protected byte value;

    /**
     * Defines data is published to server or server and clients.
     * @param range Range value (ClientOnly 0, ServerOnly 1, All 2)
     */
    Range(byte range) {
        this.value = range;
    }

    public static Range fromByte(byte value) {
        for (Range rng : Range.values()) {
            if (rng.value == value) {
                return rng;
            }
        }

        return null;
    }
}