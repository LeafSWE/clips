package com.leaf.clips;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.InstrumentationTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.leaf.clips.model.InformationManager;
import com.leaf.clips.model.NavigationManager;
import com.leaf.clips.model.usersetting.Setting;
import com.leaf.clips.presenter.DetailedInformationActivity;
import com.leaf.clips.presenter.DeveloperUnlockerActivity;
import com.leaf.clips.presenter.HomeActivity;
import com.leaf.clips.presenter.LogInformationActivity;
import com.leaf.clips.presenter.LoggingActivity;
import com.leaf.clips.presenter.MainDeveloperActivity;
import com.leaf.clips.presenter.MainDeveloperPresenter;
import com.leaf.clips.presenter.NavigationActivity;
import com.leaf.clips.presenter.NearbyPoiActivity;
import com.leaf.clips.presenter.PoiActivity;
import com.leaf.clips.presenter.PoiCategoryActivity;
import com.leaf.clips.presenter.PoiDescriptionActivity;
import com.leaf.clips.presenter.PreferencesActivity;
import com.leaf.clips.presenter.SearchSuggestionsProvider;

import junit.framework.Assert;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Field;

/**
 * @author Marco Zanella
 * @version 0.01
 * @since 0.01
 */

@RunWith(AndroidJUnit4.class)
@SmallTest
public class IntegrationTest17_18_19 extends InstrumentationTestCase {
    private HomeActivity homeActivity;
    private DeveloperUnlockerActivity developerUnlockerActivity;
    private PoiCategoryActivity poiCategoryActivity;
    private NearbyPoiActivity nearbyPoiActivity;
    private NavigationActivity navigationActivity;
    private MainDeveloperPresenter mainDeveloperPresenter;
    private MainDeveloperActivity mainDeveloperActivity;
    private LogInformationActivity logInformationActivity;
    private DetailedInformationActivity detailedInformationActivity;
    private LoggingActivity loggingActivity;
    private PreferencesActivity preferenceActivity;
    private SearchSuggestionsProvider searchSuggestionsProvider;
    private PoiActivity poiActivity;
    private PoiDescriptionActivity poiDescriptionActivity;

    @Rule
    public final ActivityTestRule<HomeActivity> mHomeActivityRule =
            new ActivityTestRule<HomeActivity>(HomeActivity.class, true, true);
    @Rule
    public final ActivityTestRule<DeveloperUnlockerActivity> mDeveloperUnlockerActivityRule =
            new ActivityTestRule<>(DeveloperUnlockerActivity.class, true, true);
    @Rule
    public final ActivityTestRule<PoiCategoryActivity> mPoiCategoryActivity =
            new ActivityTestRule<>(PoiCategoryActivity.class, true, true);
    @Rule
    public final ActivityTestRule<NearbyPoiActivity> mNearbyPoiActivity =
            new ActivityTestRule<>(NearbyPoiActivity.class, true, true);
    @Rule
    public final ActivityTestRule<NavigationActivity> mNavigationActivity =
            new ActivityTestRule<>(NavigationActivity.class, true, true);
    @Rule
    public final ActivityTestRule<MainDeveloperPresenter> mMainDeveloperPresenter =
            new ActivityTestRule<>(MainDeveloperPresenter.class, true, true);
    @Rule
    public final ActivityTestRule<MainDeveloperActivity> mMainDeveloperActivity =
            new ActivityTestRule<>(MainDeveloperActivity.class, true, true);
    @Rule
    public final ActivityTestRule<LogInformationActivity> mLogInformationActivity =
            new ActivityTestRule<>(LogInformationActivity.class, true, false);
    @Rule
    public final ActivityTestRule<DetailedInformationActivity> mDetailedInformationActivity =
            new ActivityTestRule<>(DetailedInformationActivity.class, true, false);
    @Rule
    public final ActivityTestRule<LoggingActivity> mLoggingActivity =
            new ActivityTestRule<>(LoggingActivity.class, true, true);
    @Rule
    public final ActivityTestRule<PreferencesActivity> mPreferencesActivity =
            new ActivityTestRule<>(PreferencesActivity.class, true, true);
    @Rule
    public final ActivityTestRule<PoiActivity> mPoiActivity =
            new ActivityTestRule<>(PoiActivity.class, true, true);
    @Rule
    public final ActivityTestRule<PoiDescriptionActivity> mPoiDescriptionActivity =
            new ActivityTestRule<>(PoiDescriptionActivity.class, true, false);

