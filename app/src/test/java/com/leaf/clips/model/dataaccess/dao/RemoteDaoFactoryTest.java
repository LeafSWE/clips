package com.leaf.clips.model.dataaccess.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Marco Zanella
 * @version 0.01
 * @since 0.01
 */
public class RemoteDaoFactoryTest {

    private RemoteBuildingDao remoteBuildingDao;
    private RemoteCategoryDao remoteCategoryDao;
    private RemoteEdgeDao remoteEdgeDao;
    private RemoteEdgeTypeDao remoteEdgeTypeDao;
    private RemotePhotoDao remotePhotoDao;
    private RemotePointOfInterestDao remotePointOfInterestDao;
    private RemoteRegionOfInterestDao remoteRegionOfInterestDao;
    private RemoteRoiPoiDao remoteRoiPoiDao;
    private RemoteDaoFactory remoteDaoFactory;

    @Before
    public void init() {
        remoteDaoFactory = new RemoteDaoFactory();
        remoteBuildingDao = remoteDaoFactory.getBuildingDao();
        remoteCategoryDao = remoteDaoFactory.getCategoryDao();
        remoteEdgeDao = remoteDaoFactory.getEdgeDao();
        remoteEdgeTypeDao = remoteDaoFactory.getEdgeTypeDao();
        remotePhotoDao = remoteDaoFactory.getPhotoDao();
        remotePointOfInterestDao = remoteDaoFactory.getPointOfInterestDao();
        remoteRegionOfInterestDao = remoteDaoFactory.getRegionOfInterestDao();
        remoteRoiPoiDao = remoteDaoFactory.getRoiPoiDao();
    }

    @Test
    public void testGetBuildingDao() throws Exception {
        Assert.assertNotNull(remoteBuildingDao);
    }

    @Test
    public void testGetCategoryDao() throws Exception {
        Assert.assertNotNull(remoteCategoryDao);
    }

    @Test
    public void testGetEdgeDao() throws Exception {
        Assert.assertNotNull(remoteEdgeDao);
    }

    @Test
    public void testGetEdgeTypeDao() throws Exception {
        Assert.assertNotNull(remoteEdgeTypeDao);
    }

    @Test
    public void testGetPhotoDao() throws Exception {
        Assert.assertNotNull(remotePhotoDao);
    }

    @Test
    public void testGetPointOfInterestDao() throws Exception {
        Assert.assertNotNull(remotePointOfInterestDao);
    }

    @Test
    public void testGetRegionOfInterestDao() throws Exception {
        Assert.assertNotNull(remoteRegionOfInterestDao);
    }

    @Test
    public void testGetRoiPoiDao() throws Exception {
        Assert.assertNotNull(remoteRoiPoiDao);
    }
}