package com.leaf.clips.model.navigator;
/**
 * @author Eduard Bicego
 * @version 0.01
 * @since 0.00
 */

import android.support.test.runner.AndroidJUnit4;

import com.leaf.clips.BuildConfig;
import com.leaf.clips.model.beacon.MyBeacon;
import com.leaf.clips.model.compass.Compass;
import com.leaf.clips.model.navigator.algorithm.DijkstraPathFinder;
import com.leaf.clips.model.navigator.graph.MapGraph;
import com.leaf.clips.model.navigator.graph.area.PointOfInterest;
import com.leaf.clips.model.navigator.graph.area.RegionOfInterest;
import com.leaf.clips.model.navigator.graph.edge.EnrichedEdge;
import com.leaf.clips.model.navigator.graph.navigationinformation.PhotoInformation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

import static org.mockito.Mockito.when;

import static org.junit.Assert.*;

/**
 * TU33, TU34, TU35
 */
@RunWith(AndroidJUnit4.class)
public class NavigatorImpTest {

    final static String FAKE_BASIC_INFO = "FakeBasicInfo";
    final static String FAKE_DETAILED_INFO = "FakeDetailedInfo";
    final static String FAKE_WRONG_DIRECTION_INFO = "Direzione sbagliata, voltati";
    final static String FAKE_CORRECT_DIRECTION_INFO = "Direzione corretta";


    //@Mock
    private PhotoInformation mockEdgePhotos = Mockito.mock(PhotoInformation.class);

    private NavigatorImp navigatorImp;

    //@Mock
    private Compass mockCompass = Mockito.mock(Compass.class);

    //@Mock
    private RegionOfInterest mockStartRoi = Mockito.mock(RegionOfInterest.class);

    //@Mock
    private PointOfInterest mockNullEndPoi = Mockito.mock(PointOfInterest.class);

    //@Mock
    private RegionOfInterest mockEndRoi = Mockito.mock(RegionOfInterest.class);

    //@Mock
    private PriorityQueue<MyBeacon> mockVisibleBeacons = Mockito.mock(PriorityQueue.class);

    //@Mock
    private PriorityQueue<MyBeacon> mockNoVisibleBeacons = Mockito.mock(PriorityQueue.class);

    //@Mock
    private MyBeacon mockNearBeacon = Mockito.mock(MyBeacon.class);

    //@Mock
    private MyBeacon mockNoNearBeacon = Mockito.mock(MyBeacon.class);

    //@Mock
    private MapGraph mockMapGraph = Mockito.mock(MapGraph.class);

    //@Mock
    private DijkstraPathFinder mockDijkstraPathFinder = Mockito.mock(DijkstraPathFinder.class);

    private List<EnrichedEdge> fakeShortestPath;

    //@Mock
    private EnrichedEdge mockEnrichedEdge = Mockito.mock(EnrichedEdge.class);

    //@Mock
    private EnrichedEdge mockEnrichedEdge2 = Mockito.mock(EnrichedEdge.class);

    //@Mock
    private PointOfInterest mockEndPoi = Mockito.mock(PointOfInterest.class);

    private List<RegionOfInterest> fakeBelongingRoi;

    private List<ProcessedInformation> fakeProcessedInformation;

    //@Mock
    private RegionOfInterest mockRoiInPoi = Mockito.mock(RegionOfInterest.class);

    //@Mock
    private RegionOfInterest mockRoiWithBeacon = Mockito.mock(RegionOfInterest.class);

    @Before
    public void setUp() throws Exception {
        navigatorImp = new NavigatorImp(mockCompass, mockDijkstraPathFinder);

        fakeShortestPath = new ArrayList<>();
        fakeShortestPath.add(mockEnrichedEdge);
        fakeShortestPath.add(mockEnrichedEdge2);

        fakeBelongingRoi = new ArrayList<>();
        fakeBelongingRoi.add(mockRoiInPoi);

        when(mockCompass.getLastCoordinate()).thenReturn(180f);
        when(mockDijkstraPathFinder.calculatePath(mockMapGraph, mockStartRoi, mockRoiInPoi))
                .thenReturn(fakeShortestPath);
        when(mockEndPoi.getAllBelongingROIs()).thenReturn(fakeBelongingRoi);

        for (EnrichedEdge mockEE : fakeShortestPath) {
            when(mockEE.getBasicInformation()).thenReturn(FAKE_BASIC_INFO);
            when(mockEE.getDetailedInformation()).thenReturn(FAKE_DETAILED_INFO);
            when(mockEE.getPhotoInformation()).thenReturn(mockEdgePhotos);
            when(mockEE.getEndPoint()).thenReturn(mockRoiWithBeacon);
        }

        when(mockVisibleBeacons.peek()).thenReturn(mockNearBeacon);
        when(mockNoVisibleBeacons.peek()).thenReturn(mockNoNearBeacon);
        when(mockRoiWithBeacon.contains(mockVisibleBeacons.peek())).thenReturn(true);

        fakeProcessedInformation = new ArrayList<>();
        fakeProcessedInformation.add(new ProcessedInformationImp(mockEnrichedEdge));
        fakeProcessedInformation.add(new ProcessedInformationImp(mockEnrichedEdge2));
        //destinazione
        fakeProcessedInformation.add(new ProcessedInformationImp());

    }

    /**
     * TU33
     * In particolare deve essere testato che venga lanciata l'eccezione NoGraphSetException nel
     * caso in cui venga richiesto di calcolare un percorso e non sia stato settato alcun grafo..
     * @throws Exception
     */
    @Test(expected = NoGraphSetException.class)
    public void testCalculatePathWithNoGraphSet() throws Exception {
        navigatorImp.calculatePath(mockStartRoi, mockNullEndPoi);
        fail("Test not throw NoGraphException");
    }

