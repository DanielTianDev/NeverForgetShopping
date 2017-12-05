package nier.neverforgetshopping;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.json.JSONArray;
import org.junit.Test;
import org.junit.runner.RunWith;

import OfflineServices.SharedPreferencesList;

import static OfflineServices.SharedPreferencesList.PREFERENCE_FILE_KEY;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ShoppingInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("nier.neverforgetshopping", appContext.getPackageName());
    }


    @Test
    public void useSharedPreferences() throws Exception{ //add an item first, and then check
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        SharedPreferencesList localPreferences;
        localPreferences = new SharedPreferencesList(appContext);

        SharedPreferences.Editor editor = localPreferences.sharedPref.edit();
        editor.putString(PREFERENCE_FILE_KEY, "testString");
        editor.commit();

        String test = localPreferences.sharedPref.getString(PREFERENCE_FILE_KEY, "");


        if (test.length() < 1) throw new AssertionError("get shared preferences failed");
    }

    @Test
    public void testClearSharedPreferences() throws Exception{ //add an item first, and then check
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        SharedPreferencesList localPreferences;
        localPreferences = new SharedPreferencesList(appContext);
        SharedPreferences.Editor editor = localPreferences.sharedPref.edit();    //
        editor.clear();
        editor.commit();

        String test = localPreferences.sharedPref.getString(PREFERENCE_FILE_KEY, "");
        if (test.length() != 0) throw new AssertionError("clear preferences failed");
    }

}
