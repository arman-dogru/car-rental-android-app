package com.example.bybloscar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {
    private DBHelper DB;

    public Button addServiceButton;
    public Button accountsButton;
    public EditText serviceNameField;
    private LinearLayout servicesScroll;
    private Button removeServiceButton;
    private Button editServiceButton;
    private Button logoffButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        DB = new DBHelper(this);
        DB.getUserData();
        DB.getServiceData();

        servicesScroll = findViewById(R.id.services_scroll);
        refreshPage();

        editServiceButton = findViewById(R.id.edit_service_button);
        addServiceButton = findViewById(R.id.open_services_button);
        accountsButton = findViewById(R.id.open_accounts_button);
        logoffButton = findViewById(R.id.logoffButton);

        logoffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLoginActivity();
            }
        });

        removeServiceButton = findViewById(R.id.remove_service_button);
        removeServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serviceNameField = findViewById(R.id.service_name_field);
                String serviceName = serviceNameField.getText().toString().trim();

                if(TextUtils.isEmpty(serviceName)){
                    return;
                }


                if(!Service.hasService(serviceName)){

                    Toast.makeText(AdminActivity.this, "That service doesn't exist", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(Service.getServiceList().size()==3) {

                    Toast.makeText(AdminActivity.this, "Sorry, you can't go below 3 services", Toast.LENGTH_SHORT).show();
                    return;
                }

                Service.removeService(serviceName);
                DB.deleteAllUserData();
                DB.deleteAllServiceData();
                DB.insertServiceData();
                DB.insertUserData();
                refreshPage();
            }
        });

        editServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                serviceNameField = findViewById(R.id.service_name_field);
                String serviceName = serviceNameField.getText().toString().trim();

                if(TextUtils.isEmpty(serviceName)){
                    return;
                }

                if(!Service.hasService(serviceName)){

                    Toast.makeText(AdminActivity.this, "That service doesn't exist", Toast.LENGTH_SHORT).show();
                    return;
                }



                openServiceCreatorActivity();

                ServiceCreatorActivity.editModeActivity(Service.getServiceObject(serviceName));
                DB.deleteAllUserData();
                DB.deleteAllServiceData();
                DB.insertServiceData();
                DB.insertUserData();
            }
        });

        accountsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAccountManager();
            }
        });

        addServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ServiceCreatorActivity.createModeActivity();
                openServiceCreatorActivity();
            }
        });
    }

    public void refreshPage(){
        servicesScroll.removeAllViewsInLayout();

        for (Service service : Service.getServiceList()) {
            TextView userText = new TextView(this);
            userText.setText(service.getName());
            userText.setId(service.getName().hashCode());
            userText.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            servicesScroll.addView(userText);
        }
    }

    public void openServiceCreatorActivity(){
        Intent intent = new Intent(this, ServiceCreatorActivity.class);
        startActivity(intent);
    }

    public void openAccountManager(){
        Intent intent = new Intent(this, AccountManagerActivity.class);
        startActivity(intent);
    }

    public void openLoginActivity(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}