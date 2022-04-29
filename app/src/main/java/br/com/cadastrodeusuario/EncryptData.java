package br.com.cadastrodeusuario;

import java.security.MessageDigest;

public class EncryptData {
    public static byte[] codificaSHA(byte[] data) throws Exception{
        MessageDigest sha = MessageDigest.getInstance("SHA-1");
        sha.update(data);
        return sha.digest();
    }
}
