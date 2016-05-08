package com.leaf.clips.model;
/**
* @author Federico Tavella
* @version 0.01
* @since 0.00
* 
* 
*/

/** 
*Classe che rappresenta l'etichetta di un messaggio scambiato all'interno dell'applicazione
 * contenente una lista di beacon
*/ 
public enum MessageSendType {
    VISIBLE_BEACON, ERROR;
    
    public static MessageSendType fromInt(int i){
        switch(i){
            case 0 : 
                return VISIBLE_BEACON;
            
        }
        return ERROR;
    }
    
    public static int toInt(MessageSendType messageSendType){
        switch (messageSendType){
            case VISIBLE_BEACON:
                return 0;
        }
        return -1;
    }
}

