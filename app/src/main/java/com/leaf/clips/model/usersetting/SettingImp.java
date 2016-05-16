package com.leaf.clips.model.usersetting;
/**
* @author Federico Tavella
* @version 0.02
* @since 0.00
* 
* 
*/

import android.content.Context;
import android.content.SharedPreferences;

/** 
 * Classe che implementa l'interfaccia Setting. Implementa tutti i metodi per la gestione delle impostazioni dell'utente
 */
public class SettingImp implements Setting { 

    /**
    * Preferenze dell'utente riguardanti le modalità di fruizione delle informazioni per la navigazione
    */
    private InstructionPreference instructionPreference;

    /**
    * Preferenze di percorso dell'utente
    */
    private PathPreference pathPreference;

    /**
     * SharedPreferences relative all'applicazione
     */

    SharedPreferences sharedPreferences;

    /**
     * Editor per modificare le SharedPreferences dell'applicazione
     */

    SharedPreferences.Editor preferencesEditor;

    /**
     * Chiave per accedere alle preferenze dell'utente contenute nelle SharedPreferences
     */

    private static final String USER_PREFERENCES = "userKey"; // TODO: 02/05/2016 decidere la stringa

    /**
     * Chiave per accedere alle preferenze di percorso dell'utente contenute nelle SharedPreferences
     */

    private static final String PATH_PREFERENCES = "pathKey"; // TODO: 01/05/2016 decidere la stringa

    /**
     * Chiave per accedere alle preferenze di fruizione dell'informazione dell'utente contenute
     * nelle SharedPreferences
     */

    private static final String INSTRUCTION_PREFERENCES = "instructionKey"; // TODO: 01/05/2016 decidere la stringa

    /**
     * Chiave per accedere al codice sviluppatore contenuto nelle SharedPreferences
     */

    private static final String DEVELOPER_CODE = "developerKey"; // TODO: 01/05/2016 decidere la stringa


    /**
     * Costruttore della classe SettingImp
     * @param context Context dell'applicazione
     */

    public SettingImp(Context context){
        sharedPreferences = context.getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE);
        pathPreference = PathPreference.fromInt(sharedPreferences.getInt(PATH_PREFERENCES, 0));
        instructionPreference = InstructionPreference.fromInt(sharedPreferences.getInt(INSTRUCTION_PREFERENCES, 0));
        preferencesEditor = sharedPreferences.edit();
    }

    /**
    * Metodo che ritorna le preferenze riguardanti la modalità di fruizione delle informazioni
    * @return  InstructionPreference
    */
    @Override
    public InstructionPreference getInstructionPreference(){
        return instructionPreference;
    }

    /**
    * Metodo che ritorna le preferenze di percorso
    * @return  PathPreference
    */
    @Override
    public PathPreference getPathPreference(){
        return pathPreference;
    }

    /**
    * Metodo che verifica se è stato inserito in precedenza un codice sviluppatore valido
    * @return  boolean
    */
    @Override
    public boolean isDeveloper(){
        String hashedcode = "6a2387e9baac5778ab72dda120a3f2d7fe6c7611aaebc0571d1cb6e4b41f8a51";
        if (sharedPreferences.getString(DEVELOPER_CODE,"Nessuna chiave settata")
                .equals(hashedcode))
            return true;
        else
            return false;

    }

    /**
    * Metodo che permette di modificare le impostazioni riguardanti le preferenze di fruizione delle istruzioni di navigazione
    * @param instructionPreference Questo parametro richiede il tipo di istruzioni che si vuole ricevere durante la navigazione
    */
    @Override
    public void setInstructionPreference(InstructionPreference instructionPreference){
        this.instructionPreference = instructionPreference;
        preferencesEditor = sharedPreferences.edit();
        preferencesEditor.putInt(INSTRUCTION_PREFERENCES, InstructionPreference.toInt(instructionPreference));
        preferencesEditor.apply();
    }

    /**
    * Metodo che permette di modificare le impostazioni riguardanti le preferenze sul percorso di navigazione
    * @param pathPreference Questo parametro richiede le preferenze di percorso che un utente vuole impostare
    */
    @Override
    public void setPathPreference(PathPreference pathPreference){
        this.pathPreference = pathPreference;
        preferencesEditor = sharedPreferences.edit();
        preferencesEditor.putInt(PATH_PREFERENCES, PathPreference.toInt(pathPreference));
        preferencesEditor.apply();
    }

    /**
    * Metodo che passato una stringa in ingresso controlla se tale stringa rappresenta un codice sviluppatore valido
    * @param code Stringa rappresentante il codice sviluppatore
    * @return  boolean
    */
    @Override
    public boolean unlockDeveloper(String code) {
        if( new DeveloperCodeManager(sharedPreferences).isValid(code)){
            return true;
        }else
            return false;
    }

}

