package com.leaf.clips.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.test.espresso.contrib.NavigationViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.leaf.clips.R;
import com.leaf.clips.presenter.DeveloperUnlockerActivity;
import com.leaf.clips.presenter.HomeActivity;
import com.leaf.clips.presenter.MainDeveloperActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.DrawerActions.open;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * @author Davide Castello
 * @version 0.01
 * @since 0.01
 *
 * TU144
 * Viene verificato che sia possibile inserire un codice per sbloccare le funzionalità sviluppatore
 * e visualizzare un messaggio di errore in caso di codice errato.
 *
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class DeveloperUnlockerViewImpTest {

    HomeActivity testActivity;

    @Rule
    public ActivityTestRule<HomeActivity> mActivityRule =
            new ActivityTestRule<>(HomeActivity.class);

    @Before
    public void init() throws InterruptedException {
        testActivity = mActivityRule.getActivity();
    }

    @Test
    public void shouldShowAnErrorMessage(){
        // Vado nell'Area Sviluppatore, inserisco un codice non corretto e verifico che venga
        // mostrato il messaggio d'errore
        onView(withId(R.id.drawer_layout_home)).perform(open());
        onView(withId(R.id.nav_view_home)).perform(NavigationViewActions
                .navigateTo(R.id.nav_developer));
        onView(withId(R.id.developer_code)).perform(typeText("password"),
                closeSoftKeyboard());
        onView(withId(R.id.developer_login_button)).perform(click());
        onView(withText(R.string.dialog_dev_code_error)).check(matches(isDisplayed()));
    }

    @Test
    public void shouldShowMainDeveloperView() {
        Intents.init();
        onView(withId(R.id.drawer_layout_home)).perform(open());
        onView(withId(R.id.nav_view_home))
                .perform(NavigationViewActions.navigateTo(R.id.nav_developer));
        onView(withId(R.id.developer_code)).perform(typeText("miriade"),
                closeSoftKeyboard());
        onView(withId(R.id.developer_login_button)).perform(click());
        intended(hasComponent(MainDeveloperActivity.class.getName()));
        Intents.release();
    }
}
