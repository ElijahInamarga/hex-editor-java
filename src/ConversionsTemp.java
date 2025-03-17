public class ConversionsTemp {
    public static String byteToHex(byte b) {
        return String.format("%02X", b);
    }

    public static int hexToByte(String hexString) {
        return (byte) Integer.parseInt(hexString, 16);
    }
}
