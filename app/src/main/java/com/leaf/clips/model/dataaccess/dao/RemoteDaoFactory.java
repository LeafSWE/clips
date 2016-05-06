package com.leaf.clips.model.dataaccess.dao;

/**
 * @author Marco Zanella
 * @version 0.01
 * @since 0.01
 */

/**
 *Classe che rappresenta la factory per creare tutti gli oggetti DAO remoti
 */
public class RemoteDaoFactory {

    /**
     * Costruttore di default per la classe RemoteDaoFactory
     */
    public RemoteDaoFactory() {}

    /**
     * Metodo che viene utilizzato per ottenere un'istanza di RemoteBuildingDao
     * @return  RemoteBuildingDao
     */
    public RemoteBuildingDao getBuildingDao() {
        return new RemoteBuildingDao();
    }

    /**
     * Metodo che viene utilizzato per ottenere un'istanza di RemoteCategoryDao
     * @return  RemoteCategoryDao
     */
    public RemoteCategoryDao getCategoryDao() {
        return new RemoteCategoryDao();
    }

    /**
     * Metodo che viene utilizzato per ottenere un'istanza di RemoteEdgeDao
     * @return  RemoteEdgeDao
     */
    public RemoteEdgeDao getEdgeDao() {
        return new RemoteEdgeDao();
    }

    /**
     * Metodo che viene utilizzato per ottenere un'istanza di RemoteEdgeTypeDao
     * @return  RemoteEdgeTypeDao
     */
    public RemoteEdgeTypeDao getEdgeTypeDao() {
        return new RemoteEdgeTypeDao();
    }

    /**
     * Metodo che viene utilizzato per ottenere un'istanza di RemotePhotoDao
     * @return  RemotePhotoDao
     */
    public RemotePhotoDao getPhotoDao() {
        return new RemotePhotoDao();
    }

    /**
     * Metodo che viene utilizzato per ottenere un'istanza di RemotePointOfInterestDao
     * @return  RemotePointOfInterestDao
     */
    public RemotePointOfInterestDao getPointOfInterestDao() {
        return new RemotePointOfInterestDao();
    }

    /**
     * Metodo che viene utilizzato per ottenere un'istanza di RemoteRegionOfInterestDao
     * @return  RemoteRegionOfInterestDao
     */
    public RemoteRegionOfInterestDao getRegionOfInterestDao() {
        return new RemoteRegionOfInterestDao();
    }

    /**
     * Metodo che viene utilizzato per ottenere un'istanza di RemoteRoiPoiDao
     * @return  RemoteRoiPoiDao
     */
    public RemoteRoiPoiDao getRoiPoiDao() {
        return new RemoteRoiPoiDao();
    }

}
