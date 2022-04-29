package br.com.cadastrodeusuario;

import java.security.MessageDigest;

public class EncriptaMD5 {
    //método para encriptar a senha
    public static byte[] encriptaSenha(byte[] dados) throws Exception{
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(dados);
        return md5.digest();
    }
}
