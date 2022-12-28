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

public class EmployeeActivity extends AppCompatActivity {
    private DBHelper DB;
    private static User currentEmployee;


    private Button logoffButton;
    private Button removeServiceButton;
    private Button addServiceButton;
    private Button requestsButton;
    private Button saveProfileButton;
    private Button branchAddressButton;
    private Button workingHoursButton;

    private EditText phoneField;
    private EditText serviceNameField;

    private LinearLayout allServicesScroll;
    private LinearLayout myServicesScroll;

    private Button profileButton;

    public static void setCurrentEmployee(User currentEmployee) {
        EmployeeActivity.currentEmployee = currentEmployee;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);
        DB = new DBHelper(this);
        DB.getUserData();
        DB.getServiceData();

        profileButton = findViewById(R.id.profile_button);
        phoneField = findViewById(R.id.phone_field);
        serviceNameField = findViewById(R.id.service_name_field);
        allServicesScroll = findViewById(R.id.allServices_scroll);
        myServicesScroll = findViewById(R.id.myServices_scroll);

        refreshPage();

        logoffButton = findViewById(R.id.back_to_login_button);
        logoffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLoginActivity();
            }
        });

        removeServiceButton = findViewById(R.id.remove_service_button);
        removeServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String serviceName = serviceNameField.getText().toString();
                if(currentEmployee.getEmployeeServiceList().contains(Service.getServiceObject(serviceName))) {
                    currentEmployee.removeService(serviceName);
                    serviceNameField.setText("");
                    currentEmployee.getAllRequestList().remove(serviceName);
                    DB.deleteAllUserData();
                    DB.deleteAllServiceData();
                    DB.insertServiceData();
                    DB.insertUserData();
                }
                refreshPage();
            }
        });

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openUserInfoActivity(currentEmployee);
            }
        });

        addServiceButton = findViewById(R.id.add_service_button);
        addServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(currentEmployee.getCity()==null){
                    Toast.makeText(EmployeeActivity.this, "Please first enter the branch address to proceed",
                            Toast.LENGTH_SHORT).show();

                    return;

                }

                String serviceName = serviceNameField.getText().toString();


                if(TextUtils.isEmpty(serviceName)){
                    return;
                }

                if(!Service.hasService(serviceName)){
                    Toast.makeText(EmployeeActivity.this, "You can only create services  that are in 'All Services'",
                            Toast.LENGTH_SHORT).show();

                    return;
                }

                if(!currentEmployee.getEmployeeServiceList().contains(Service.getServiceObject(serviceName)))
                {
                    currentEmployee.addService(Service.getServiceObject(serviceName));
                }
                DB.deleteAllUserData();
                DB.deleteAllServiceData();
                DB.insertServiceData();
                DB.insertUserData();
                refreshPage();
            }
        });

        requestsButton = findViewById(R.id.requests_button);
        requestsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    openRequestActivity();

            }
        });

        saveProfileButton = findViewById(R.id.save_profile_button);
        saveProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!isValidMobileNo(phoneField.getText().toString().trim())) {

                    Toast.makeText(EmployeeActivity.this, "Please use XXX-XXX-XXXX format in your phone number", Toast.LENGTH_SHORT).show();

                    return ;
                }

                if(currentEmployee.getCity()==null) {

                    Toast.makeText(EmployeeActivity.this, "To create a profile, provide an address and a valid phone number first", Toast.LENGTH_SHORT).show();

                    return ;
                }

                Toast.makeText(EmployeeActivity.this, "Profile Successfully Saved!", Toast.LENGTH_SHORT).show();

                currentEmployee.setPhone(phoneField.getText().toString().trim());

                DB.deleteAllUserData();
                DB.deleteAllServiceData();
                DB.insertServiceData();
                DB.insertUserData();
                refreshPage();
            }
        });

        branchAddressButton = findViewById(R.id.branchAddressButton);

        branchAddressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openBranchAddressActivity();
            }
        });

        workingHoursButton = findViewById(R.id.workingHoursButton);

        workingHoursButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(currentEmployee.getCity()==null){

                    Toast.makeText(EmployeeActivity.this, "Please set the branch address first", Toast.LENGTH_SHORT).show();
                    return;

                }


                openWorkingHoursActivity();
            }
        });

    }



    public void refreshPage(){
        phoneField.setText(currentEmployee.getPhone());
        myServicesScroll.removeAllViewsInLayout();
        allServicesScroll.removeAllViewsInLayout();

        for (Service service : Service.getServiceList()){
            TextView textView = new TextView(this);
            textView.setText(service.getName());
            textView.setId(service.getName().hashCode());
            textView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            allServicesScroll.addView(textView);
        }

        for (Service service : currentEmployee.getEmployeeServiceList()){
            TextView textView = new TextView(this);
            textView.setText(service.getName());
            textView.setId(service.getName().hashCode());
            textView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            myServicesScroll.addView(textView);
        }
    }

    public void openLoginActivity(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void openRequestActivity(){
        Intent intent = new Intent(this, RequestActivity.class);
        startActivity(intent);
        RequestActivity.setCurrentEmployee(currentEmployee);
    }

    public void openBranchAddressActivity(){

        Intent intent = new Intent(this, BranchAddressActivity.class);
        startActivity(intent);


    }

    public void openWorkingHoursActivity(){
        WorkingHoursActivity.setCurrentEmployee(currentEmployee);
        Intent intent = new Intent(this, WorkingHoursActivity.class);
        startActivity(intent);
    }

    public static void setAddress(String streetName,String zipCode,String cityName,String buildingNo,String regionName){

        currentEmployee.setAddress(streetName, zipCode , cityName , buildingNo , regionName);
    }


    public static User getCurrentEmployee(){

        return currentEmployee;

    }

    public static boolean isValidMobileNo(String str)
    { //https://stackoverflow.com/questions/27631692/phone-number-validation-in-java
        String regex = "\\d{3}-\\d{3}-\\d{4}"; // XXX-XXX-XXXX
        return str.matches(regex);

    }

    public void openUserInfoActivity(User currentUser){
        Intent intent = new Intent(this, UserInfoActivity.class);
        startActivity(intent);
        UserInfoActivity.setCurrentUser(currentUser);
    }

}