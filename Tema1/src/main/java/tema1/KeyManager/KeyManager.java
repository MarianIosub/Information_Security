package tema1.KeyManager;

public class KeyManager {
    public static byte[] K;
    public static byte[] Kprim = "aabbccddeeffgghh".getBytes();


    public static void main(String[] args) throws Exception {
        KMClient KMNode = new KMClient("127.0.0.1", 12345);
        System.out.println("M-am conectat!");
        String message = new String(KMNode.readMessageFromA(12));
        System.out.println("Am primit mesaj de cerere de la A!");
        K = KMCrypto.createFirstEncryptionKey();
        K = KMCrypto.encryptMessage(K, Kprim);
        if (message.contains("Vreau cheie!")) {
            System.out.println("Am trimis cheia de criptare la nodul A!");
            KMNode.sendMessageToA(K);
        } else {
            System.out.println("Nu am primit cerere!");
        }
        KMNode.closeConnections();
    }
}
