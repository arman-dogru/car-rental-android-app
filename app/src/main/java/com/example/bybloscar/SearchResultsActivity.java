package com.example.bybloscar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.ArrayList;

public class SearchResultsActivity extends AppCompatActivity {
    private DBHelper DB;

    static User currentCustomer = new User();

    static String searchType;
    ListView results;
    static String city;
    static String time;
    static String service;
    static int day;
    Button backButton;

    ArrayList<String> searchByAddressResults = new ArrayList<>();
    ArrayList<String> searchByWorkHoursResults = new ArrayList<>();
    ArrayList<String> searchByServiceResults = new ArrayList<>();

    static ArrayList<User> searchResultObjects =  new ArrayList<>();


    public static void setDay(int day) {
        SearchResultsActivity.day = day;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        DB = new DBHelper(this);
        DB.getUserData();
        DB.getServiceData();

        searchResultObjects.clear();

        refreshPage(searchType);

        results = findViewById(R.id.results);
        backButton = findViewById(R.id.backButton2);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCustomerActivity();
            }
        });


        if(searchType.equals("Address")){

            if(searchByAddressResults.isEmpty()){
                Toast.makeText(SearchResultsActivity.this, "There are no Byblos branches in this city", Toast.LENGTH_SHORT).show();
                return;
            }

            ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, searchByAddressResults);

            results.setAdapter(arrayAdapter);

        }


        else if(searchType.equals("Service")){

            if(searchByServiceResults.isEmpty()){
                Toast.makeText(SearchResultsActivity.this, "No Byblos branch offers this type of service", Toast.LENGTH_SHORT).show();
                return;
            }

            ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, searchByServiceResults);

            results.setAdapter(arrayAdapter);

        }


        else if(searchType.equals("WorkHours")){

            if(searchByWorkHoursResults.isEmpty()){
                Toast.makeText(SearchResultsActivity.this, "No Byblos branch is open at this day and time", Toast.LENGTH_SHORT).show();
                return;
            }

            ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, searchByWorkHoursResults);

            results.setAdapter(arrayAdapter);

        }

        // 3 durum icin de ayni

        results.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                BranchProfileActivity.setCurrentBranch(searchResultObjects.get(position));
                BranchProfileActivity.setSearchType(searchType);
                BranchProfileActivity.setCurrentCustomer(currentCustomer);
                openBranchProfileActivity();
            }
        });




    }



    public void refreshPage(String searchType){

        if(searchType=="Address"){

            for(User user : User.getUserList()){

                if(user.getCity()!=null){

                    if(user.getCity().equals(city)) {
                        searchByAddressResults.add(user.getFullAddress());
                        searchResultObjects.add(user);
                    }

                }
            }
        }

        if(searchType=="Service"){

            for(User user : User.getUserList()) {

                    for (Service s : user.getEmployeeServiceList())

                        if (s.getName().equals(service)) {
                            searchByServiceResults.add(user.getFullAddress());
                            searchResultObjects.add(user);
                        }

            }
        }

        if(searchType=="WorkHours"){
            this.searchResultObjects = User.searchByWorkingHours(time,day);
            for (User user : searchResultObjects){
                searchByWorkHoursResults.add(user.getFullAddress());
            }
            Log.d("XXXXXXXXXXXXXXXXXXXXX", "list size is: "+searchResultObjects.size());
        }
    }

    public static void setSearchType(String str){

        SearchResultsActivity.searchType = str;
    }

    public static void setCity(String str){

        city=str;
    }

    public static void setTime(String str){

        time=str;
    }

    public static void setService(String str){

        service=str;
    }

    public void openBranchProfileActivity(){

        Intent intent = new Intent(this, BranchProfileActivity.class);
        startActivity(intent);
    }

    public void openCustomerActivity(){
        Intent intent = new Intent(this, CustomerActivity.class);
        startActivity(intent);

    }

    public static void setCurrentCustomer(User user){currentCustomer=user;}




}