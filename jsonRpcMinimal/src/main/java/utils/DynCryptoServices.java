package utils;

import java.io.IOException;

public interface DynCryptoServices {

    /**
     * Produces an "Url safe" coding of toEncode, meaning that all non URL compatible characters are
     * transformed to the appropriate browser compatible equivalents
     * 
     * @param toEncode
     * @return
     * @throws DynServicesException 
     */
    public String urlSafeEncrypt(String toEncode) throws IOException;

    /**
     * Decripts the code that has been produced with the encrypt functions.
     * This suppose the "Url safe" characters transformation has been done before as it is the case with Spring Url Manipulation.
     * @param toDecode
     * @return
     * @throws DynServicesException 
     */
    public String decrypt(String toDecode) throws IOException;
    
    /**
     * Basic Dynamease reversible encryption of a String.
     * not Url safe.
     * @param toEncode
     * @return
     * @throws DynServicesException 
     */
    public String basicEncrypt(String toEncode) throws IOException;
}
