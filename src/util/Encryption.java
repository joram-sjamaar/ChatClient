package util;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Encryption {

    public static String encrypt(String string, String key) {

        try {

            Key aesKey = new SecretKeySpec(Base64.getDecoder().decode(key), "AES");

            Cipher cipher = Cipher.getInstance("AES");

            cipher.init(Cipher.ENCRYPT_MODE, aesKey);

            byte[] encrypted = cipher.doFinal(string.getBytes());

            return Base64.getEncoder().encodeToString(encrypted);

        }

        catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {

            e.printStackTrace();

        }

        return null;

    }

    public static String decrypt(String string, String key) {

        try {

            Key aesKey = new SecretKeySpec(Base64.getDecoder().decode(key), "AES");

            Cipher cipher = Cipher.getInstance("AES");

            cipher.init(Cipher.DECRYPT_MODE, aesKey);

            byte[] bytes = Base64.getDecoder().decode(string);

            return new String(cipher.doFinal(bytes));

        }

        catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {

            e.printStackTrace();

        }

        return null;

    }

}
