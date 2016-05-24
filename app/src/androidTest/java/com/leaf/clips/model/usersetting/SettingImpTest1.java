package com.leaf.clips.model.usersetting;
/**
 * @author Federico Tavella
 * @version 0.01
 * @since 0.00
 */

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.InstrumentationTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Unit Test 1
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class SettingImpTest1 {

    private Setting setting;

    @Before
    public void init(){
        setting = new SettingImp(InstrumentationRegistry.getTargetContext());
    }

    @Test
    public void shouldStoreInstructionChanges(){
        setting.setInstructionPreference(InstructionPreference.DEFAULT);
        Assert.assertEquals(true, InstructionPreference.DEFAULT == setting.getInstructionPreference());

        setting.setInstructionPreference(InstructionPreference.SONAR);
        Assert.assertEquals(true, InstructionPreference.SONAR == setting.getInstructionPreference());

        setting.setInstructionPreference(InstructionPreference.TEXT_TO_SPEECH);
        Assert.assertEquals(true, InstructionPreference.TEXT_TO_SPEECH == setting.getInstructionPreference());
    }

    @Test
    public void shouldStorePathChanges(){
        setting.setPathPreference(PathPreference.NO_PREFERENCE);
        Assert.assertEquals(true, PathPreference.NO_PREFERENCE == setting.getPathPreference());

        setting.setPathPreference(PathPreference.ELEVATOR_PREFERENCE);
        Assert.assertEquals(true, PathPreference.ELEVATOR_PREFERENCE == setting.getPathPreference());

        setting.setPathPreference(PathPreference.STAIR_PREFERENCE);
        Assert.assertEquals(true, PathPreference.STAIR_PREFERENCE == setting.getPathPreference());
    }
}
