package com.example.bybloscar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class BranchAddressActivity extends AppCompatActivity {
    private DBHelper DB;

    private static EditText streetNameField;
    private static EditText cityField;
    private static EditText zipCodeField;
    private static EditText regionField;
    private static EditText buildingNumberField;
    private Button saveAddressButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch_address);
        DB = new DBHelper(this);
        DB.getUserData();
        DB.getServiceData();



        streetNameField = findViewById(R.id.street_name_field);
        cityField = findViewById(R.id.city_field);
        zipCodeField = findViewById(R.id.postal_code_field);
        regionField= findViewById(R.id.region_field);
        buildingNumberField= findViewById(R.id.building_number_field);
        saveAddressButton =  findViewById(R.id.save_address_button);

        User currentEmployee = EmployeeActivity.getCurrentEmployee();

        if(currentEmployee.getCity()!=null){

            streetNameField.setText(currentEmployee.getStreetName());
            cityField.setText(currentEmployee.getCity());
            zipCodeField.setText(currentEmployee.getZipCode());
            regionField.setText(currentEmployee.getRegion());
            buildingNumberField.setText(currentEmployee.getBuildingNumber());

        }


        saveAddressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String streetName = streetNameField.getText().toString();
                String zipCode = zipCodeField.getText().toString();
                String city = cityField.getText().toString();
                String buildingNumber = buildingNumberField.getText().toString();
                String region = regionField.getText().toString();


                if (streetName.length()==0 || zipCode.length()==0 || city.length()==0 || buildingNumber.length()==0 ||
                        region.length()==0){

                    Toast.makeText(BranchAddressActivity.this, "All of the fields are required", Toast.LENGTH_SHORT).show();
                    return;
                }

                else if(!isZipCodeValid(zipCode)){

                    Toast.makeText(BranchAddressActivity.this, "Postal code format should be A1A 1A1", Toast.LENGTH_SHORT).show();
                    return;

                }

                else {

                    Toast.makeText(BranchAddressActivity.this, "Address Saved", Toast.LENGTH_SHORT).show();

                    openEmployeeActivity();

                    EmployeeActivity.setAddress(streetName, zipCode, city, buildingNumber , region);

                    User.fullAddressList.add(currentEmployee.getFullAddress());

                    DB.deleteAllUserData();
                    DB.deleteAllServiceData();
                    DB.insertServiceData();
                    DB.insertUserData();
                }
            }
        });




    }

    public void openEmployeeActivity(){

        Intent intent = new Intent(this, EmployeeActivity.class);
        startActivity(intent);

    }


    public boolean isZipCodeValid(String zip){

        if(zip.length() !=7){

            return false;

        }

        if(zip.charAt(3)!=' '){

            return false;
        }

        if(!Character.isDigit(zip.charAt(1)) || !Character.isDigit(zip.charAt(4)) || !Character.isDigit(zip.charAt(6)) ){

            return false;
        }

        if(Character.isDigit(zip.charAt(0)) || Character.isDigit(zip.charAt(2)) || Character.isDigit(zip.charAt(5)) ){

            return false;
        }

        return true;


    }



}