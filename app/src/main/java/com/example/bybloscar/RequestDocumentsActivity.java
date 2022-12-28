package com.example.bybloscar;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.method.DigitsKeyListener;
import android.text.method.TextKeyListener;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Locale;

public class RequestDocumentsActivity extends AppCompatActivity {
    private DBHelper DB;

    private static User currentCustomer = new User();
    private static Service currentService = new Service();

    private LinearLayout documentsScroll;
    private Button saveButton;

    public static void setCurrentCustomer(User currentCustomer) {
        RequestDocumentsActivity.currentCustomer = currentCustomer;
    }

    public static void setCurrentRequest(String request) {
        RequestDocumentsActivity.currentService = Service.getServiceObject(request);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_documents);
        DB = new DBHelper(this);
        DB.getUserData();
        DB.getServiceData();

        documentsScroll = findViewById(R.id.documents_scroll);
        saveButton = findViewById(R.id.save_documents_button);

        refreshPage();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < documentsScroll.getChildCount(); i++) {
                    View v = documentsScroll.getChildAt(i);
                    if (((EditText) v).getText().toString().equals("")) {
                        Toast.makeText(RequestDocumentsActivity.this, "You must fill all the fields", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (((EditText) v).getHint().toString().toLowerCase().contains("email")) {

                        if(!Patterns.EMAIL_ADDRESS.matcher(((EditText) v).getText().toString()).matches()) {
                            Toast.makeText(RequestDocumentsActivity.this, "Invalid email", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }

                    if (((EditText) v).getHint().toString().toLowerCase().contains("d.o.b") ||
                            ((EditText) v).getHint().toString().toLowerCase().contains("date")) {

                        if(!((EditText) v).getText().toString().contains("/") ||
                                (((EditText) v).getText().toString().matches(".*[a-z].*"))){
                            Toast.makeText(RequestDocumentsActivity.this, "Invalid Date or Time", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }

                }

                for (int i = 0; i < documentsScroll.getChildCount(); i++) {
                    View v = documentsScroll.getChildAt(i);
                    if (currentCustomer.hasDocument(((EditText) v).getHint().toString())) {
                        Document document = currentCustomer.getDocumentObjectByType(((EditText) v).getHint().toString());
                        document.setContent(((EditText) v).getText().toString());
                        document.addService(currentService);
                        DB.deleteAllUserData();
                        DB.deleteAllServiceData();
                        DB.insertServiceData();
                        DB.insertUserData();
                        continue;
                    }
                    currentCustomer.addDocument(((EditText) v).getHint().toString(), ((EditText) v).getText().toString(), currentService);
                    DB.deleteAllUserData();
                    DB.deleteAllServiceData();
                    DB.insertServiceData();
                    DB.insertUserData();
                    openCustomerActivity(currentCustomer);

                }
                BranchProfileActivity.saveDocuments(currentService.getName());
                Toast.makeText(RequestDocumentsActivity.this, "Request sent. Waiting employer response", Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void refreshPage() {
        documentsScroll.removeAllViewsInLayout();

        for (String documentName : currentService.getRequiredDocuments()) {
            EditText editText = new EditText(this);
            editText.setHint(documentName);
            editText.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));

            if (documentName.toLowerCase(Locale.ROOT).contains("mileage")) {
                editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            } else if (documentName.toLowerCase().contains("number")) {
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
            } else if (documentName.toLowerCase().contains("name") || documentName.toLowerCase().contains("location") || documentName.toLowerCase().contains("region")) {
                editText.setInputType(InputType.TYPE_CLASS_TEXT);
                InputFilter letterFilter = new InputFilter() {
                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                        String filtered = "";
                        for (int i = start; i < end; i++) {
                            char character = source.charAt(i);
                            if (!Character.isWhitespace(character) && Character.isLetter(character)) {
                                filtered += character;
                            }
                        }

                        return filtered;
                    }

                };
                editText.setFilters(new InputFilter[]{letterFilter});
            } else if (documentName.toLowerCase().contains("date and time")) {
                editText.setFocusable(false);
                editText.setFocusableInTouchMode(true);
                editText.setCursorVisible(false);

                editText.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        showDateTimePicker(editText);
                    }
                });
            } else if (documentName.toLowerCase().contains("d.o.b")) {


                editText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDatePicker(editText);
                    }
                });

            } else if (documentName.toLowerCase().contains("address")) {
                editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_POSTAL_ADDRESS);

            } else if (documentName.toLowerCase().contains("email")) {
                editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

            }

            if (currentCustomer.getDocuments() != null) {
                if (!currentCustomer.getDocuments().isEmpty()) {
                    for (Document document : currentCustomer.getDocuments()) {
                        if (document.getType().equals(documentName)) {
                            editText.setText(document.getContent());
                        }
                    }
                }
            }
            documentsScroll.addView(editText);
        }
    }

    public void openCustomerActivity(User currentCustomer) {
        Intent intent = new Intent(this, CustomerActivity.class);
        CustomerActivity.setCurrentCustomer(currentCustomer);
        startActivity(intent);
    }

    Calendar date;

    public void showDateTimePicker(EditText editText) {

        final Calendar currentDate = Calendar.getInstance();
        date = Calendar.getInstance();

        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                date.set(year, monthOfYear, dayOfMonth);

                new TimePickerDialog(RequestDocumentsActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {


                        date.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        date.set(Calendar.MINUTE, minute);
                        editText.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth + " " + hourOfDay + ":" + minute);

                    }
                }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), false).show();


            }
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();


    }

    public void showDatePicker(EditText editText) {
        final Calendar currentDate = Calendar.getInstance();

        date = Calendar.getInstance();
        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date.set(year, monthOfYear, dayOfMonth);
                editText.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);

            }
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();

    }

}