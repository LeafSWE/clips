package com.leaf.clips.model.beacon;
/**
 * @author Federico Tavella
 * @version 0.02
 * @since 0.00
 */

/**
 * Class Description
 */
public enum PeriodType{
    BACKGROUND, FOREGROUND, BACKGROUND_BETWEEN, FOREGROUND_BETWEEN, ERROR;
    
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
