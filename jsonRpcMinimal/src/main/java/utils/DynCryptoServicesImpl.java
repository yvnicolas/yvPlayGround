package utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;




public class DynCryptoServicesImpl implements DynCryptoServices {

    // TODO : rejeter de nouvelles exceptions si probleme
    public DynCryptoServicesImpl() {
        try {
            clef = new SecretKeySpec("dyn".getBytes("ISO-8859-2"), "Blowfish");
        } catch (UnsupportedEncodingException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        // blowfishKey = KeyGenerator.getInstance("Blowfish").generateKey();
        String algorithm = clef.getAlgorithm();
        try {
            ecipher = Cipher.getInstance(algorithm);
            dcipher = Cipher.getInstance(algorithm);
            ecipher.init(Cipher.ENCRYPT_MODE, clef);
            dcipher.init(Cipher.DECRYPT_MODE, clef);
        } catch (NoSuchPaddingException e) {

        } catch (NoSuchAlgorithmException e) {

        } catch (InvalidKeyException e) {

        }

    }

    Cipher ecipher;
    Cipher dcipher;
    Key clef = null;

    @Override
    public String urlSafeEncrypt(String str) throws IOException {
        String beforeEncode = basicEncrypt(str);
        try {
            return URLEncoder.encode(beforeEncode, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new IOException("Dynamease Encryption error", e);
        }
    }

    @Override
    public String basicEncrypt(String toEncode) throws IOException {

        try {
            // Encode the string into bytes using utf-8
            byte[] utf8 = toEncode.getBytes("UTF8");

            // Encrypt
            byte[] enc = null;
            try {
                enc = ecipher.doFinal(utf8);
            } catch (IllegalBlockSizeException e) {
                throw new IOException("Dynamease Encryption error", e);
            }

            // Encode bytes to base64 to get a string and returns it
            return new sun.misc.BASE64Encoder().encode(enc);
        } catch (javax.crypto.BadPaddingException e) {
            throw new IOException("Dynamease Encryption error", e);
        } catch (java.io.IOException e) {
            throw new IOException("Dynamease Encryption error", e);
        }
       
    }

    @Override
    public String decrypt(String strEnc) throws IOException {
        try {
            // Decode base64 to get bytes
            //String str = URLDecoder.decode(strEnc,"UTF-8");
            byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(strEnc);

            // Decrypt
            byte[] utf8 = null;
            try {
                utf8 = dcipher.doFinal(dec);
            } catch (IllegalBlockSizeException e) {
                throw new IOException("Dynamease Encryption error", e);
            }

            // Decode using utf-8
            return new String(utf8, "UTF8");
        } catch (javax.crypto.BadPaddingException e) {
            throw new IOException("Dynamease Encryption error", e);
        } catch (java.io.IOException e) {
            throw new IOException("Dynamease Encryption error", e);
        }
    
    }

}
