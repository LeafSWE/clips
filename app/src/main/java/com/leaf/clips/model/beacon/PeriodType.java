package com.leaf.clips.model.beacon;
/**
 * @author Federico Tavella
 * @version 0.02
 * @since 0.00
 */

/**
 * Classe enumeratore che serve per descrivere i periodi di di scansione dei beacon
 */
public enum PeriodType{
    BACKGROUND, FOREGROUND, BACKGROUND_BETWEEN, FOREGROUND_BETWEEN, ERROR;

    /**
     * Metodo che permette di trasformare un oggetto PeriodType in intero
     * @param periodType oggetto da trasformare in intero
     * @return int
     */
    static public int toInt(PeriodType periodType){
        switch (periodType){
            case BACKGROUND:
                return 0;
            case FOREGROUND:
                return 1;
            case BACKGROUND_BETWEEN:
                return 2;
            case FOREGROUND_BETWEEN:
                return 3;
            
        }
        return -1;    
    }

    /**
     * Metodo che permette di trasformare un intero in un oggetto PeriodType
     * @param i intero da trasformare in PeriodType
     * @return PeriodType
     */
    static public PeriodType fromInt(int i){
        switch (i){
            case 0:
                return BACKGROUND;
            case 1:
                return FOREGROUND;
            case 2:
                return BACKGROUND_BETWEEN;
            case 3:
                return FOREGROUND_BETWEEN;
            
        }
        return ERROR;
    }
}
