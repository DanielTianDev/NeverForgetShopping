package nier.neverforgetshopping;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import OfflineServices.SharedPreferencesList;
import RestfulServices.RestfulController;


/**
 * Created by Dan on 2017-11-23.
 */

public class ShoppingListActivity extends Activity{

    private ListView mShoppingList;
    private EditText mItemEdit;
    private Button mAddButton;
    private ArrayAdapter<String> mAdapter;

    private ArrayList<String> shoppingListItems = new ArrayList<String>();
    public static String mostRecentItem = "";
    SharedPreferencesList localPreferences;

    RestfulController restController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        mShoppingList =  findViewById(R.id.shopping_listView);
        mItemEdit = findViewById(R.id.item_editText);
        mAddButton = findViewById(R.id.add_button);
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        mShoppingList.setAdapter(mAdapter);

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String item = mItemEdit.getText().toString();
                mAdapter.add(item);
                mAdapter.notifyDataSetChanged();
                mItemEdit.setText("");
                mostRecentItem = item;
                localPreferences.AddItemToSharedPreferences(item, shoppingListItems);
            }
        });

        Context context = getApplicationContext();
        localPreferences = new SharedPreferencesList(context);
        GetItemsFromPreferences();
        /*
        try{
            new RetrieveFeedTask().execute(SERVER_URL+"api.php");
           // Log.e(MainActivity.DEBUG_DEFAULT_TAG, "test msg: " + teststr);
        }catch (Exception e){
            Log.e(MainActivity.DEBUG_DEFAULT_TAG, e.getMessage());
        }
        Timer timer = new Timer();
        timer.schedule(new RetrieveAllItems(), 0, 2500);
        */
    }


    public boolean GetItemsFromPreferences(){

        try{
            localPreferences.GetItemsFromPreferences(shoppingListItems, mAdapter);
        }catch (Exception e){
            Log.e(MainActivity.DEBUG_DEFAULT_TAG, "Exception occured at GetItemsFromPreferences");
            return false;
        }

        return true;
    }


    public void ClearShoppingList(View view){
        mostRecentItem = "cleared";
        localPreferences.ClearSharedPreferences(view, shoppingListItems, mAdapter);
    }






    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*
    public static String getHTML(String urlToRead) throws Exception {
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlToRead);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        return result.toString();
    }


    class RetrieveFeedTask extends AsyncTask<String, Void, String> {

        private Exception exception;

        protected String doInBackground(String... urls) {
            try {



                teststr =  getHTML(SERVER_URL + "api.php");
               // Log.e(MainActivity.DEBUG_DEFAULT_TAG, "doing in background " + teststr  );



                String[] listItems = teststr.split("&&&");


                shoppingListItems.clear();

                for(int i=0;i<listItems.length;i++){
                    shoppingListItems.add(listItems[i]);

                }


                for(int i=0;i<shoppingListItems.size();i++){
                    if(!checkIfAdapterContains(shoppingListItems.get(i))){
                        mAdapter.add(shoppingListItems.get(i));
                        mAdapter.notifyDataSetChanged();
                        mItemEdit.setText("");
                    }


                }

                return teststr;

            } catch (Exception e) {
                this.exception = e;
                return "exception";
            }
        }

        protected void onPostExecute() {


            Log.e(MainActivity.DEBUG_DEFAULT_TAG, "on post executed");

            // TODO: check this.exception
            // TODO: do something with the feed
        }
    }

    class AddItemTask extends AsyncTask<String, Void, String> {

        private Exception exception;

        protected String doInBackground(String... itemName) {
            try {



                getHTML(SERVER_URL+"insertItem.php?itemName=" + itemName[0]);

                return teststr;

            } catch (Exception e) {
                this.exception = e;
                return "exception";
            }
        }

        protected void onPostExecute() {


            //Log.e(MainActivity.DEBUG_DEFAULT_TAG, "on post executed");

            // TODO: check this.exception
            // TODO: do something with the feed
        }
    }

    class RetrieveAllItems extends TimerTask {
        public void run() {

            try{
                new RetrieveFeedTask().execute(SERVER_URL+"api.php");
                //ViewGroup vg = findViewById (R.id.shopping_listView);
                //vg.invalidate();
                //Log.e(MainActivity.DEBUG_DEFAULT_TAG, "test msg: " + teststr);
            }catch (Exception e){
                Log.e(MainActivity.DEBUG_DEFAULT_TAG, e.getMessage());
            }


        }
    }

    boolean checkIfAdapterContains(String value){
        for (int i = 0; i < mShoppingList.getAdapter().getCount(); i++){
            if(value.equals((String)mShoppingList.getAdapter().getItem(i))){
                return true;
            }
        }

        return false;
    }

    */
}
