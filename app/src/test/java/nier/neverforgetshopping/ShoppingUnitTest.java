package nier.neverforgetshopping;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ShoppingUnitTest {

    private ArrayList<String> shoppingList = new ArrayList<String>();


    @Test
    public void addToList_isCorrect() throws Exception {
        shoppingList.add("milk");
        shoppingList.add("chocolate");
        if (shoppingList.size() != 2) throw new AssertionError("Adding to shopping list exception");
    }

    @Test
    public void readFromList_isCorrect() throws Exception {
        shoppingList.add("Water");
        String tempItem = shoppingList.get(0);
        if (tempItem == null) throw new AssertionError("ReadFromList exception");
    }

    @Test
    public void deleteFromList_isCorrect() throws Exception {
        shoppingList.clear();
        if (shoppingList.size() > 0) throw new AssertionError("delete from list exception");
    }
}