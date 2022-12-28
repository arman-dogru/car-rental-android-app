package com.example.bybloscar;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;


public class WorkingHoursActivity extends AppCompatActivity {
    private DBHelper DB;

    private static User currentEmployee;

    EditText mondayBegin;
    EditText mondayEnd;
    EditText tuesdayBegin;
    EditText tuesdayEnd;
    EditText wednesdayBegin;
    EditText wednesdayEnd;
    EditText thursdayBegin;
    EditText thursdayEnd;
    EditText fridayBegin;
    EditText fridayEnd;
    EditText saturdayBegin;
    EditText saturdayEnd;
    EditText sundayBegin;
    EditText sundayEnd;

    private Button saveWorkHours;
    TimePickerDialog timePickerDialog;

    public static void setCurrentEmployee(User currentEmployee) {
        WorkingHoursActivity.currentEmployee = currentEmployee;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_working_hours);
        DB = new DBHelper(this);
        DB.getUserData();
        DB.getServiceData();

        mondayBegin = findViewById(R.id.mondayBegin);

        mondayBegin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog = new TimePickerDialog(WorkingHoursActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        mondayBegin.setText(hourOfDay + ":" + minute);
                    }
                },0,0,false);

                timePickerDialog.show();
            }
        });


        mondayEnd = findViewById(R.id.mondayEnd);

        mondayEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mondayBegin.getText().toString().equals("")){
                    Toast.makeText(WorkingHoursActivity.this, "Working hours must have a start", Toast.LENGTH_SHORT).show();
                    return;
                }
                timePickerDialog = new TimePickerDialog(WorkingHoursActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        mondayEnd.setText(hourOfDay + ":" + minute);
                    }
                },0,0,false);

                timePickerDialog.show();
            }
        });


        tuesdayBegin = findViewById(R.id.tuesdayBegin);

        tuesdayBegin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog = new TimePickerDialog(WorkingHoursActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        tuesdayBegin.setText(hourOfDay + ":" + minute);
                    }
                },0,0,false);

                timePickerDialog.show();
            }
        });


        tuesdayEnd = findViewById(R.id.tuesdayEnd);

        tuesdayEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tuesdayBegin.getText().toString().equals("")){
                    Toast.makeText(WorkingHoursActivity.this, "Working hours must have a start", Toast.LENGTH_SHORT).show();
                    return;
                }
                timePickerDialog = new TimePickerDialog(WorkingHoursActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        tuesdayEnd.setText(hourOfDay + ":" + minute);
                    }
                },0,0,false);

                timePickerDialog.show();
            }
        });


        wednesdayBegin = findViewById(R.id.wednesdayBegin);

        wednesdayBegin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog = new TimePickerDialog(WorkingHoursActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        wednesdayBegin.setText(hourOfDay + ":" + minute);
                    }
                },0,0,false);

                timePickerDialog.show();
            }
        });


        wednesdayEnd = findViewById(R.id.wednesdayEnd);

        wednesdayEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(wednesdayBegin.getText().toString().equals("")){
                    Toast.makeText(WorkingHoursActivity.this, "Working hours must have a start", Toast.LENGTH_SHORT).show();
                    return;
                }
                timePickerDialog = new TimePickerDialog(WorkingHoursActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        wednesdayEnd.setText(hourOfDay + ":" + minute);
                    }
                },0,0,false);

                timePickerDialog.show();
            }
        });


        thursdayBegin = findViewById(R.id.thursdayBegin);

        thursdayBegin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog = new TimePickerDialog(WorkingHoursActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        thursdayBegin.setText(hourOfDay + ":" + minute);
                    }
                },0,0,false);

                timePickerDialog.show();
            }
        });

        thursdayEnd = findViewById(R.id.thursdayEnd);

        thursdayEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(thursdayBegin.getText().toString().equals("")){
                    Toast.makeText(WorkingHoursActivity.this, "Working hours must have a start", Toast.LENGTH_SHORT).show();
                    return;
                }
                timePickerDialog = new TimePickerDialog(WorkingHoursActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        thursdayEnd.setText(hourOfDay + ":" + minute);
                    }
                },0,0,false);

                timePickerDialog.show();
            }
        });

        fridayBegin = findViewById(R.id.fridayBegin);

        fridayBegin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog = new TimePickerDialog(WorkingHoursActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        fridayBegin.setText(hourOfDay + ":" + minute);
                    }
                },0,0,false);

                timePickerDialog.show();
            }
        });

        fridayEnd = findViewById(R.id.fridayEnd);

        fridayEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fridayBegin.getText().toString().equals("")){
                    Toast.makeText(WorkingHoursActivity.this, "Working hours must have a start", Toast.LENGTH_SHORT).show();
                    return;
                }
                timePickerDialog = new TimePickerDialog(WorkingHoursActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        fridayEnd.setText(hourOfDay + ":" + minute);
                    }
                },0,0,false);

                timePickerDialog.show();
            }
        });

        saturdayBegin = findViewById(R.id.saturdayBegin);

        saturdayBegin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog = new TimePickerDialog(WorkingHoursActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        saturdayBegin.setText(hourOfDay + ":" + minute);
                    }
                },0,0,false);

                timePickerDialog.show();
            }
        });


        saturdayEnd = findViewById(R.id.saturdayEnd);

        saturdayEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(saturdayBegin.getText().toString().equals("")){
                    Toast.makeText(WorkingHoursActivity.this, "Working hours must have a start", Toast.LENGTH_SHORT).show();
                    return;
                }
                timePickerDialog = new TimePickerDialog(WorkingHoursActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        saturdayEnd.setText(hourOfDay + ":" + minute);
                    }
                },0,0,false);

                timePickerDialog.show();
            }
        });


        sundayBegin = findViewById(R.id.sundayBegin);

        sundayBegin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog = new TimePickerDialog(WorkingHoursActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        sundayBegin.setText(hourOfDay + ":" + minute);
                    }
                },0,0,false);

                timePickerDialog.show();
            }
        });


        sundayEnd = findViewById(R.id.sundayEnd);

        sundayEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sundayBegin.getText().toString().equals("")){
                    Toast.makeText(WorkingHoursActivity.this, "Working hours must have a start", Toast.LENGTH_SHORT).show();
                    return;
                }
                timePickerDialog = new TimePickerDialog(WorkingHoursActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        sundayEnd.setText(hourOfDay + ":" + minute);
                    }
                },0,0,false);

                timePickerDialog.show();
            }
        });

        if(currentEmployee.getEmployeeWorkingHours().size()!=0){
            refreshPage();
        }

        saveWorkHours = findViewById(R.id.saveWorkHours);

        saveWorkHours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mondayEnd.getText().toString().equals("") && !mondayBegin.getText().toString().equals("") || mondayBegin.getText().toString().equals(mondayEnd.getText().toString()) && !mondayBegin.getText().toString().equals("")){
                    Toast.makeText(WorkingHoursActivity.this, "Invalid working hours", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(tuesdayEnd.getText().toString().equals("") && !tuesdayBegin.getText().toString().equals("") || tuesdayBegin.getText().toString().equals(tuesdayEnd.getText().toString()) && !tuesdayBegin.getText().toString().equals("")){
                    Toast.makeText(WorkingHoursActivity.this, "Invalid working hours", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(wednesdayEnd.getText().toString().equals("") && !wednesdayBegin.getText().toString().equals("") || wednesdayBegin.getText().toString().equals(wednesdayEnd.getText().toString()) && !wednesdayBegin.getText().toString().equals("")){
                    Toast.makeText(WorkingHoursActivity.this, "Invalid working hours", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(thursdayEnd.getText().toString().equals("") && !thursdayBegin.getText().toString().equals("") || thursdayBegin.getText().toString().equals(thursdayEnd.getText().toString()) && !thursdayBegin.getText().toString().equals("")){
                    Toast.makeText(WorkingHoursActivity.this, "Invalid working hours", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(fridayEnd.getText().toString().equals("") && !fridayBegin.getText().toString().equals("") || fridayEnd.getText().toString().equals(fridayBegin.getText().toString()) && !fridayBegin.getText().toString().equals("")){
                    Toast.makeText(WorkingHoursActivity.this, "Invalid working hours", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(saturdayEnd.getText().toString().equals("") && !saturdayBegin.getText().toString().equals("") || saturdayEnd.getText().toString().equals(saturdayBegin.getText().toString()) && !saturdayBegin.getText().toString().equals("")){
                    Toast.makeText(WorkingHoursActivity.this, "Invalid working hours", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(sundayEnd.getText().toString().equals("") && !sundayBegin.getText().toString().equals("") || sundayBegin.getText().toString().equals(sundayEnd.getText().toString()) && !sundayBegin.getText().toString().equals("")){
                    Toast.makeText(WorkingHoursActivity.this, "Invalid working hours", Toast.LENGTH_SHORT).show();
                    return;
                }
                currentEmployee.clearEmployeeWorkingHours();
                currentEmployee.addWorkingHours(mondayBegin.getText().toString());
                currentEmployee.addWorkingHours(mondayEnd.getText().toString());
                currentEmployee.addWorkingHours(tuesdayBegin.getText().toString());
                currentEmployee.addWorkingHours(tuesdayEnd.getText().toString());
                currentEmployee.addWorkingHours(wednesdayBegin.getText().toString());
                currentEmployee.addWorkingHours(wednesdayEnd.getText().toString());
                currentEmployee.addWorkingHours(thursdayBegin.getText().toString());
                currentEmployee.addWorkingHours(thursdayEnd.getText().toString());
                currentEmployee.addWorkingHours(fridayBegin.getText().toString());
                currentEmployee.addWorkingHours(fridayEnd.getText().toString());
                currentEmployee.addWorkingHours(saturdayBegin.getText().toString());
                currentEmployee.addWorkingHours(saturdayEnd.getText().toString());
                currentEmployee.addWorkingHours(sundayBegin.getText().toString());
                currentEmployee.addWorkingHours(sundayEnd.getText().toString());
                DB.deleteAllUserData();
                DB.deleteAllServiceData();
                DB.insertServiceData();
                DB.insertUserData();
                openEmployeeActivity();
            }
        });
    }

    public void refreshPage(){
        mondayBegin.setText(currentEmployee.getEmployeeWorkingHours().get(0));
        mondayEnd.setText(currentEmployee.getEmployeeWorkingHours().get(1));
        tuesdayBegin.setText(currentEmployee.getEmployeeWorkingHours().get(2));
        tuesdayEnd.setText(currentEmployee.getEmployeeWorkingHours().get(3));
        wednesdayBegin.setText(currentEmployee.getEmployeeWorkingHours().get(4));
        wednesdayEnd.setText(currentEmployee.getEmployeeWorkingHours().get(5));
        thursdayBegin.setText(currentEmployee.getEmployeeWorkingHours().get(6));
        thursdayEnd.setText(currentEmployee.getEmployeeWorkingHours().get(7));
        fridayBegin.setText(currentEmployee.getEmployeeWorkingHours().get(8));
        fridayEnd.setText(currentEmployee.getEmployeeWorkingHours().get(9));
        saturdayBegin.setText(currentEmployee.getEmployeeWorkingHours().get(10));
        saturdayEnd.setText(currentEmployee.getEmployeeWorkingHours().get(11));
        sundayBegin.setText(currentEmployee.getEmployeeWorkingHours().get(12));
        sundayEnd.setText(currentEmployee.getEmployeeWorkingHours().get(13));
    }

    public void openEmployeeActivity(){
        Intent intent = new Intent(this, EmployeeActivity.class);
        startActivity(intent);

    }
}