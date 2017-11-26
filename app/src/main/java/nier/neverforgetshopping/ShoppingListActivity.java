package nier.neverforgetshopping;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by Kirito on 2017-11-23.
 */

public class ShoppingListActivity extends Activity{


    ArrayList<String> shoppingList = null;
    ArrayAdapter<String> adapter = null;
    ListView lv = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        //startDateInput = (EditText) findViewById(R.id.startDateInput);
        //endDateInput = (EditText) findViewById(R.id.endDateInput);
    }


    void listsomething(){

        shoppingList = new ArrayList<>();
        Collections.addAll(shoppingList, "Eggs", "Yogurt", "Milk", "Bananas", "Apples", "Tide with bleach", "Cascade");
        shoppingList.addAll(Arrays.asList("Napkins", "Dog Food", "Chap-stick", "Bread"));
        shoppingList.add("Sunscreen");
        shoppingList.add("Toothpaste");

        //adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, shoppingList);

        //lv = (ListView) findViewById(R.id.listView);

        lv.setAdapter(adapter);

        


    }

}
