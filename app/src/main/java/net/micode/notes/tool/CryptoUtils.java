package net.micode.notes.tool;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.Arrays;
import android.util.Base64;

public class CryptoUtils {
    private static final String ALGORITHM = "AES";
    private static String KEY_STRING = "YourSecretKeyString"; // 替换为您的密钥字符串

    public static String getKeyString() {
        return KEY_STRING;
    }
    public static void setKeyString(String newKeyString) {
        KEY_STRING = newKeyString;
    }

    // 从固定字符串生成 AES 密钥
    public static SecretKey generateFixedKey() throws Exception {
        byte[] keyBytes = KEY_STRING.getBytes("UTF-8");
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        keyBytes = sha.digest(keyBytes);
        keyBytes = Arrays.copyOf(keyBytes, 16); // 使用前 16 个字节作为 AES 密钥（128 位）
        return new SecretKeySpec(keyBytes, ALGORITHM);
    }

    // 加密文本
    public static String encrypt(String value, SecretKey key) throws Exception {
        byte[] valueBytes = value.getBytes("UTF-8");
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(valueBytes);
        return Base64.encodeToString(encryptedBytes, Base64.DEFAULT);
    }

    // 解密文本
    public static String decrypt(String value, SecretKey key) throws Exception {
        byte[] encryptedValueBytes = Base64.decode(value, Base64.DEFAULT);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedValueBytes = cipher.doFinal(encryptedValueBytes);
        return new String(decryptedValueBytes, "UTF-8");
    }
}
