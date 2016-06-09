package com.leaf.clips;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.rule.ActivityTestRule;
import android.webkit.ValueCallback;
import android.webkit.WebView;

import com.leaf.clips.presenter.HelpActivity;
import com.leaf.clips.view.HelpViewImp;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.lang.reflect.Field;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * @author Marco Zanella
 * @version 0.01
 * @since 0.01
 */
public class SystemTest17 {
    HelpActivity testActivity;

    @Rule
    public ActivityTestRule<HelpActivity> mActivityRule =
            new ActivityTestRule<>(HelpActivity.class);

    @Before
    public void setUp() throws Exception {
        testActivity = mActivityRule.getActivity();
        try {
            onView(withText(R.string.ok)).perform(click());
            onView(withText(R.string.ok)).perform(click());
            onView(withText(R.string.ok)).perform(click());
            onView(withText(R.string.ok)).perform(click());
        } catch (NoMatchingViewException e) {}

        ConnectivityManager connectivityManager =
                (ConnectivityManager)testActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        Assert.assertTrue("il telefono deve essere collegato a internet per eseguire il test",
                (networkInfo != null && networkInfo.isConnected()));
        Thread.sleep(5000);
    }

    @Test
    public void shouldAccessHelp() throws Exception {
        testActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Field fieldView = null;
                try {
                    fieldView = HelpActivity.class.getDeclaredField("view");
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
                fieldView.setAccessible(true);
                HelpViewImp helpViewImp = null;
                try {
                    helpViewImp = (HelpViewImp) fieldView.get(testActivity);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

                Field fieldWebView = null;
                try {
                    fieldWebView = HelpViewImp.class.getDeclaredField("webView");
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
                fieldWebView.setAccessible(true);
                WebView wvbrowser = null;
                try {
                    wvbrowser = (WebView) fieldWebView.get(helpViewImp);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

                wvbrowser.evaluateJavascript(
                        "(function() { return ('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>'); })();",
                        new ValueCallback<String>() {
                            @Override
                            public void onReceiveValue(String html) {
                                Assert.assertNotNull(html);
                            }
                        });
            }
        });
        Thread.sleep(1000);
    }

}