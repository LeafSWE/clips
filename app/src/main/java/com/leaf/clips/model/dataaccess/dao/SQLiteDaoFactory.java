package com.leaf.clips.model.dataaccess.dao;

/**
 * @author Marco Zanella
 * @version 0.01
 * @since 0.01
 */

import android.database.sqlite.SQLiteDatabase;

/**
 *Classe che rappresenta la factory per creare tutti gli oggetti DAO locali
 */
public class SQLiteDaoFactory {

    /**
     * Il database locale 
     */
    private final SQLiteDatabase database;

    /**
     * Costruttore della classe SQLiteDaoFactory
     * @param database Il database locale 
     */
    public SQLiteDaoFactory(SQLiteDatabase database) {
        this.database = database;
    }

    /**
     * Metodo che viene utilizzato per ottenere un'istanza di SQLiteBuildingDao
     * @return  BuildingDao
     */
    public BuildingDao getBuildingDao() {
        return new SQLiteBuildingDao(database);
    }

    /**
     * Metodo che viene utilizzato per ottenere un'istanza di SQLiteCategoryDao
     * @return  CategoryDao
     */
    public CategoryDao getCategoryDao() {
        return new SQLiteCategoryDao(database);
    }

    /**
     * Metodo che viene utilizzato per ottenere un'istanza di SQLiteEdgeDao
     * @return  EdgeDao
     */
    public EdgeDao getEdgeDao() {
        return new SQLiteEdgeDao(database);
    }

    /**
     * Metodo che viene utilizzato per ottenere un'istanza di SQLiteEdgeTypeDao
     * @return  EdgeTypeDao
     */
    public EdgeTypeDao getEdgeTypeDao() {
        return new SQLiteEdgeTypeDao(database);
    }

    /**
     * Metodo che viene utilizzato per ottenere un'istanza di SQLitePhotoDao
     * @return  PhotoDao
     */
    public PhotoDao getPhotoDao() {
        return new SQLitePhotoDao(database);
    }

    /**
     * Metodo che viene utilizzato per ottenere un'istanza di SQLitePointOfInterestDao
     * @return  PointOfInterestDao
     */
    public PointOfInterestDao getPointOfInterestDao() {
        return new SQLitePointOfInterestDao(database);
    }

    /**
     * Metodo che viene utilizzato per ottenere un'istanza di SQLiteRegionOfInterestDao
     * @return  RegionOfInterestDao
     */
    public RegionOfInterestDao getRegionOfInterestDao() {
        return new SQLiteRegionOfInterestDao(database);
    }

    /**
     * Metodo che viene utilizzato per ottenere un'istanza di SQLiteRoiPoiDao
     * @return  RoiPoiDao
     */
    public RoiPoiDao getRoiPoiDao() {
        return new SQLiteRoiPoiDao(database);
    }

}

