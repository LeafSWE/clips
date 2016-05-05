package com.leaf.clips.model.dataaccess.dao;
/**
 * @author Marco Zanella
 * @version 0.01
 * @since 0.01
 *
 *
 */

/**
 *.Interfaccia che espone i metodi per un DAO per accedere alla tabella "Category" del database locale
 */
public interface CategoryDao {

    /**
     * Metodo che permette la rimozione delle informazioni di un edificio dalla tabella "Category" del database locale
     * @param id Identificativo della categoria da rimuovere dal database locale
     */
    void deleteCategory(int id);

    /**
     * Metodo per recuperare le informazioni di una categoria dal database locale tramite il suo identificativo, sotto forma di oggetto CategoryTable
     * @param id Identificativo della categoria di cui recuperare le informazioni
     * @return  CategoryTable
     */
    CategoryTable findCategory(int id);

    /**
     * Metodo che permette l'inserimento di una categoria nella tabella "Category" del database locale
     * @param toInsert Oggetto di tipo CategoryTable che contiene le informazioni della categoria
     * @return  int
     */
    int insertCategory(CategoryTable toInsert);

    /**
     * Metodo per aggiornare le informazioni di una categoria nella tabella "Category" del database locale
     * @param id Identificativo della categoria di cui aggiornare le informazioni
     * @param toUpdate Oggetto che contiene le informazioni aggiornate della categoria
     * @return  boolean
     */
    boolean updateCategory(int id, CategoryTable toUpdate);

}
