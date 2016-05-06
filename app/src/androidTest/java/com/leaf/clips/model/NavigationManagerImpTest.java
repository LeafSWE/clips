package com.leaf.clips.model;
/**
 * @author Federico Tavella
 * @version 0.01
 * @since 0.00
 */

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import com.leaf.clips.model.navigator.NavigationExceptions;
import com.leaf.clips.model.navigator.PathException;
import com.leaf.clips.model.navigator.graph.MapGraph;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Unit test 171
 */

// TODO: 5/6/16 Eduard controlla questi test 

@RunWith(AndroidJUnit4.class)
@SmallTest
public class NavigationManagerImpTest {

    NavigationManager navigationManager;

    @Before
    public void init(){
        //navigationManager = new NavigationManagerImp(new MapGraph(), InstrumentationRegistry.getTargetContext());
    }

    @Test
    public void testVuoto(){
        // TODO: 5/6/16 Test da rimuovere 
    }
/*
    @Test(expected = NavigationExceptions.class)
    public void shouldNotReturnNavigationInstructions() throws NavigationExceptions {
        //navigationManager.getAllNavigationInstruction();
    }

    @Test(expected = NoBeaconSeenException.class)
    public void shouldNotReturnNextNavigationInstruction() throws PathException {
        // TODO: 5/6/16 Test da fare
        //navigationManager.getNextInstruction();
    }
    */
}
