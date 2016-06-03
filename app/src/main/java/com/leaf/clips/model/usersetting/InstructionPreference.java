package com.leaf.clips.model.usersetting;
/**
* @author Federico Tavella
* @version 0.01
* @since 0.00
* 
* 
*/

/** 
*Classe enumeratore che espone le possibili preferenze riguardanti la fruizione delle informazioni
*/ 
public enum InstructionPreference {
    DEFAULT, TEXT_TO_SPEECH, SONAR, NO_PREFERENCE;

    /**
     * Metodo per convertire un intero ad una InstructionPreference
     * @param i Intero da convertire a InstructionPreference
     * @return InstructionPreference Preferenza dell'utente
     */
    public static InstructionPreference fromInt(int i){
        switch (i){
            case 0:
                return DEFAULT;
            case 1:
                return TEXT_TO_SPEECH;
            case 2:
                return SONAR;
        }
        return NO_PREFERENCE;
    }

    /**
     * Metodo per convertire una InstructionPreference ad un intero
     * @param instructionPreference PathPreference da convertire ad un intero
     * @return int Intero rappresentante la preferenza dell'utente
     */
    public static int toInt(InstructionPreference instructionPreference){
        switch (instructionPreference){
            case DEFAULT:
                return 0;
            case TEXT_TO_SPEECH:
                return 1;
            case SONAR:
                return 2;
        }
        return -1;
    }
}

