package nier.neverforgetshopping;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import RestfulServices.RestfulController;


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


        SetViewItems();

    }


    void SetViewItems(){
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
        restController.retrieveDataFromServer();


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

    }


    public void ClearShoppingListNetworked(View view){

        try{
            restController.clearDataFromList();
            view.invalidate();
        }catch(Exception e){
            Log.e(MainActivity.DEBUG_DEFAULT_TAG, "ClearShoppingListNetworked exception");
        }

    }



}
