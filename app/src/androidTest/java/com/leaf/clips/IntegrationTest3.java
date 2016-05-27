package com.leaf.clips;
/**
 * @author Federico Tavella
 * @version 0.01
 * @since 0.00
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ServiceTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.content.LocalBroadcastManager;
import android.test.InstrumentationTestCase;
import android.test.suitebuilder.annotation.LargeTest;
import android.test.suitebuilder.annotation.SmallTest;
import android.util.Log;

import com.leaf.clips.model.beacon.BeaconManagerAdapter;
import com.leaf.clips.model.beacon.BeaconRanger;
import com.leaf.clips.model.beacon.Logger;
import com.leaf.clips.model.beacon.LoggerImp;
import com.leaf.clips.model.beacon.MyBeacon;
import com.leaf.clips.model.beacon.MyBeaconImp;

import org.altbeacon.beacon.AltBeacon;
import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.Region;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

/**
 * Integration Test 3
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class IntegrationTest3 extends InstrumentationTestCase{

    private BeaconRanger ranger;

    private Intent intent;

    private Context context = InstrumentationRegistry.getTargetContext();

    private final ServiceTestRule mServiceRule = new ServiceTestRule();

    private BroadcastReceiver receiver;

    private Logger logger;

    private Beacon beacon;

    @Before
    public void init() throws TimeoutException {

        intent = new Intent(context, BeaconManagerAdapter.class);
        IBinder binder = mServiceRule.bindService(intent);
        ranger = ((BeaconManagerAdapter.LocalBinder) binder).getService();

        logger = new LoggerImp();

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {


                String act = intent.getAction();
                if(act == "beaconsDetected"){
                    Log.i("TEST3","ECCOCI");
                    PriorityQueue<MyBeacon> pmb = ((PriorityQueue<MyBeacon>)intent.getSerializableExtra("queueOfBeacons"));
                    assertEquals(beacon.getId1().toString(), pmb.peek().getUUID());
                    logger.add(pmb);

                }
                else
                    assertTrue(false); //NO LIST DETECTED
            }

        };

        List<Long> data = new LinkedList<>();
        data.add((long) 88);

        beacon = new MyBeaconImp(new AltBeacon.Builder().setId1("19235dd2-574a-4702-a42e-caccac06e325").setId2("1").setId3("1")
                .setRssi(-55).setTxPower(-55).setBluetoothAddress("prova").setDataFields(data)
                .setBeaconTypeCode(1).build());

        LocalBroadcastManager.getInstance(context).registerReceiver(receiver,
                new IntentFilter("beaconsDetected"));

        context.startService(intent);

    }

    @Test
    public void shouldSendBeaconsAndLogThem(){

        Runnable r = new Runnable() {
            @Override
            public void run() {
                List<Beacon> beaconsMock = new LinkedList<>();

                beaconsMock.add(beacon);

                UUID uuid = UUID.fromString("19235dd2-574a-4702-a42e-caccac06e325");
                Region region = new Region("Region", Identifier.fromUuid(uuid), null, null);
                ((BeaconManagerAdapter) ranger).didRangeBeaconsInRegion(beaconsMock, region);
            }
        };
        new Thread(r).start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        StringBuffer buffer = logger.getData();
        String s = buffer.toString();
        s = s.substring(0, s.length()-3); //removing the last 3 \n
        assertEquals(beacon.toString(), s);

    }

}
