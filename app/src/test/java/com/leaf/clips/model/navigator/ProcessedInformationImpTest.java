package com.leaf.clips.model.navigator;
/**
 * @author Eduard Bicego
 * @version 0.01
 * @since 0.00
 */

import com.leaf.clips.model.navigator.graph.edge.EnrichedEdge;
import com.leaf.clips.model.navigator.graph.navigationinformation.PhotoInformation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * TU7
 */
@RunWith(MockitoJUnitRunner.class)
public class ProcessedInformationImpTest {

    private static final String FAKE_BASIC_INFORMATION = "FakeBasicInformation";
    private static final String FAKE_DETAIL_INFORMATION = "FakeDetailInformation";
    private static final String FAKE_STARTER_INFORMATION = "FakeStartInformation";
    @Mock
    private PhotoInformation mockPhotoInformation;


    private ProcessedInformationImp processedInformation;
    private ProcessedInformationImp processedInformationWithStart;

    @Mock
    private EnrichedEdge mockEnrichedEdge;


    @Before
    public void setUp() throws Exception {
        when(mockEnrichedEdge.getBasicInformation()).thenReturn(FAKE_BASIC_INFORMATION);
        when(mockEnrichedEdge.getDetailedInformation()).thenReturn(FAKE_DETAIL_INFORMATION);
        when(mockEnrichedEdge.getPhotoInformation()).thenReturn(mockPhotoInformation);

        processedInformation = new ProcessedInformationImp(mockEnrichedEdge);
        processedInformationWithStart = new ProcessedInformationImp(mockEnrichedEdge,
                FAKE_STARTER_INFORMATION);
    }

    /*@Test
    public void testBuildProcessedInformationImp() throws Exception {
        when(mockEnrichedEdge.getBasicInformation()).thenReturn(FAKE_BASIC_INFORMATION);
        when(mockEnrichedEdge.getDetailInformation()).thenReturn(FAKE_DETAIL_INFORMATION);
        when(mockEnrichedEdge.getPhotoInformation()).thenReturn(mockPhotoInformation);

        processedInformationImp = new ProcessedInformationImp(mockEnrichedEdge);
    }*/

    @Test
    public void testShouldGettingAttribute() throws Exception {
        assertEquals("Different basic info", processedInformation.getProcessedBasicInstruction(),
                FAKE_BASIC_INFORMATION);
        assertEquals("Different detail info", processedInformation.getDetailedInstruction(),
                FAKE_DETAIL_INFORMATION);
        assertEquals("Different PhotoInformation info", processedInformation.getPhotoInstruction(),
                mockPhotoInformation);
    }

    @Test
    public void testShouldGetBasicWithStartInfo() throws Exception {
        assertEquals("Different start + basic info",
                processedInformationWithStart.getProcessedBasicInstruction(),
                FAKE_STARTER_INFORMATION + " " + FAKE_BASIC_INFORMATION);
    }
}