package by.bsu.guglya.library.utils;

import java.security.NoSuchAlgorithmException;

public interface Encryptor {
    public String Encrypt(String stringToEncrypt) throws NoSuchAlgorithmException;
}
