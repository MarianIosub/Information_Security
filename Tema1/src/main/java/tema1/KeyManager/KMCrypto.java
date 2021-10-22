package tema1.KeyManager;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class KMCrypto {
    public static byte[] createFirstEncryptionKey() {
        byte[] array = new byte[16];
        new Random().nextBytes(array);
        return array;
    }

    public static byte[] encryptMessage(byte[] message, byte[] keyBytes) throws InvalidKeyException,
            NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
        SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher.doFinal(message);
    }
}
