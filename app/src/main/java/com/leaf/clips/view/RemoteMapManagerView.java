package com.leaf.clips.view;

import com.leaf.clips.model.dataaccess.dao.BuildingTable;

import java.util.Collection;

/**
 * @author Oscar Elia Conti
 * @version 0.01
 * @since 0.00
 */


/**
 *Interfaccia che espone i metodi per aggiornare la UI contenente le mappe delle quali è possibile effettuare il download dal server
 */

public interface RemoteMapManagerView {
    /**
     * Metodo utilizzate per visualizzare le mappe che è possibile scaricare da un server remoto
     * @param buildingTables Collegamento tra la lista delle mappe che è possibile scaricare e la view in cui esse devono essere mostrate
     */
    void setRemoteMaps(Collection<BuildingTable> buildingTables);

    /**
     * Metodo utilizzato per infomrare l'utente che la mappa selezionata è già installata sul dispositivo
     */
    void showMapAlreadyPresent ();
}
