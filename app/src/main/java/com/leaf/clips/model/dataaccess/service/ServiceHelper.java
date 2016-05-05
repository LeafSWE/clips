package com.leaf.clips.model.dataaccess.service;

import com.leaf.clips.model.dataaccess.dao.RemoteBuildingDao;
import com.leaf.clips.model.dataaccess.dao.RemoteCategoryDao;
import com.leaf.clips.model.dataaccess.dao.RemoteDaoFactory;
import com.leaf.clips.model.dataaccess.dao.RemoteEdgeDao;
import com.leaf.clips.model.dataaccess.dao.RemoteEdgeTypeDao;
import com.leaf.clips.model.dataaccess.dao.RemotePhotoDao;
import com.leaf.clips.model.dataaccess.dao.RemotePointOfInterestDao;
import com.leaf.clips.model.dataaccess.dao.RemoteRegionOfInterestDao;
import com.leaf.clips.model.dataaccess.dao.RemoteRoiPoiDao;
import com.leaf.clips.model.dataaccess.dao.SQLiteBuildingDao;
import com.leaf.clips.model.dataaccess.dao.SQLiteCategoryDao;
import com.leaf.clips.model.dataaccess.dao.SQLiteDaoFactory;
import com.leaf.clips.model.dataaccess.dao.SQLiteEdgeDao;
import com.leaf.clips.model.dataaccess.dao.SQLiteEdgeTypeDao;
import com.leaf.clips.model.dataaccess.dao.SQLitePhotoDao;
import com.leaf.clips.model.dataaccess.dao.SQLitePointOfInterestDao;
import com.leaf.clips.model.dataaccess.dao.SQLiteRegionOfInterestDao;
import com.leaf.clips.model.dataaccess.dao.SQLiteRoiPoiDao;

/**
 * @author Davide Castello
 * @version 0.01
 * @since 0.00
 *
 * Classe che rappresenta un aiutante per la costruzione di un DatabaseService
 */
public class ServiceHelper {

    /**
     * Metodo che viene utilizzato per ottenere un'istanza di DatabaseService
     * @param sqliteDaoFactory Un oggetto SQLiteDaoFactory necessaria per la
     *                         creazione degli oggetti DAO locali
     * @param remoteDaoFactory Un oggetto RemoteDaoFactory necessario per la
     *                         creazione degli oggetti DAO remoti
     * @param dbURL L'URL del database remoto
     * @return  DatabaseService
     */
    public static DatabaseService getService(SQLiteDaoFactory sqliteDaoFactory,
                                             RemoteDaoFactory remoteDaoFactory, String dbURL) {

        // recupero dalla SQLiteDaoFactory tutti gli oggetti DAO per il database locale
        SQLiteBuildingDao sqliteBuildingDao = (SQLiteBuildingDao)sqliteDaoFactory.getBuildingDao();
        SQLitePointOfInterestDao sqlitePOIDao =
                (SQLitePointOfInterestDao)sqliteDaoFactory.getPointOfInterestDao();
        SQLiteRegionOfInterestDao sqliteROIDao =
                (SQLiteRegionOfInterestDao)sqliteDaoFactory.getRegionOfInterestDao();
        SQLiteRoiPoiDao sqliteRoiPoiDao = (SQLiteRoiPoiDao)sqliteDaoFactory.getRoiPoiDao();
        SQLiteEdgeDao sqliteEdgeDao = (SQLiteEdgeDao)sqliteDaoFactory.getEdgeDao();
        SQLiteCategoryDao sqliteCategoryDao = (SQLiteCategoryDao)sqliteDaoFactory.getCategoryDao();
        SQLiteEdgeTypeDao sqliteEdgeTypeDao = (SQLiteEdgeTypeDao)sqliteDaoFactory.getEdgeTypeDao();
        SQLitePhotoDao sqlitePhotoDao = (SQLitePhotoDao)sqliteDaoFactory.getPhotoDao();

        // recupero dalla RemoteDaoFactory
        RemoteBuildingDao remoteBuildingDao = remoteDaoFactory.getBuildingDao();
        RemotePointOfInterestDao remotePOIDao = remoteDaoFactory.getPointOfInterestDao();
        RemoteRegionOfInterestDao remoteROIDao = remoteDaoFactory.getRegionOfInterestDao();
        RemoteRoiPoiDao remoteRoiPoiDao = remoteDaoFactory.getRoiPoiDao();
        RemoteEdgeDao remoteEdgeDao = remoteDaoFactory.getEdgeDao();
        RemoteCategoryDao remoteCategoryDao = remoteDaoFactory.getCategoryDao();
        RemoteEdgeTypeDao remoteEdgeTypeDao = remoteDaoFactory.getEdgeTypeDao();
        RemotePhotoDao remotePhotoDao = remoteDaoFactory.getPhotoDao();

        // costruisco un oggetto di tipo RegionOfInterestService
        RegionOfInterestService roiService = new RegionOfInterestService(sqliteROIDao, remoteROIDao,
                sqliteRoiPoiDao, remoteRoiPoiDao);

        // costruisco un oggetto di tipo PointOfInterestService
        PointOfInterestService poiService = new PointOfInterestService(sqlitePOIDao, remotePOIDao,
                sqliteRoiPoiDao, remoteRoiPoiDao, sqliteCategoryDao, remoteCategoryDao);

        // costruisco un oggetto di tipo PhotoService
        PhotoService photoService = new PhotoService(sqlitePhotoDao, remotePhotoDao);

        // costruisco un oggetto di tipo EdgeService
        EdgeService edgeService = new EdgeService(sqliteEdgeDao, remoteEdgeDao, sqliteEdgeTypeDao,
                remoteEdgeTypeDao, photoService, roiService);

        // costruisco l'oggetto BuildingService da ritornare
        BuildingService buildingService = new BuildingService(dbURL, sqliteBuildingDao,
                remoteBuildingDao, roiService, poiService, edgeService);
        return buildingService;

    }

}
