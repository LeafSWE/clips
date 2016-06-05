package com.leaf.clips.view;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.widget.TextView;

import com.leaf.clips.R;
import com.leaf.clips.model.navigator.graph.navigationinformation.PhotoInformation;
import com.leaf.clips.model.navigator.graph.navigationinformation.PhotoRef;
import com.leaf.clips.presenter.DetailedInformationActivity;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Field;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;

/**
 * @author Marco Zanella
 * @version 0.01
 * @since 0.01
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class DetailedInformationViewImpTest {

    DetailedInformationActivity testActivity;

    @Rule
    public ActivityTestRule<DetailedInformationActivity> mActivityRule =
            new ActivityTestRule<>(DetailedInformationActivity.class, true, false);

    @Before
    public void init() throws Exception {
        Intent intent = new Intent();
        intent.putExtra("detailed_info", "string");
        ArrayList<String> uris = new ArrayList<>();
        uris.add("...");
        intent.putExtra("photo_uri", uris);
        testActivity = mActivityRule.launchActivity(intent);
        //testActivity = mActivityRule.getActivity();
        Thread.sleep(1000);
        try {
            onView(withText(R.string.ok)).perform(click());
            Thread.sleep(500);
            onView(withText(R.string.ok)).perform(click());
            Thread.sleep(500);
            onView(withText(R.string.ok)).perform(click());
        } catch (Exception e) {}

        testActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Field field = null;
                try {
                    field = DetailedInformationActivity.class.getDeclaredField("view");
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
                field.setAccessible(true);
                DetailedInformationViewImp view = null;
                try {
                    view = (DetailedInformationViewImp) field.get(testActivity);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                Collection<PhotoRef> photoRefs = new HashSet<PhotoRef>();
                try {
                    photoRefs.add(new PhotoRef(1024, new URI("http://bucketclips01.s3.amazonaws.com/images/164134.jpg")));
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                view.setPhoto(new PhotoInformation(photoRefs));
                view.setDetailedDescription("DetailedDescription");
            }
        });

        Thread.sleep(5000);

    }

    @Test
    public void shouldSetPhotosAndDescription() throws Exception {
        Assert.assertEquals("DetailedDescription", ((TextView) testActivity.findViewById(R.id.detailed_description)).getText().toString());
        onData(anything())
                .inAdapterView(withId(R.id.gridView_images))
                .atPosition(0)
                .perform(click());
    }
}

