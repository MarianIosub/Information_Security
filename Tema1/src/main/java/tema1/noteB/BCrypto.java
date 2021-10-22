package tema1.noteB;

import com.google.common.primitives.Bytes;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class BCrypto {
    public static byte[] encryptMessage(byte[] message, byte[] keyBytes) throws InvalidKeyException,
            NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
        SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher.doFinal(message);
    }

    public static byte[] decryptMessage(byte[] encryptedMessage, byte[] keyBytes) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
        SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return cipher.doFinal(encryptedMessage);
    }


    public static byte[] addPadding(byte[] message) {
        int count = 0;
        while ((message.length + count) % 16 != 0) {
            count = count + 1;
        }
        byte[] paddedMessage = new byte[message.length + count];
        System.arraycopy(message, 0, paddedMessage, 0, message.length);
        for (int i = message.length; i < message.length + count; i++) {
            paddedMessage[i] = (byte) 32;
        }
        return paddedMessage;
    }

    public static byte[] ECBDecrypt(byte[] text, byte[] key) throws NoSuchPaddingException, IllegalBlockSizeException,
            NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        List<Byte> myList = new ArrayList<>();
        byte[] cipherBlock = new byte[16];
        int contor = 0;
        int len = 16;
        int start = 0;
        byte[] bytes = text;
        bytes = addPadding(bytes);
        while (contor != bytes.length / 16) {
            int j = 0;
            for (int i = start; i < len; i++) {
                cipherBlock[j] = bytes[i];
                j += 1;
            }
            byte[] encryptedMessage = decryptMessage(cipherBlock, key);
            for (int i = 0; i < encryptedMessage.length; i++) {
                Byte bObj = encryptedMessage[i];
                myList.add(bObj);
            }
            start += 16;
            len += 16;
            contor += 1;
        }
        byte[] finalMessage = Bytes.toArray(myList);
        return finalMessage;
    }

    public static byte[] OFBDecrypt(byte[] text, byte[] key, byte[] iv) throws NoSuchPaddingException,
            IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        List<Byte> myList = new ArrayList<>();
        byte[] cipherBlock = new byte[16];
        int contor = 0;
        int len = 16;
        int start = 0;
        byte[] bytes = text;
        bytes = addPadding(bytes);
        while (contor != bytes.length / 16) {
            int j = 0;
            for (int i = start; i < len; i++) {
                cipherBlock[j] = bytes[i];
                j += 1;
            }
            byte[] encryptedMessage = encryptMessage(iv, key);
            for (int i = 0; i < encryptedMessage.length; i++) {
                Byte bObj = (byte) (encryptedMessage[i] ^ cipherBlock[i]);
                myList.add(bObj);
            }
            iv = encryptedMessage;
            start += 16;
            len += 16;
            contor += 1;
        }
        byte[] finalMessage = Bytes.toArray(myList);
        return finalMessage;
    }
}
