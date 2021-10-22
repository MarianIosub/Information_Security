package tema1.nodeA;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class NodeA {
    public static byte[] K;
    public static byte[] Kprim = "aabbccddeeffgghh".getBytes();
    public static byte[] IV = "1122334455667788".getBytes();

    public static String readPlainText() throws IOException {
        File file = new File("D:\\Facultate\\AN3_SEM1\\SI\\Tried\\Tema1\\src\\main\\java\\tema1\\nodeA\\plain.txt");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        return bufferedReader.readLine();
    }

    public static void main(String[] args) throws Exception{
        String plainText = readPlainText();
        AServer aNode = new AServer(12345);
        aNode.sendToKM("Vreau cheie!".getBytes());
        System.out.println("Am cerut cheie de la Key Manager");
        K = aNode.getMessageFromKM(16);
        System.out.println("Am primit cheia K: " + new String(K));
        String operationMode = aNode.readFromClient();
        aNode.sendToB(operationMode.getBytes());
        System.out.println("Am trimis la B modul de criptare:  " + operationMode);
        aNode.sendToB(K);
        System.out.println("Am trimis la B cheia K");
        K = ACrypto.decryptMessage(K, Kprim);
        System.out.println("Am decriptat cheia K");
        String message = new String(aNode.getMessageFromB()).trim();
        System.out.println("Am primit mesajul de incepere a comunicarii cu B!");
        if (message.contains("Let's start")) {
            System.out.println("Am criptat plain textul din fisier!");
            aNode.sendToB(ACrypto.chooseEncryption(plainText.getBytes(), K, IV, operationMode));
            System.out.println("Am trimis textul criptat la B!");
        }
        aNode.closeConnections();
    }
}
