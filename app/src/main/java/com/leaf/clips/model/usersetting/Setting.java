package com.leaf.clips.model.usersetting;
/**
* @author Federico Tavella
* @version 0.01
* @since 0.00
* 
* 
*/

/** 
 * Interfaccia che espone i metodi per accedere alle preferenze di un utente riguardo il percorso di navigazione e le istruzioni di navigazione. Espone inoltre i metodi per verificare se è stato inserito un codice sviluppatore valido e per verificare i codici inseriti
 */
public interface Setting { 

    /**
    * Metodo che ritorna le preferenze riguardanti la modalità di fruizione delle informazioni
    * @return  InstructionPreference Preferenze riguardanti la modalità di fruizione delle informazioni
    */
    InstructionPreference getInstructionPreference();

    /**
    * Metodo che ritorna le preferenze di percorso
    * @return  PathPreference Preferenze di percorso
    */
    PathPreference getPathPreference();

    /**
    * Metodo che verifica se è stato inserito in precedenza un codice sviluppatore valido
    * @return  booolean true se il codice sviluppatore è valido, false altrimenti
    */
    boolean isDeveloper();

    /**
    * Metodo che permette di modificare le impostazioni riguardanti le preferenze di fruizione delle istruzioni di navigazione
    * @param instructionPreference Preferenza riguardante la fruizione delle istruzioni di navigazione.
    */
    void setInstructionPreference(InstructionPreference instructionPreference);

    /**
    * Metodo che permette di modificare le impostazioni riguardanti le preferenze sul percorso di navigazione
    * @param pathPreference Preferenza riguardante il percorso di navigazione.
    */
    void setPathPreference(PathPreference pathPreference);

    /**
    * Metodo che passato una stringa in ingresso controlla se tale stringa rappresenta un codice sviluppatore valido
    * @param code Stringa rappresentante il codice sviluppatore
    * @return  boolean true se la stringa rappresenta un codice sviluppatore valido, false altrmenti
    */
    boolean unlockDeveloper(String code);

}

