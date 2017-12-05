package OfflineServices;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import org.json.JSONArray;

import java.util.ArrayList;

import nier.neverforgetshopping.MainActivity;

/**
 * Created by Daniel on 2017-12-05.
 */

public class SharedPreferencesList {


    public static final String PREFERENCE_FILE_KEY = "NFSPref";

    SharedPreferences sharedPref;

    public SharedPreferencesList(Context appContext){
        sharedPref = appContext.getSharedPreferences(PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
    }


    public void AddItemToSharedPreferences(String item, ArrayList<String> shoppingListItems){
        shoppingListItems.add(item);
        SharedPreferences.Editor editor = sharedPref.edit();
        JSONArray jsArray = new JSONArray(shoppingListItems);
        String jsonString = jsArray.toString();
        editor.putString(PREFERENCE_FILE_KEY, jsonString);
        editor.commit();
    }

    public void GetItemsFromPreferences(ArrayList<String> shoppingListItems,  ArrayAdapter<String> mAdapter){

        try{
            String jsonString = sharedPref.getString(PREFERENCE_FILE_KEY, "");
            JSONArray jsonArr = new JSONArray(jsonString);
            //Log.e(MainActivity.DEBUG_DEFAULT_TAG, jsonArr.toString());

            for (int i = 0; i < jsonArr.length(); i++)
            {
                shoppingListItems.add(jsonArr.getString(i));
            }

            for(int i=0;i<shoppingListItems.size();i++){
                mAdapter.add(shoppingListItems.get(i));
                mAdapter.notifyDataSetChanged();
            }
        }catch (Exception e){
            Log.e(MainActivity.DEBUG_DEFAULT_TAG, "Exception at GetItemsFromPreferences");
        }

    }

    public void ClearSharedPreferences(View view, ArrayList<String> shoppingListItems, ArrayAdapter<String> mAdapter){
        try{
            SharedPreferences.Editor editor = sharedPref.edit();    //
            editor.clear();
            editor.commit();

            shoppingListItems.clear();
            mAdapter.clear();
            mAdapter.notifyDataSetChanged();

        }catch (Exception e){
            Log.e(MainActivity.DEBUG_DEFAULT_TAG, "Exception at ClearSharedPreferences");
        }
    }

}
