package RestfulServices;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import org.json.JSONArray;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import nier.neverforgetshopping.MainActivity;

/**
 * Created by Dan on 2017-12-05.
 *
 * Learning resources: https://code.tutsplus.com/tutorials/android-from-scratch-using-rest-apis--cms-27117
 *
 */

public class RestfulController {

    public final static String SERVER_URL =  "http://danieltian.com/"; // http://66.183.145.51 http://10.0.2.2:666/
    public final static String GET_DATA_ENDPOINT = "api.php?userName=";
    public final static String INSERT_DATA_ENDPOINT = "insertItem.php"; //2 url params, userName and itemName
    public final static String DELETE_DATA_ENDPOINT = "deleteall.php";

    public final static String DATA_DELIMITER = "&&&";


    String username;

    ArrayList<String> shoppingListItems;
    ArrayAdapter<String> mAdapter;

    public RestfulController(String username, ArrayList<String> sl, ArrayAdapter<String> mA){
        this.username = username;
        shoppingListItems = sl;
        mAdapter = mA;
    }

    public void retrieveDataFromServer(){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                // All your networking logic should be here
                try{
                    // Create URL
                    URL appEndPoint = new URL(SERVER_URL +GET_DATA_ENDPOINT + username);

                    // Create connection
                    HttpURLConnection myConnection =
                            (HttpURLConnection) appEndPoint.openConnection();


                    if (myConnection.getResponseCode() == 200) {
                        // Success
                        // Further processing here
                        Log.e(MainActivity.DEBUG_DEFAULT_TAG, "response 200");


                        InputStream responseBodyStream = myConnection.getInputStream();

                        String responseString = ConvertStreamToString(responseBodyStream);

                        Log.e(MainActivity.DEBUG_DEFAULT_TAG, responseString);

                        AddItemsToShoppingList(responseString);

                    } else { //error has occured

                        Log.e(MainActivity.DEBUG_DEFAULT_TAG, "response code was not 200, try again later");
                    }


                }catch(Exception e){
                    Log.e(MainActivity.DEBUG_DEFAULT_TAG, "retrieveDataFromServer error occured");
                }
            }
        });
    }

    public void addDataToDB(final String itemName){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                // All your networking logic should be here
                try{
                    // Create URL
                    URL appEndPoint = new URL(SERVER_URL +INSERT_DATA_ENDPOINT + "?userName=" + username + "&itemName=" + itemName);

                    // Create connection
                    HttpURLConnection myConnection =
                            (HttpURLConnection) appEndPoint.openConnection();


                    if (myConnection.getResponseCode() == 200) {
                        // Success
                        Log.e(MainActivity.DEBUG_DEFAULT_TAG, myConnection.getResponseMessage());

                    } else { //error has occured
                        Log.e(MainActivity.DEBUG_DEFAULT_TAG, "response code was not 200, try again later");
                    }


                }catch(Exception e){
                    Log.e(MainActivity.DEBUG_DEFAULT_TAG, "addDataToDB error occured");
                }
            }
        });
    }

    public void AddItemsToShoppingList(String dataStr){

        try{

            shoppingListItems.clear();
            mAdapter.clear();

            String[] listItems = dataStr.split(DATA_DELIMITER);

            for(int i=0;i<listItems.length;i++){
                shoppingListItems.add(listItems[i]);
            }

            for(int i=0;i<shoppingListItems.size();i++){
                mAdapter.add(shoppingListItems.get(i));
                mAdapter.notifyDataSetChanged();
            }

        }catch (Exception e){
            Log.e(MainActivity.DEBUG_DEFAULT_TAG, "Exception at Restful AddItemsToShoppingList" + e.getMessage());
        }
    }

    public void clearDataFromList(){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                // All your networking logic should be here
                try{
                    // Create URL
                    URL appEndPoint = new URL(SERVER_URL + DELETE_DATA_ENDPOINT + "?userName=" + username);

                    // Create connection
                    HttpURLConnection myConnection =
                            (HttpURLConnection) appEndPoint.openConnection();


                    if (myConnection.getResponseCode() == 200) {
                        // Success
                        Log.e(MainActivity.DEBUG_DEFAULT_TAG, myConnection.getResponseMessage());

                    } else { //error has occured
                        Log.e(MainActivity.DEBUG_DEFAULT_TAG, "response code was not 200, try again later");
                    }


                }catch(Exception e){
                    Log.e(MainActivity.DEBUG_DEFAULT_TAG, "clearDataFromList error occured");
                }
            }
        });
    }


    public static String ConvertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

}
