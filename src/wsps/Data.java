package wsps;

public class Data {
    public enum Type {
        Null ('n'),
        Integer ('i'),
        Float ('f'),
        String ('s'),
        JSONString ('j');

        protected char value;

        /**
         * Defines the datas type.
         * @param type Data type value
         */
        Type(char type) {
            this.value = type;
        }
    }

    protected Type type;

    private JObject objHolder = null;
    private String strHolder = null;
    private long intHolder = 0;
    private double fltHolder = 0.0;

    /**
     * Initializes a new Data object by initial values parsed from the data string.
     * @param dataString
     * @return
     */
    public static Data fromDataString(String dataString) {
        return new Data(dataString, 0);
    }

    //sigdif is just a filler value for the compiler to make a difference between String value and data string.
    private Data(String unparsedDataString, int sigdif) {
        strHolder = unparsedDataString.substring(1);

        switch(unparsedDataString.charAt(0)) {
            case 's':
                type = Type.String;
                break;

            case 'j':
                type = Type.JSONString;
                break;

            case 'i':
                type = Type.Integer;
                intHolder = Long.parseLong(strHolder);
                break;

            case 'f':
                type = Type.Float;
                fltHolder = Double.parseDouble(strHolder);
                break;

            default:
                type = Type.Null;
                break;
        }
    }

    public Data() {
        type = Type.Null;
    }

    public Data(String val) {
        strHolder = val;
        type = Type.String;
    }

    public Data(JObject val) {
        objHolder = val;
        type = Type.JSONString;
    }

    public Data(float val) {
        fltHolder = val;
        type = Type.Float;
    }

    public Data(double val) {
        fltHolder = val;
        type = Type.Float;
    }

    public Data(byte val) {
        intHolder = val;
        type = Type.Integer;
    }

    public Data(short val) {
        intHolder = val;
        type = Type.Integer;
    }

    public Data(int val) {
        intHolder = val;
        type = Type.Integer;
    }

    public Data(long val) {
        intHolder = val;
        type = Type.Integer;
    }

    public Type getType() {
        return type;
    }

    public byte toByte() {
        return (byte)intHolder;
    }

    public short toInt16() {
        return (short)intHolder;
    }

    public int toInt32() {
        return (int)intHolder;
    }

    public long toInt64() {
        return intHolder;
    }

    /**
     * Returns the Data as string. (Includes JSON)
     * @return String or JSON String value.
     */
    public String toString() {
        if(objHolder != null && strHolder == null) {
            strHolder = objHolder.toJSONString();
        }

        return strHolder;
    }

    /**
     * Returns the current Object. (Only for using at >server side only< to save res. serializing and unserializing Object to JSON and back.)
     * @return JObject or null if data is sent by client or data is not an object.
     */
    public JObject getObject() {
        return objHolder;
    }

    public float toFloat() {
        return (float)fltHolder;
    }

    public double toDouble() {
        return fltHolder;
    }

    /**
     * Converts the data back to a network string like defined in the WSPS documentation v1.0.
     * @return Data string.
     */
    public String toNetworkString() {
        String str = String.valueOf(type.value);
        switch (type) {
            case String:
            case JSONString:
                str += toString();
                break;

            case Integer:
                str += String.valueOf(toInt64());
                break;

            case Float:
                str += String.valueOf(toDouble());
                break;
        }

        return str;
    }
}
