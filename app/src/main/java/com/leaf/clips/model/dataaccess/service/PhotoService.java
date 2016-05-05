package com.leaf.clips.model.dataaccess.service;

import com.google.gson.JsonObject;

import com.leaf.clips.model.dataaccess.dao.PhotoTable;
import com.leaf.clips.model.dataaccess.dao.RemotePhotoDao;
import com.leaf.clips.model.dataaccess.dao.SQLitePhotoDao;
import com.leaf.clips.model.navigator.graph.navigationinformation.PhotoRef;

import java.net.URI;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Davide Castello
 * @version 0.01
 * @since 0.00
 *
 * Classe che rappresenta il layer Service tra gli oggetti PhotoRef e gli oggetti DAO corrispettivi
 */
public class PhotoService {

    /**
     * Oggetto di utility per la conversione da JSON a PhotoTable
     */
    private RemotePhotoDao remotePhotoDao;

    /**
     * Oggetto che rappresenta un DAO per la tabella "Photo" del database locale
     */
    private SQLitePhotoDao sqlitePhotoDao;

    /**
     * Costruttore della classe PhotoService
     * @param sqlitePhoto Oggetto che rappresenta un DAO per la tabella "Photo" del database locale
     * @param remotePhoto Oggetto di utility per la conversione da JSON a PhotoTable
     */
    public PhotoService(SQLitePhotoDao sqlitePhoto, RemotePhotoDao remotePhoto) {
        sqlitePhotoDao = sqlitePhoto;
        remotePhotoDao = remotePhoto;
    }

    /**
     * Metodo per la conversione di un JsonObject in un oggetto PhotoTable, che verrà inserito
     * nel database locale
     * @param object Oggetto JsonObject che contiene le informazioni di una foto
     */
    public void convertAndInsert(JsonObject object) {
        PhotoTable table = remotePhotoDao.fromJSONToTable(object);
        int i = sqlitePhotoDao.insertPhoto(table);
    }

    /**
     * Metodo per rimuovere una foto di un Edge dal database locale
     * @param id Identificativo numerico della foto da rimuovere
     */
    public void deletePhoto(int id) {
        sqlitePhotoDao.deletePhoto(id);
    }

    /**
     * Metodo per recuperare tutte le foto di un Edge dal database locale
     * @param id Identificativo dell'Edge di cui si vuole recuperare tutte le foto
     * @return  Collection<PhotoRef>
     */
    public Collection<PhotoRef> findAllPhotosOfEdge(int id) {
        Collection<PhotoTable> tables = sqlitePhotoDao.findAllPhotosOfEdge(id);
        Iterator<PhotoTable> iter = tables.iterator();
        List<PhotoRef> photoRefs = new LinkedList<PhotoRef>();
        while(iter.hasNext()) {
            PhotoTable p = iter.next();
            PhotoRef ref = fromTableToBo(p);
            photoRefs.add(ref);
        }
        return photoRefs;
    }

    /**
     * Metodo per recuperare una foto ricercandola nel database locale
     * @param id Identificativo numerico della foto da recuperare
     * @return  PhotoRef
     */
    public PhotoRef findPhoto(int id) {
        PhotoTable photoTable = sqlitePhotoDao.findPhoto(id);
        PhotoRef photoRef = fromTableToBo(photoTable);
        return photoRef;
    }

    /**
     * Metodo per la costruzione di oggetto PhotoRef a partire da un PhotoTable
     * @param photoTable Oggetto contenente le informazioni della foto
     * @return  PhotoRef
     */
    private PhotoRef fromTableToBo(PhotoTable photoTable) {
        String uri_s = photoTable.getUrl();
        URI uri = URI.create(uri_s);
        return new PhotoRef(uri);
    }

}
