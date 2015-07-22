package by.bsu.guglya.library.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Encryptor implements Encryptor{
    @Override
    public String Encrypt(String stringToEncrypt) throws NoSuchAlgorithmException {
        String encryptedString = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(stringToEncrypt.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            encryptedString = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return encryptedString;
    }

}
