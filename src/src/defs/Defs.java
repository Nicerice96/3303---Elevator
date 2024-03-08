package src.defs;

import java.time.format.DateTimeFormatter;

public class Defs {
    public static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
    public static final int MSG_SIZE = 1024;
    public static final int SCHEDULER_PORT = 5000;

    /**
     * Trims and returns the message from a byte stream taken from a packet
     * @param buff the byte array buffer
     * @param length the packet length
     * @return the trimmed message
     */
    public static String getMessage(byte[] buff, int length) {
        byte[] data = new byte[length];
        System.arraycopy(buff, 0, data, 0, length);
        return new String(data);
    }
}