    public void setUp(){
        homeActivity = mHomeActivityRule.getActivity();
        developerUnlockerActivity = mDeveloperUnlockerActivityRule.getActivity();
        poiCategoryActivity = mPoiCategoryActivity.getActivity();
        nearbyPoiActivity = mNearbyPoiActivity.getActivity();
        navigationActivity = mNavigationActivity.getActivity();
        mainDeveloperPresenter = mMainDeveloperPresenter.getActivity();
        mainDeveloperActivity = mMainDeveloperActivity.getActivity();
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Intent i = new Intent(context, LogInformationActivity.class);
        i.putExtra("logNumber", "0");
        logInformationActivity = mLogInformationActivity.launchActivity(i);
        detailedInformationActivity = mDetailedInformationActivity.getActivity();
        i = new Intent(context, DetailedInformationActivity.class);
        i.putExtra("poi_id", "0");
        loggingActivity = mLoggingActivity.getActivity();
        preferenceActivity = mPreferencesActivity.getActivity();
        poiActivity = mPoiActivity.getActivity();
        i.putExtra("poi_id", "0");
        poiDescriptionActivity = mPoiDescriptionActivity.getActivity();
    }

    @Test
    public void shouldInjectAllFields(){
        setUp();

        try {
            Field field = HomeActivity.class.getDeclaredField("informationManager");
            field.setAccessible(true);
            InformationManager informationManager = (InformationManager) field.get(homeActivity);
            Assert.assertNotNull(informationManager);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        try {
            Field field = DeveloperUnlockerActivity.class.getDeclaredField("userSetting");
            field.setAccessible(true);
            Setting userSetting = (Setting) field.get(developerUnlockerActivity);
            Assert.assertNotNull(userSetting);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        try {
            Field field = PoiCategoryActivity.class.getDeclaredField("informationManager");
            field.setAccessible(true);
            InformationManager informationManager = (InformationManager) field.get(poiCategoryActivity);
            Assert.assertNotNull(informationManager);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        try {
            Field field = NearbyPoiActivity.class.getDeclaredField("informationManager");
            field.setAccessible(true);
            InformationManager informationManager = (InformationManager) field.get(nearbyPoiActivity);
            Assert.assertNotNull(informationManager);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        try {
            Field field = NavigationActivity.class.getDeclaredField("informationManager");
            field.setAccessible(true);
            InformationManager informationManager = (InformationManager) field.get(navigationActivity);
            Assert.assertNotNull(informationManager);
            field = NavigationActivity.class.getDeclaredField("navigationManager");
            field.setAccessible(true);
            NavigationManager navigationManager = (NavigationManager) field.get(navigationActivity);
            Assert.assertNotNull(navigationManager);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        try {
            Field field = MainDeveloperPresenter.class.getDeclaredField("userSetting");
            field.setAccessible(true);
            Setting userSetting = (Setting) field.get(mainDeveloperPresenter);
            Assert.assertNotNull(userSetting);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        try {
            Field field = MainDeveloperActivity.class.getDeclaredField("infoManager");
            field.setAccessible(true);
            InformationManager informationManager = (InformationManager) field.get(mainDeveloperActivity);
            Assert.assertNotNull(informationManager);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        try {
            Field field = LogInformationActivity.class.getDeclaredField("infoManager");
            field.setAccessible(true);
            InformationManager informationManager = (InformationManager) field.get(logInformationActivity);
            Assert.assertNotNull(informationManager);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        try {
            Field field = DetailedInformationActivity.class.getDeclaredField("infoManager");
            field.setAccessible(true);
            InformationManager informationManager = (InformationManager) field.get(detailedInformationActivity);
            Assert.assertNotNull(informationManager);
            field = DetailedInformationActivity.class.getDeclaredField("navigationManager");
            field.setAccessible(true);
            NavigationManager navigationManager = (NavigationManager) field.get(detailedInformationActivity);
            Assert.assertNotNull(navigationManager);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        try {
            Field field = LoggingActivity.class.getDeclaredField("informationManager");
            field.setAccessible(true);
            InformationManager informationManager = (InformationManager) field.get(loggingActivity);
            Assert.assertNotNull(informationManager);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        try {
            Field field = PreferencesActivity.class.getDeclaredField("setting");
            field.setAccessible(true);
            Setting userSetting = (Setting) field.get(preferenceActivity);
            Assert.assertNotNull(userSetting);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        try {
            Field field = PoiActivity.class.getDeclaredField("informationManager");
            field.setAccessible(true);
            InformationManager informationManager = (InformationManager) field.get(poiActivity);
            Assert.assertNotNull(informationManager);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        try {
            Field field = PoiDescriptionActivity.class.getDeclaredField("infoManager");
            field.setAccessible(true);
            InformationManager informationManager = (InformationManager) field.get(poiDescriptionActivity);
            Assert.assertNotNull(informationManager);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldInjectTheRightCardinality(){
        setUp();

        try {
            Field field = HomeActivity.class.getDeclaredField("informationManager");
            field.setAccessible(true);
            InformationManager informationManager0 = (InformationManager) field.get(homeActivity);

            field = DeveloperUnlockerActivity.class.getDeclaredField("userSetting");
            field.setAccessible(true);
            Setting userSetting0 = (Setting) field.get(developerUnlockerActivity);

            field = PoiCategoryActivity.class.getDeclaredField("informationManager");
            field.setAccessible(true);
            InformationManager informationManager1 = (InformationManager) field.get(poiCategoryActivity);
            Assert.assertNotNull(informationManager1);

            field = NearbyPoiActivity.class.getDeclaredField("informationManager");
            field.setAccessible(true);
            InformationManager informationManager2 = (InformationManager) field.get(nearbyPoiActivity);
            Assert.assertNotNull(informationManager2);

            field = NavigationActivity.class.getDeclaredField("informationManager");
            field.setAccessible(true);
            InformationManager informationManager3 = (InformationManager) field.get(navigationActivity);
            Assert.assertNotNull(informationManager3);
            field = NavigationActivity.class.getDeclaredField("navigationManager");
            field.setAccessible(true);
            NavigationManager navigationManager0 = (NavigationManager) field.get(navigationActivity);

            field = MainDeveloperPresenter.class.getDeclaredField("userSetting");
            field.setAccessible(true);
            Setting userSetting1 = (Setting) field.get(mainDeveloperPresenter);

            field = MainDeveloperActivity.class.getDeclaredField("infoManager");
            field.setAccessible(true);
            InformationManager informationManager4 = (InformationManager) field.get(mainDeveloperActivity);

            field = LogInformationActivity.class.getDeclaredField("infoManager");
            field.setAccessible(true);
            InformationManager informationManager5 = (InformationManager) field.get(logInformationActivity);

            field = DetailedInformationActivity.class.getDeclaredField("infoManager");
            field.setAccessible(true);
            InformationManager informationManager6 = (InformationManager) field.get(detailedInformationActivity);
            field = DetailedInformationActivity.class.getDeclaredField("navigationManager");
            field.setAccessible(true);
            NavigationManager navigationManager1 = (NavigationManager) field.get(detailedInformationActivity);

            field = LoggingActivity.class.getDeclaredField("informationManager");
            field.setAccessible(true);
            InformationManager informationManager7 = (InformationManager) field.get(loggingActivity);

            field = PreferencesActivity.class.getDeclaredField("setting");
            field.setAccessible(true);
            Setting userSetting2 = (Setting) field.get(preferenceActivity);

            field = PoiActivity.class.getDeclaredField("informationManager");
            field.setAccessible(true);
            InformationManager informationManager8 = (InformationManager) field.get(poiActivity);

            field = PoiDescriptionActivity.class.getDeclaredField("informationManager");
            field.setAccessible(true);
            InformationManager informationManager9 = (InformationManager) field.get(poiDescriptionActivity);

            Assert.assertSame(informationManager0, informationManager1);
            Assert.assertSame(informationManager0, informationManager2);
            Assert.assertSame(informationManager0, informationManager3);
            Assert.assertSame(informationManager0, informationManager4);
            Assert.assertSame(informationManager0, informationManager5);
            Assert.assertSame(informationManager0, informationManager6);
            Assert.assertSame(informationManager0, informationManager7);
            Assert.assertSame(informationManager0, informationManager8);
            Assert.assertSame(informationManager0, informationManager9);

            Assert.assertSame(userSetting0, userSetting1);
            Assert.assertSame(userSetting0, userSetting2);

            Assert.assertNotSame(navigationManager0, navigationManager1);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}