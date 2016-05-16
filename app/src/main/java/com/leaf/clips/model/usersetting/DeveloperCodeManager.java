package com.leaf.clips.model.usersetting;
/**
* @author Federico Tavella
* @version 0.01
* @since 0.00
* 
* 
*/

import android.content.SharedPreferences;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Classe che per la verifica dei codici sviluppatore
 */
public class DeveloperCodeManager {

    /**
     * Stringa rappresentante la chiave per recuperare le SharedPreferences riguardanti
     * il codice sviluppatore
     */
    private static final String DEVELOPER_CODE = "developerKey"; // TODO: 01/05/2016 decidere la stringa

    /**
     * SharedPreferences relative all'applicazione
     */
    private SharedPreferences sharedPreferences;

    /**
     * Costruttore della classe DeveloperCodeManager
     * @param sharedPreferences SharedPreference dell'applicazione a cui accedere per verificare
     *                          se un utente è sviluppatore
     */
    public DeveloperCodeManager(SharedPreferences sharedPreferences){
        this.sharedPreferences = sharedPreferences;
    }
    /**
     * Questo metodo permette di verificare se il codice inserito è valido per attivare
     * la modalità sviluppatore
     * @param code Questo parametro richiede il codice per attivare la modalità sviluppatore
     * @return  boolean
     */
    public boolean isValid(String code)  {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash =  md.digest(code.getBytes(StandardCharsets.UTF_8));
            StringBuilder result = new StringBuilder();
            for (byte byt : hash)
                result.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(1));
            String hashedcode = result.toString();
            if (hashedcode.equals("6a2387e9baac5778ab72dda120a3f2d7fe6c7611aaebc0571d1cb6e4b41f8a51")){
                sharedPreferences.edit().putString(DEVELOPER_CODE, hashedcode).apply();
                return true;
            }else{
                return false;
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        }
    }

}

