package com.example.bybloscar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SearchByServiceActivity extends AppCompatActivity {
    private DBHelper DB;
    static User currentCustomer = new User();

    Button searchButton;
    String service;
    EditText serviceField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_service);
        DB = new DBHelper(this);
        DB.getUserData();
        DB.getServiceData();

        serviceField = findViewById(R.id.field_service_name);
        searchButton = findViewById(R.id.searchButton);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(serviceField.getText())){

                    return;

                }

                if(serviceField.getText().toString().matches(".*\\d.*")){

                    Toast.makeText(SearchByServiceActivity.this, "Service name cannot contain numeric characters", Toast.LENGTH_SHORT).show();
                    return;

                }

                if(!serviceField.getText().toString().matches("[a-zA-Z0-9 ]*")){

                    Toast.makeText(SearchByServiceActivity.this, "Service name cannot contain any special characters", Toast.LENGTH_SHORT).show();
                    return;

                }

                service = serviceField.getText().toString();

                SearchResultsActivity.setSearchType("Service");

                SearchResultsActivity.setService(service);

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