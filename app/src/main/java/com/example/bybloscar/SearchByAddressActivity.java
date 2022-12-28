package com.example.bybloscar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SearchByAddressActivity extends AppCompatActivity {
    private DBHelper DB;
    static User currentCustomer = new User();

    Button searchButton;
    static EditText cityField;
    String city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_address);
        DB = new DBHelper(this);
        DB.getUserData();
        DB.getServiceData();

        cityField = findViewById(R.id.field_adress);
        searchButton = findViewById(R.id.searchButton);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                city = cityField.getText().toString();

                SearchResultsActivity.setSearchType("Address");

                SearchResultsActivity.setCity(city);

                SearchResultsActivity.setCurrentCustomer(currentCustomer);

                openSearchResultsActivity();
            }
        });

    }

    public void openSearchResultsActivity(){

        DB.deleteAllServiceData();
        DB.insertServiceData();
        DB.deleteAllUserData();
        DB.insertUserData();
        Intent intent = new Intent(this, SearchResultsActivity.class);
        startActivity(intent);

    }
    public static void setCurrentCustomer(User user){currentCustomer=user;}

}