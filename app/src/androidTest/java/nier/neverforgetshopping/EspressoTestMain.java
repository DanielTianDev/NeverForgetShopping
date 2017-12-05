package nier.neverforgetshopping;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.EspressoKey;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.KeyEvent;
import junit.framework.AssertionFailedError;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class EspressoTestMain {
    @Rule
    public ActivityTestRule<ShoppingListActivity> mActivityRule =
            new ActivityTestRule<>(ShoppingListActivity.class);

    @Test
    public void testAddItemUI() {
        // Type text and then press the button.
        String itemName = "Milk";
        onView(withId(R.id.item_editText))
                .perform(typeText(itemName), closeSoftKeyboard());

        onView(withId(R.id.add_button)).perform(click());
        // Check that the text was changed.
        //onView(withId(R.id.shopping_listView)).check(matches(withText("1")));
        //onView(withContentDescription("Navigate up")).perform(click());
        assertEquals(itemName, ShoppingListActivity.mostRecentItem);
        try{
            Thread.sleep(2000);
        }catch (Exception e){

        }
    }

    @Test
    public void testDeleteItemUI() {
        // Type text and then press the button.
        onView(withId(R.id.btnClear)).perform(click());
        //
        assertEquals("cleared", ShoppingListActivity.mostRecentItem);
        try{
            Thread.sleep(2000);
        }catch (Exception e){

        }
    }

}