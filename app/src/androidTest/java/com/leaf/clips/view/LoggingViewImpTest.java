package com.leaf.clips.view;

import android.support.test.rule.ActivityTestRule;
import android.widget.ListView;

import com.leaf.clips.R;
import com.leaf.clips.model.beacon.MyBeacon;
import com.leaf.clips.model.beacon.MyBeaconImp;
import com.leaf.clips.presenter.LoggingActivity;

import junit.framework.Assert;

import org.altbeacon.beacon.Beacon;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.LinkedList;

/**
 * @author Marco Zanella
 * @version 0.01
 * @since 0.01
 */
public class LoggingViewImpTest {
    LoggingActivity testActivity;

    @Rule
    public ActivityTestRule<LoggingActivity> mActivityRule =
            new ActivityTestRule<>(LoggingActivity.class);

    @Before
    public void init() throws Exception {
        testActivity = mActivityRule.getActivity();
        Thread.sleep(1000);
        testActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Field field = null;
                try {
                    field = LoggingActivity.class.getDeclaredField("view");
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
                field.setAccessible(true);
                LoggingViewImp view = null;
                try {
                    view = (LoggingViewImp) field.get(testActivity);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                StringBuffer stringBuffer = new StringBuffer();
                LinkedList<Long> l = new LinkedList<Long>();
                l.add((long)0);
                l.add((long)0);
                l.add((long)0);
                l.add((long)0);
                l.add((long)0);
                l.add((long)0);
                l.add((long)0);
                l.add((long) 0);
                MyBeacon b = new MyBeaconImp(new Beacon.Builder()
                        .setId1("f7826da6-4fa2-4e98-8024-bc5b71e0893e")
                        .setId2("666").setId3("1024").setDataFields(l).build());
                stringBuffer.append(b.toString());
                view.setBeaconListAdapter(stringBuffer);
            }
        });
        Thread.sleep(5000);
    }

    //tu146
    @Test
    public void shouldShowBeaconIdentifiers() throws Exception {
        String s = (String) (((ListView) testActivity.findViewById(R.id.visible_beacons_list)).getAdapter().getItem(0));
        Assert.assertTrue(s.contains("f7826da6-4fa2-4e98-8024-bc5b71e0893e"));
        Assert.assertTrue(s.contains("666"));
        Assert.assertTrue(s.contains("1024"));
    }
}