    /**
     * TU33
     * mentre deve essere lanciata l'eccezione NoNavigationInformationException nel caso in cui si
     * richieda un percorso e quest'ultimo non è ancora stato calcolato
     * @throws Exception
     */
    @Test(expected = NoNavigationInformationException.class)
    public void testGetPathWithoutSetIt() throws Exception {
        navigatorImp.toNextRegion(mockVisibleBeacons);
    }

    /**
     * TU33
     * Viene testato che sia possibile settare ad un oggetto Navigator il grafo su cui si vuole
     * effettuare la navigazione e calcolare un percorso da un certo punto ad un altro.
     * TODO: N.B. Non esegue test in NavigatorImp.isShortestPath()
     * @throws NavigationExceptions
     */
    @Test
    public void testCalculatePath() throws NavigationExceptions {
        navigatorImp.setGraph(mockMapGraph);
        navigatorImp.calculatePath(mockStartRoi, mockEndPoi);
        List<EnrichedEdge> resultFakeShortestPath = navigatorImp.getPath();
        if (BuildConfig.DEBUG && !resultFakeShortestPath.containsAll(fakeShortestPath)) {
            fail("Shortest path is not same of shortest path expected");
        }
    }

    @Test(expected = PathException.class)
    public void testSetGraph() throws Exception {
        navigatorImp.setGraph(mockMapGraph);
        navigatorImp.calculatePath(mockStartRoi, mockNullEndPoi);
    }

    /**
     * TU34
     * Viene testato che sia possibile, settato un grafo e calcolato un percorso, ottenere tutte le
     * istruzioni di navigazione.
     * TODO: N.B. il test utilizza la classe ProcessedInformation istanziandola
     * @throws NavigationExceptions
     */
    @Test
    public void testGetAllInstruction() throws NavigationExceptions{
        navigatorImp.setGraph(mockMapGraph);
        navigatorImp.calculatePath(mockStartRoi, mockEndPoi);
        List<ProcessedInformation> resultFakeProcessedInformation =
                navigatorImp.getAllInstructions();
        if ( resultFakeProcessedInformation.size() != fakeProcessedInformation.size()) {
            assertEquals(fakeProcessedInformation.size(), resultFakeProcessedInformation.size());
            fail("List of ProcessedInformation don't have same size");
        }
        else {
            Iterator<ProcessedInformation> resultIterator = resultFakeProcessedInformation.iterator();
            Iterator<ProcessedInformation> compareIterator = fakeProcessedInformation.iterator();
            while(resultIterator.hasNext() && compareIterator.hasNext()) {
                ProcessedInformation resultEdge = resultIterator.next();
                ProcessedInformation compareEdge = compareIterator.next();
                if (!resultEdge.getDetailedInstruction().equals(compareEdge.getDetailedInstruction())) {
                    fail("Detailed processed information is different from expected");
                }
                if (!resultEdge.getProcessedBasicInstruction().equals(compareEdge.getProcessedBasicInstruction())) {
                    fail("Basic processed information is different from expected");
                }
                if (resultEdge.getPhotoInstruction() != null && !resultEdge.getPhotoInstruction().
                        equals(compareEdge.getPhotoInstruction())) {
                    fail("Photo processed information is different from expected");
                }
            }
        }
    }

    /**
     * TU34
     * In particolare deve essere lanciata un'eccezione di tipo
     * NoNavigationInformationException nel caso in cui si richiedano le informazioni riguardanti
     * un percorso ma queste non siano disponibili poichè non è stato settato un grafo o non è
     * ancora stato calcolato un percorso
     * @throws Exception
     */
    @Test(expected = NoNavigationInformationException.class)
    public void testGetAllInstructionsException() throws Exception {
        navigatorImp.getAllInstructions();
    }

    /**
     * TU35
     * 	Viene testato che sia possibile, settato un grafo e calcolato un percorso, ottenere le
     * 	informazioni di navifìgazione una di seguito all'altra.
     * @throws NavigationExceptions
     */
    @Test
    public void testToNextRegion() throws NavigationExceptions {
        navigatorImp.setGraph(mockMapGraph);
        navigatorImp.calculatePath(mockStartRoi, mockEndPoi);
        ProcessedInformation resultProcessedInfo = navigatorImp.toNextRegion(mockVisibleBeacons);
        if (!resultProcessedInfo.getDetailedInstruction().equals(FAKE_DETAILED_INFO)) {
            fail("Result processed detailed info not equal as expected");
        }
        if (!resultProcessedInfo.getProcessedBasicInstruction().equals(
                FAKE_WRONG_DIRECTION_INFO + " " + FAKE_BASIC_INFO)) {
            fail("Result processed basic info not equal as expected");
        }
    }

    /**
     * TU35
     *  In particolare deve essere lanciata un'eccezione di tipo NoNavigationInformationException
     *  nel caso in cui si richiedano le informazioni riguardanti un percorso ma queste non siano
     *  disponibili poichè non è stato settato un grafo o non è ancora stato calcolato un percorso.
     * @throws Exception
     */
    @Test(expected = NoNavigationInformationException.class)
    public void testToNextRegionWithoutPathOrGraph() throws Exception {
        navigatorImp.toNextRegion(mockVisibleBeacons);
    }

    /**
     * TU35
     * Inoltre viene lanciata un'eccezione PathException nel caso in cui il beacon più potente
     * rilevato non faccia parte del percorso previsto
     */
    @Test (expected = PathException.class)
    public void testToNextRegionWrongBeacon() throws Exception {
        navigatorImp.setGraph(mockMapGraph);
        navigatorImp.calculatePath(mockStartRoi, mockEndPoi);
        navigatorImp.toNextRegion(mockNoVisibleBeacons);
    }
}