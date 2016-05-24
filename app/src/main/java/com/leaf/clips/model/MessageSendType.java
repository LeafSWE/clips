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

    /**
     * Metodo che permette di trasformare un intero in un oggetto MessageSendType
     * @param i intero da trasformare in MessageSendType
     * @return MessageSendType
     */
    public static MessageSendType fromInt(int i){
        switch(i){
            case 0 : 
                return VISIBLE_BEACON;
            
        }
        return ERROR;
    }

    /**
     * Metodo che permette di trasformare un oggetto MessageSendType in intero
     * @param messageSendType oggetto da trasformare in intero
     * @return int
     */
    public static int toInt(MessageSendType messageSendType){
        switch (messageSendType){
            case VISIBLE_BEACON:
                return 0;
        }
        return -1;
    }
}

