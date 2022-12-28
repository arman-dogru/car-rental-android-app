package com.example.bybloscar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class BranchProfileActivity extends AppCompatActivity {
    private DBHelper DB;

    static User currentBranch = new User();
    static User currentCustomer = new User();


    Button feedbackButton;
    Button backButton;
    Button requestButton;
    static String searchType;
    TextView ratingText;
    TextView phone;
    TextView fullAddress;
    EditText requestField;

    LinearLayout branchServicesScroll;
    LinearLayout workingHoursScroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DB = new DBHelper(this);
        DB.getUserData();
        DB.getServiceData();

        setContentView(R.layout.activity_branch_profile);
        requestField = findViewById(R.id.requestField);
        backButton = findViewById(R.id.backButton);
        feedbackButton = findViewById(R.id.feedbackButton);
        requestButton = findViewById(R.id.requestButton);
        ratingText = findViewById(R.id.ratingText);
        branchServicesScroll = findViewById(R.id.branchServices);
        workingHoursScroll= findViewById(R.id.workingHours);
        phone = findViewById(R.id.phone);
        fullAddress = findViewById(R.id.fullAddress);

        refreshPage();


        feedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FeedbackActivity.setCurrentBranch(currentBranch);

                openFeedbackActivity();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchResultsActivity.setSearchType(searchType);
                SearchResultsActivity.setSearchType(searchType);
                openSearchResultsActivity();
            }
        });

        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String requestName = requestField.getText().toString();

                if(requestName.equals("")) {
                    return;
                }

                if(!currentBranch.getEmployeeServiceList().contains(Service.getServiceObject(requestName))){
                    Toast.makeText(BranchProfileActivity.this, "You can only request services that are offered by this branch", Toast.LENGTH_SHORT).show();
                    return;
                }

                openRequestDocumentsActivity(currentCustomer,requestName);
            }

        });


    }

    public void openRequestDocumentsActivity(User user, String request){
        Intent intent = new Intent(this, RequestDocumentsActivity.class);
        RequestDocumentsActivity.setCurrentCustomer(user);
        RequestDocumentsActivity.setCurrentRequest(request);
        startActivity(intent);
    }

    public void openFeedbackActivity(){

        Intent intent = new Intent(this, FeedbackActivity.class);
        startActivity(intent);
    }

    public void openCustomerActivity(){
        Intent intent = new Intent(this, CustomerActivity.class);
        startActivity(intent);
    }

    public void openSearchResultsActivity(){

        Intent intent = new Intent(this, SearchResultsActivity.class);
        startActivity(intent);
    }

    public static void setCurrentBranch(User user){

        currentBranch = user;
    }

    public void refreshPage(){

        branchServicesScroll.removeAllViewsInLayout();

        phone.setText("Branch Phone Number: "+currentBranch.getPhone());
        fullAddress.setText("Branch Address: "+currentBranch.getFullAddress());
        ratingText.setText("Current rating: " + currentBranch.getRating()+"/5");

        TextView mon = new TextView(this);
        mon.setText("Monday: "+currentBranch.getEmployeeWorkingHours().get(0)+" - "+currentBranch.getEmployeeWorkingHours().get(1));
        TextView tue = new TextView(this);
        tue.setText("Tuesday: "+currentBranch.getEmployeeWorkingHours().get(2)+" - "+currentBranch.getEmployeeWorkingHours().get(3));
        TextView wed = new TextView(this);
        wed.setText("Wednesday: "+currentBranch.getEmployeeWorkingHours().get(4)+" - "+currentBranch.getEmployeeWorkingHours().get(5));
        TextView thu = new TextView(this);
        thu.setText("Thursday: "+currentBranch.getEmployeeWorkingHours().get(6)+" - "+currentBranch.getEmployeeWorkingHours().get(7));
        TextView fri = new TextView(this);
        fri.setText("Friday: "+currentBranch.getEmployeeWorkingHours().get(8)+" - "+currentBranch.getEmployeeWorkingHours().get(9));
        TextView sat = new TextView(this);
        sat.setText("Saturday: "+currentBranch.getEmployeeWorkingHours().get(10)+" - "+currentBranch.getEmployeeWorkingHours().get(11));
        TextView sun = new TextView(this);
        sun.setText("Sunday: "+currentBranch.getEmployeeWorkingHours().get(12)+" - "+currentBranch.getEmployeeWorkingHours().get(13));

        workingHoursScroll.addView(mon);
        workingHoursScroll.addView(tue);
        workingHoursScroll.addView(wed);
        workingHoursScroll.addView(thu);
        workingHoursScroll.addView(fri);
        workingHoursScroll.addView(sat);
        workingHoursScroll.addView(sun);

        for (Service  service : currentBranch.getEmployeeServiceList()){
            TextView text = new TextView(this);
            text.setText(service.getName());
            //text.setId(requestName.hashCode());
            text.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            branchServicesScroll.addView(text);
        }

    }

    public static void setSearchType(String str){searchType=str;}

    public static void setCurrentCustomer(User user){currentCustomer=user;}

    public static void saveDocuments(String requestName) {

        currentBranch.getAllRequestList().add(currentCustomer.getUsername()+":"+requestName);
        currentBranch.getRequestingUsers().add(currentCustomer);

        currentCustomer.getCustomerPendingRequestsList().add(requestName+":"+currentBranch.getUsername());
        RequestActivity.setCurrentEmployee(currentBranch);
        RequestActivity.setCurrentCustomer(currentCustomer);
    }





}