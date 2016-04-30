package com.leaf.clips.model.usersetting;
/**
* @author Federico Tavella
* @version 0.01
* @since 0.00
* 
* 
*/

/** 
*Classe enumeratore che espone le possibili preferenze riguardanti il percorso di navigazione
*/ 
public enum PathPreference {
    NO_PREFERENCE, ELEVATOR_PREFERENCE, STAIR_PREFERENCE;

    public static PathPreference fromInt(int i){
        switch (i){
            case 0:
                return NO_PREFERENCE;
            case 1:
                return ELEVATOR_PREFERENCE;
            case 2:
                return STAIR_PREFERENCE;
        }
        return NO_PREFERENCE; // TODO: 01/05/2016 Cosa fare in caso di errore?
    }

    public static int toInt(PathPreference pathPreference){
        switch (pathPreference){
            case NO_PREFERENCE:
                return 0;
            case ELEVATOR_PREFERENCE:
                return 1;
            case STAIR_PREFERENCE:
                return 2;
        }
        return -1;
    }

}

