package nier.neverforgetshopping;

import android.app.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import RestfulServices.RestfulController;

import static RestfulServices.RestfulController.ConvertStreamToString;
import static RestfulServices.RestfulController.DATA_DELIMITER;
import static RestfulServices.RestfulController.GET_DATA_ENDPOINT;
import static RestfulServices.RestfulController.SERVER_URL;


/**
 * Created by Kirito on 2017-12-05.
 */

public class ShoppingListNetworkedActivity extends Activity {
    private ListView mShoppingList;
    private EditText mItemEdit;
    private Button mAddButton;
    private ArrayAdapter<String> mAdapter;

    private TextView userNameText;
    String userName;

    private ArrayList<String> shoppingListItems = new ArrayList<String>();
    RestfulController restController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main_networked);
        mShoppingList =  findViewById(R.id.shopping_listView);
        mItemEdit = findViewById(R.id.item_editText);
        mAddButton = findViewById(R.id.add_button);
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        mShoppingList.setAdapter(mAdapter);

        userNameText = findViewById(R.id.shopping_userName);

        Intent intent = getIntent();
        userName = intent.getStringExtra(MainActivity.EXTRA_KEY);
        userNameText.setText("Welcome back: " +userName);
        restController = new RestfulController(userName, shoppingListItems, mAdapter);

        new RetrieveShoppingListCall().execute(userName);
        //restController.retrieveDataFromServer();


        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String item = mItemEdit.getText().toString();
                mAdapter.add(item);
                mAdapter.notifyDataSetChanged();
                mItemEdit.setText("");

                restController.addDataToDB(item);

            }
        });

        Timer timer = new Timer();
        timer.schedule(new RetrieveAllItems(), 0, 2500);
    }


    public void ClearShoppingListNetworked(View view){

        try{
            restController.clearDataFromList();
            shoppingListItems.clear();
            mAdapter.clear();
            mAdapter.notifyDataSetChanged();
        }catch(Exception e){
            Log.e(MainActivity.DEBUG_DEFAULT_TAG, "ClearShoppingListNetworked exception");
        }

    }

    private class RetrieveShoppingListCall extends AsyncTask<String, Integer, ArrayList<String>> {
        protected ArrayList<String> doInBackground(String... username) {


            try{
                // Create URL
                URL appEndPoint = new URL(SERVER_URL +GET_DATA_ENDPOINT + username[0]);

                // Create connection
                HttpURLConnection myConnection =
                        (HttpURLConnection) appEndPoint.openConnection();


                if (myConnection.getResponseCode() == 200) {
                    // Success
                    // Further processing here
                    Log.e(MainActivity.DEBUG_DEFAULT_TAG, "response 200");


                    InputStream responseBodyStream = myConnection.getInputStream();

                    String responseString = ConvertStreamToString(responseBodyStream);

                    if(responseString.length() > 0){

                        String[] listItems = responseString.split(DATA_DELIMITER);
                        for(int i=0;i<listItems.length;i++){
                            if(!checkIfItemExists(listItems[i]))
                                shoppingListItems.add(listItems[i]);
                        }
                    }else{
                        shoppingListItems.clear();
                    }

                    return shoppingListItems;

                } else { //error has occured

                    Log.e(MainActivity.DEBUG_DEFAULT_TAG, "response code was not 200, try again later");
                }


            }catch(Exception e){
                Log.e(MainActivity.DEBUG_DEFAULT_TAG, "RetrieveShoppingListCall error occured");
            }

            return null;

        }


        protected void onPostExecute(ArrayList<String> result) {

            Log.e(MainActivity.DEBUG_DEFAULT_TAG, "result:  " + result.size());

            mAdapter.clear();

            for(int i=0;i<result.size();i++){
                mAdapter.add(result.get(i));
            }

            mAdapter.notifyDataSetChanged();
            mAdapter.notifyDataSetInvalidated();
        }
    }

    boolean checkIfItemExists(String item){
        for(int i=0;i<shoppingListItems.size();i++){
            if(shoppingListItems.get(i).equalsIgnoreCase(item))
                return true;
        }
        return false;
    }

    class RetrieveAllItems extends TimerTask {
        public void run() {

            try{
                new RetrieveShoppingListCall().execute(userName);
            }catch (Exception e){
                Log.e(MainActivity.DEBUG_DEFAULT_TAG, e.getMessage());
            }


        }
    }


}
