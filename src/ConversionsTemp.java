//misc static sh- stuff
public class ConversionsTemp {
    public static String byteToHex(byte b) {
        return String.format("%02X", b);
    }

    public static byte hexToByte(String hexString) {
        return (byte) Integer.parseInt(hexString, 16);
    }
}


