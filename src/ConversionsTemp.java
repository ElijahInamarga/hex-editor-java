public class ConversionsTemp {
    public static String byteToHex(byte b) {
        return String.format("%02x", b);
    }

    public static int hexToByte(String hexString) {
        return (byte) Integer.parseInt(hexString, 16);
    }
}
