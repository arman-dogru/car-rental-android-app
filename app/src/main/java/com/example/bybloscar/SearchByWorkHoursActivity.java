package com.example.bybloscar;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

public class SearchByWorkHoursActivity extends AppCompatActivity {
    private DBHelper DB;
    static User currentCustomer = new User();

    TimePickerDialog timePickerDialog;
    Button searchButton;
    EditText timeField;
    String time;
    int currentDay;

    RadioButton mon;
    RadioButton tue;
    RadioButton wed;
    RadioButton thu;
    RadioButton fri;
    RadioButton sat;
    RadioButton sun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_work_hours);
        DB = new DBHelper(this);
        DB.getUserData();
        DB.getServiceData();

        mon = findViewById(R.id.mon);
        tue = findViewById(R.id.tue);
        wed = findViewById(R.id.wed);
        thu = findViewById(R.id.thu);
        fri = findViewById(R.id.fri);
        sat = findViewById(R.id.sat);
        sun = findViewById(R.id.sun);

        searchButton = findViewById(R.id.searchButton);

        timeField = findViewById(R.id.timeField);

        timeField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog = new TimePickerDialog(SearchByWorkHoursActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        timeField.setText(hourOfDay + ":" + minute);
                    }
                },0,0,false);

                timePickerDialog.show();
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!timeField.getText().toString().contains(":")){
                    return;
                }

                if(mon.isChecked()){
                    currentDay = 0;
                }

                if(tue.isChecked()){
                    currentDay = 2;
                }

                if(wed.isChecked()){
                    currentDay = 4;
                }

                if(thu.isChecked()){
                    currentDay = 6;
                }

                if(fri.isChecked()){
                    currentDay = 8;
                }

                if(sat.isChecked()){
                    currentDay = 10;
                }

                if(sun.isChecked()){
                    currentDay = 12;
                }

                time = timeField.getText().toString();

                SearchResultsActivity.setDay(currentDay);

                SearchResultsActivity.setSearchType("WorkHours");

                SearchResultsActivity.setTime(time);

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
