package com.leaf.clips.model.navigator;
/**
 * @author Eduard Bicego
 * @version 0.01
 * @since 0.00
 */
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * TU4
 */

public class BuildingInformationTest {

    private BuildingInformation buildingInformation = new BuildingInformation("FakeName", "FakeDescription",
                                                                            "FakeOpeningHours", "FakeAddress");

    @Test
    public void testGetAddress() throws Exception {
        String testString = buildingInformation.getAddress();
        assertEquals("Stringa differente da \"FakeAddress\"", "FakeAddress", testString);
    }

    @Test
    public void testGetDescription() throws Exception {
        String testString = buildingInformation.getDescription();
        assertEquals("Stringa differente da \"FakeDescription\"", "FakeDescription", testString);
    }

    @Test
    public void testGetName() throws Exception {
        String testString = buildingInformation.getName();
        assertEquals("Stringa differente da \"FakeName\"", "FakeName", testString);
    }

    @Test
    public void testGetOpeningHours() throws Exception {
        String testString = buildingInformation.getOpeningHours();
        assertEquals("Stringa differente da \"FakeOpeningHours\"", "FakeOpeningHours", testString);
    }

    @Test
    public void testToString() throws Exception {
        String testString = buildingInformation.toString();
        assertEquals("Stringa non stampata correttamente", "Nome: FakeName\nIndirizzo: " +
                        "FakeAddress\nDescrizione: FakeDescription\nOrario apertura: " +
                    "FakeOpeningHours", testString);
    }
}