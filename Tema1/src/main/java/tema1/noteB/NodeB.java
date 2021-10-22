package tema1.noteB;


import java.util.Arrays;


public class NodeB {
    public static byte[] K;
    public static byte[] Kprim = "aabbccddeeffgghh".getBytes();
    public static byte[] IV = "1122334455667788".getBytes();

    static byte[] trim(byte[] bytes) {
        int i = bytes.length - 1;
        while (i >= 0 && bytes[i] == 0) {
            --i;
        }

        return Arrays.copyOf(bytes, i + 1);
    }

    public static void main(String[] args) throws Exception {
        BClient bNode = new BClient("127.0.0.1", 12345);
        System.out.println("M-am conectat!");
        byte[] operationMode = bNode.readMessageFromA(3);
        System.out.println("Am primit modul de operare de la A: " + new String(operationMode));
        K = bNode.readMessageFromA(16);
        System.out.println("Am primit cheia K de la A!");
        K = BCrypto.decryptMessage(K, Kprim);
        System.out.println("Am decriptat cheia K!");
        bNode.sendMessageToA("Let's start".getBytes());
        System.out.println("Am trimis mesajul de incepere a comunicarii cu A!");
        byte[] encrypted = bNode.readMessageFromA(200);
        encrypted = trim(encrypted);
        System.out.println("Am primit mesajul criptat!");
        switch (new String(operationMode).trim()) {
            case "ECB": {
                System.out.println("Am decriptat mesajul dupa modul ECB:");
                System.out.println(new String(BCrypto.ECBDecrypt(encrypted, K)).trim());
                break;
            }
            case "OFB": {
                System.out.println("Am decriptat mesajul dupa modul OFB:");
                System.out.println(new String(BCrypto.OFBDecrypt(encrypted, K, IV)).trim());
                break;
            }
        }
        bNode.closeConnections();
    }
}
