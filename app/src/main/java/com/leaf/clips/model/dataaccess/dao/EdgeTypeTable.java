package com.leaf.clips.model.dataaccess.dao;
/**
 * @author Marco Zanella
 * @version 0.01
 * @since 0.01
 *
 *
 */

/**
 *Classe che rappresenta una ennupla della tabella EdgeType del database locale
 */
public class EdgeTypeTable {

    /**
     * Identificativo numerico dell'oggetto EdgeTypeTable
     */
    private final int id;

    /**
     * Identificativo numerico che permette di identificare il tipo di Edge
     */
    private final String typeName;

    /**
     * Costruttore della classe EdgeTypeTable
     * @param id Identificativo numerico dell'oggetto EdgeTypeTable
     * @param typeName Identificativo numerico che permette di identificare il tipo di Edge
     */
    public EdgeTypeTable(int id, String typeName) {
        this.id = id;
        this.typeName = typeName;
    }

    /**
     * Metodo che restituisce l'identificativo numerico dell'oggetto EdgeTypeTable
     * @return  int
     */
    public int getId() {
        return id;
    }

    /**
     * Metodo che restituisce l'identificativo numerico che permette di identificare il tipo di Edge
     * @return  String
     */
    public String getTypeName() {
        return typeName;
    }

}
