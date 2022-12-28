package com.example.bybloscar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RequestActivity extends AppCompatActivity {
    private DBHelper DB;

    private static User currentEmployee = new User();
    private static User currentCustomer = new User();


    private Button approveButton;
    private Button rejectButton;
    private Button branchProfileButton;

    private LinearLayout allRequestsScroll;
    private LinearLayout approvedRequestsScroll;
    private LinearLayout requestDetailsScroll;

    private EditText requestField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        DB = new DBHelper(this);
        DB.getUserData();
        DB.getServiceData();

        requestDetailsScroll = findViewById(R.id.request_details_scroll);
        approveButton = findViewById(R.id.searchByWorkHoursButton);
        rejectButton = findViewById(R.id.searchByAddressButton);
        allRequestsScroll = findViewById(R.id.allRequestsScroll);
        approvedRequestsScroll = findViewById(R.id.approvedRequestsScroll);
        branchProfileButton = findViewById(R.id.searchByServiceButton);

        refreshPage();

        approveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int size = allRequestsScroll.getChildCount();
                for (int i = 0; i < size; i++) {
                    View v = allRequestsScroll.getChildAt(i);
                    if (((CheckBox) v).isChecked()) {
                        currentCustomer = User.getUserObject(((CheckBox) v).getText().toString().split(":")[0]);
                        currentEmployee.getApprovedRequestList().add(((CheckBox) v).getText().toString());
                        currentEmployee.getAllRequestList().remove(((CheckBox) v).getText().toString());
                        currentCustomer.getCustomerApprovedRequestsList().add(((CheckBox) v).getText().toString().split(":")[1]+":"+currentEmployee.getUsername());
                        currentCustomer.getCustomerPendingRequestsList().remove(((CheckBox) v).getText().toString());


                    }
                }
                DB.deleteAllUserData();
                DB.deleteAllServiceData();
                DB.insertServiceData();
                DB.insertUserData();
                refreshPage();

            }
        });

        branchProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                openEmployeeActivity();

            }
        });


        rejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    int size = allRequestsScroll.getChildCount();
                    for (int i = 0; i < size; i++) {
                        View v = allRequestsScroll.getChildAt(i);
                        if (((CheckBox) v).isChecked()) {
                            currentEmployee.getAllRequestList().remove(((CheckBox) v).getText().toString());
                            currentCustomer.getCustomerPendingRequestsList().remove(((CheckBox) v).getText().toString());

                        }
                    }
                DB.deleteAllUserData();
                DB.deleteAllServiceData();
                DB.insertServiceData();
                DB.insertUserData();
                refreshPage();
            }
        });



    }

    public void refreshPage(){

        allRequestsScroll.removeAllViewsInLayout();
        approvedRequestsScroll.removeAllViewsInLayout();

        for (String requestName : currentEmployee.getAllRequestList()){
            if(currentEmployee.getServiceObject(requestName.split(":")[1]) == null){
                continue;
            }
            CheckBox cb = new CheckBox(this);
            cb.setText(requestName);
            cb.setId(requestName.hashCode());
            cb.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            cb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loadRequestDetails(cb.getText().toString());
                }
            });
            allRequestsScroll.addView(cb);
        }

        for (String  requestName : currentEmployee.getApprovedRequestList()){
            TextView text = new TextView(this);
            text.setText(requestName);
            text.setId(requestName.hashCode());
            text.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            approvedRequestsScroll.addView(text);
        }
    }

    public void openEmployeeActivity(){

        Intent intent = new Intent(this, EmployeeActivity.class);
        startActivity(intent);

    }

    public void loadRequestDetails(String requestName){
        User user = User.getUserObject(requestName.split(":")[0]);
        requestDetailsScroll.removeAllViewsInLayout();
        TextView name = new TextView(this);
        name.setText("User: "+user.getUsername());
        name.setId(user.getUsername().hashCode());
        name.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        requestDetailsScroll.addView(name);

        for (Document document : user.getDocuments()){
            if(document.getServices().contains(Service.getServiceObject(requestName.split(":")[1]))){
                TextView text = new TextView(this);
                text.setText(document.getType()+": "+document.getContent());
                text.setId(document.getType().hashCode());
                text.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.FILL_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                requestDetailsScroll.addView(text);
            }
        }
    }

    public static void setCurrentEmployee(User user) {
        currentEmployee = user;
    }

    public static void setCurrentCustomer(User user){
        currentCustomer = user;
    }



}