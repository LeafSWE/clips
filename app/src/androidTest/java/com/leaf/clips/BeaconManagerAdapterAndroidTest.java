package com.leaf.clips;
/**
 * @author Federico Tavella
 * @version 0.02
 * @since 0.00
 */


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.test.InstrumentationRegistry;

import android.support.test.rule.ServiceTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.InstrumentationTestCase;
import android.test.suitebuilder.annotation.SmallTest;
import android.util.Log;

import com.leaf.clips.model.beacon.BeaconManagerAdapter;
import com.leaf.clips.model.beacon.BeaconRanger;
import com.leaf.clips.model.beacon.PeriodType;


import org.altbeacon.beacon.Beacon;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeoutException;

/**
 * Unit test 39 & 40
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class BeaconManagerAdapterAndroidTest extends InstrumentationTestCase{

    private BeaconRanger ranger;
    private boolean isBound;

    private Intent intent;

    private Context context = InstrumentationRegistry.getTargetContext();

    @Rule
    public final ServiceTestRule mServiceRule = new ServiceTestRule();

    @Before
    public void init() throws TimeoutException {

        intent = new Intent(context, BeaconManagerAdapter.class);
        IBinder binder = mServiceRule.bindService(intent);
        ranger = ((BeaconManagerAdapter.LocalBinder) binder).getService();

    }

    /*@Test
    public void shouldModifyScanPeriod() {
        // TODO: 30/04/2016
        long period = 101;

        ranger.modifyScanPeriod(period, PeriodType.FOREGROUND);
        Assert.assertEquals(period, ranger.getPeriod(PeriodType.FOREGROUND));
        ranger.modifyScanPeriod(period, PeriodType.BACKGROUND);
        Assert.assertEquals(period, ranger.getPeriod(PeriodType.BACKGROUND));

        ranger.modifyScanPeriod(period, PeriodType.FOREGROUND_BETWEEN);
        Assert.assertEquals(period, ranger.getPeriod(PeriodType.FOREGROUND_BETWEEN));

        ranger.modifyScanPeriod(period, PeriodType.BACKGROUND_BETWEEN);
        Assert.assertEquals(period, ranger.getPeriod(PeriodType.BACKGROUND_BETWEEN));
        context.stopService(intent);
    }*/

    @Test
    public void shouldSwitchToBackgroundMode(){

        ranger.setBackgroundMode(true);

        Assert.assertEquals(true, ranger.isBackground());

        ranger.setBackgroundMode(false);

        Assert.assertEquals(false, ranger.isBackground());
    }
}

