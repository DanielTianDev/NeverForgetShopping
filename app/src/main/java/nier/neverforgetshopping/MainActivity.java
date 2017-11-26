package nier.neverforgetshopping;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {


    public final static String DEBUG_DEFAULT_TAG = "NFSDebugInfo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }



    public void LaunchShoppingList(View view){
        try{
            Intent intent = new Intent(this, ShoppingListActivity.class);
            startActivity(intent);
        }catch(Exception e){
            Log.e(DEBUG_DEFAULT_TAG, "LaunchShoppingList failed: " + e.getMessage());
        }
    }

}
