package com.leaf.clips.model.usersetting;
/**
* @author Federico Tavella
* @version 0.01
* @since 0.00
* 
* 
*/

import android.content.SharedPreferences;

/**
 * Classe che per la verifica dei codici sviluppatore
 */
public class DeveloperCodeManager {

    /**
     * Stringa rappresentante la chiave per recuperare le SharedPreferences riguardanti il codice sviluppatore
     */
    private static final String DEVELOPER_CODE = "developerKey"; // TODO: 01/05/2016

    /**
     * SharedPreferences relative all'applicazione
     */
    private SharedPreferences sharedPreferences;

    public DeveloperCodeManager(SharedPreferences sharedPreferences){
        this.sharedPreferences = sharedPreferences;
    }
    /**
    * Questo metodo permette di verificare se il codice inserito è valido per attivare la modalità sviluppatore
    * @param code Questo parametro richiede il codice per attivare la modalità sviluppatore
    * @return  boolean
    */
    public boolean isValid(String code){
        //dumb implementation
        return code == sharedPreferences.getString(DEVELOPER_CODE, "");// TODO: 01/05/2016  
    }

}

