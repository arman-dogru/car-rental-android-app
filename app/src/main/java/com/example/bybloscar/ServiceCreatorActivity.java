package com.example.bybloscar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ServiceCreatorActivity extends AppCompatActivity {
    private DBHelper DB;

    static boolean editMode = false;
    static Service editableService;

    private Button backButton;
    private Button saveServiceButton;

    private EditText serviceNameField;
    private EditText servicePriceField;

    private LinearLayout documentsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_creator);
        DB = new DBHelper(this);
        DB.getUserData();
        DB.getServiceData();

        documentsLayout = findViewById(R.id.documents_scroll);

        serviceNameField = findViewById(R.id.new_service_name_field);
        servicePriceField = findViewById(R.id.new_service_price_field);

        if(editMode){
            serviceNameField.setText(editableService.getName());
            servicePriceField.setText(Double.toString(editableService.getPrice()));
            getDocuments(editableService);
        }

        saveServiceButton = findViewById(R.id.save_service_button);
        saveServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(servicePriceField.getText().toString().trim().equals("")|| serviceNameField.getText().toString().trim().equals("")){

                    Toast.makeText(ServiceCreatorActivity.this, "Please enter the name and hourly rate of the service to proceed", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(Service.hasService(serviceNameField.getText().toString())){
                    Toast.makeText(ServiceCreatorActivity.this, "Cannot create duplicate services", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(serviceNameField.getText().toString().matches(".*\\d.*")){

                    Toast.makeText(ServiceCreatorActivity.this, "Service name cannot contain numeric characters", Toast.LENGTH_SHORT).show();
                    return;

                }

                if(!serviceNameField.getText().toString().matches("[a-zA-Z0-9 ]*")){

                    Toast.makeText(ServiceCreatorActivity.this, "Service name cannot contain any special characters", Toast.LENGTH_SHORT).show();
                    return;

                }

                if(!checkedAtLeastOneDocument()){

                    Toast.makeText(ServiceCreatorActivity.this, "Please select at least one document to proceed", Toast.LENGTH_SHORT).show();
                    return;

                }

                if(servicePriceField.getText().toString().length() > 18){
                    Toast.makeText(ServiceCreatorActivity.this, "Please enter a character number between 0-18", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(Long.parseLong(servicePriceField.getText().toString()) == 0){

                    Toast.makeText(ServiceCreatorActivity.this, "We don't offer services for free here!", Toast.LENGTH_SHORT).show();
                    return;

                }


                double servicePrice = Double.parseDouble(servicePriceField.getText().toString().trim());
                String serviceName = serviceNameField.getText().toString().trim();

                if (!editMode) {
                    Service service = new Service(serviceName, servicePrice);
                    Service.addService(service);
                    saveDocuments(service);
                }
                else {
                    editableService.setName(serviceName);
                    editableService.setPrice(servicePrice);
                    saveDocuments(editableService);
                }
                backToAdminActivity();
            }
        });

        backButton = findViewById(R.id.back_to_admin_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToAdminActivity();
            }
        });
    }

    public void backToAdminActivity(){
        DB.deleteAllServiceData();
        DB.insertServiceData();
        DB.deleteAllUserData();
        DB.insertUserData();
        Intent intent = new Intent(this, AdminActivity.class);
        startActivity(intent);
    }

    public static void editModeActivity(Service service){
        ServiceCreatorActivity.editMode = true;
        ServiceCreatorActivity.editableService = service;
    }

    public void saveDocuments(Service service){
        DB.deleteAllServiceData();
        DB.insertServiceData();
        DB.deleteAllUserData();
        DB.insertUserData();
        service.clearDocuments();
        int size = documentsLayout.getChildCount();
        for (int i = 0; i < size; i++) {
            View v = documentsLayout.getChildAt(i);
            if (((CheckBox) v).isChecked()) {
                service.addDocument(((CheckBox) v).getText().toString());
            }
        }
    }

    public void getDocuments(Service service){
        DB.deleteAllServiceData();
        DB.insertServiceData();
        DB.deleteAllUserData();
        DB.insertUserData();
        for (String document : service.getRequiredDocuments()){
            int size = documentsLayout.getChildCount();
            for (int i = 0; i < size; i++) {
                View v = documentsLayout.getChildAt(i);
                if (((CheckBox) v).getText().toString().equals(document)) {
                    ((CheckBox) v).setChecked(true);
                }
            }
        }
    }

    public static void createModeActivity(){
        ServiceCreatorActivity.editMode = false;
    }

    public boolean checkedAtLeastOneDocument() {

        int size = documentsLayout.getChildCount();
        for (int i = 0; i < size; i++) {
            View v = documentsLayout.getChildAt(i);
            if (((CheckBox) v).isChecked()) {
                return true;
            }

        }
        return false;
    }
}