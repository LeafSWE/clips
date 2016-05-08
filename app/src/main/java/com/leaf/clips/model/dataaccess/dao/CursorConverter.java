package com.leaf.clips.model.dataaccess.dao;
/**
 * @author Marco Zanella
 * @version 0.01
 * @since 0.01
 *
 *
 */

import android.database.Cursor;

/**
 *Interfaccia base per la conversione di un Cursor in un oggetto
 */
public interface CursorConverter {

    /**
     * Metodo che viene utilizzato per convertire il risultato di una query sul database locale in un oggetto
     * @param cursor Risultato della query sul database locale
     * @return  Object
     */
    Object cursorToType(Cursor cursor);

}
