package com.leaf.clips.view;

/**
 * @author Oscar Elia Conti
 * @version 0.01
 * @since 0.00
 */
public interface LogInformationView {
    /**
     * Metodo utilizzato per visualizzare la lista delle informazioni di un certo beacon
     * @param logInfo Collegamento tra la lista delle informazione dei log e la view in cui esse devono essere mostrate
     * @return  void
     */
    void setBeaconAdapter(String logInfo);
}
