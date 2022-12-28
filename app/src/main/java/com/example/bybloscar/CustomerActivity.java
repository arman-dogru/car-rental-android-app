package com.example.bybloscar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;


public class CustomerActivity extends AppCompatActivity {
    private DBHelper DB;

    static User currentCustomer = new User();

    Button searchByServiceButton;
    Button searchByWorkHoursButton;
    Button searchByAddressButton;
    Button logoffButton;
    Button profileButton;

    private LinearLayout allRequestsScroll;
    private LinearLayout approvedRequestsScroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DB = new DBHelper(this);
        DB.getUserData();
        DB.getServiceData();

        setContentView(R.layout.activity_customer);
        logoffButton = findViewById(R.id.logoffButton2);
        searchByServiceButton = findViewById(R.id.searchByServiceButton);
        searchByWorkHoursButton = findViewById(R.id.searchByWorkHoursButton);
        searchByAddressButton = findViewById(R.id.searchByAddressButton);
        allRequestsScroll = findViewById(R.id.allRequestsScroll);
        approvedRequestsScroll = findViewById(R.id.approvedRequestsScroll);
        profileButton = findViewById(R.id.profile_button);

        refreshPage();

        logoffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLoginActivity();
            }
        });

        searchByServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchByServiceActivity.setCurrentCustomer(currentCustomer);

                openSearchByServiceActivity();
            }
        });

        searchByWorkHoursButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchByWorkHoursActivity.setCurrentCustomer(currentCustomer);

                openSearchByWorkHoursActivity();

            }
        });

        searchByAddressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchByAddressActivity.setCurrentCustomer(currentCustomer);

                openSearchByAddressActivity();

            }
        });

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openUserInfoActivity(currentCustomer);
            }
        });


    }
    public  void openSearchByAddressActivity(){
        Intent intent = new Intent(this, SearchByAddressActivity.class);
        startActivity(intent);
    }

    public  void openSearchByWorkHoursActivity(){
        Intent intent = new Intent(this, SearchByWorkHoursActivity.class);
        startActivity(intent);
    }
    public  void openSearchByServiceActivity(){
        Intent intent = new Intent(this, SearchByServiceActivity.class);
        startActivity(intent);
    }
    public  void openLoginActivity(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void openUserInfoActivity(User currentUser){
        Intent intent = new Intent(this, UserInfoActivity.class);
        startActivity(intent);
        UserInfoActivity.setCurrentUser(currentUser);
    }

    public void refreshPage(){

        approvedRequestsScroll.removeAllViewsInLayout();
        allRequestsScroll.removeAllViewsInLayout();

        for (String request : currentCustomer.getCustomerApprovedRequestsList()){
            TextView textView = new TextView(this);
            textView.setText(request);
            textView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            approvedRequestsScroll.addView(textView);
        }

        for (String request : currentCustomer.getCustomerPendingRequestsList()){
            TextView textView = new TextView(this);
            textView.setText(request);
            textView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            allRequestsScroll.addView(textView);
        }

    }

    public static void setCurrentCustomer(User user){currentCustomer=user;}

    public void openRequestDocumentsActivity(User user, String request){
        Intent intent = new Intent(this, RequestDocumentsActivity.class);
        RequestDocumentsActivity.setCurrentCustomer(user);
        RequestDocumentsActivity.setCurrentRequest(request);
        startActivity(intent);
    }
}