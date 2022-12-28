package com.example.bybloscar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    private DBHelper DB;
    private Button registerButton;
    private Button backButton;
    private RadioButton employeeRadio;
    private RadioButton customerRadio;

    private EditText registerEmailField;
    private EditText registerPhoneField;
    private EditText registerUsernameField;
    private EditText registerPasswordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        DB = new DBHelper(this);
        DB.getUserData();
        DB.getServiceData();

        backButton = findViewById(R.id.cancel_register);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToLogin();
            }
        });

        registerButton = findViewById(R.id.register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUsernameField = findViewById(R.id.register_username_field);
                registerPasswordField = findViewById(R.id.register_password_field);
                registerEmailField = findViewById(R.id.register_email_field);
                registerPhoneField = findViewById(R.id.register_phone_field);

                String phone = registerPhoneField.getText().toString().trim();
                String email = registerEmailField.getText().toString().trim();
                String username = registerUsernameField.getText().toString().trim();
                String password = registerPasswordField.getText().toString().trim();

                registerActivity(username,password, email, phone);
            }
        });
        employeeRadio = findViewById(R.id.employee_radio);
        customerRadio = findViewById(R.id.customer_radio);
    }

    public void backToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public static boolean isValidMobileNo(String str)
    { //https://stackoverflow.com/questions/27631692/phone-number-validation-in-java
        String regex = "\\d{3}-\\d{3}-\\d{4}"; // XXX-XXX-XXXX
        return str.matches(regex);

    }

    public static boolean emailAlreadyExists(String email){

        if(User.getUserList().isEmpty()){

            return false;
        }

        for(User user : User.getUserList()){

            if(user.getEmail().equals(email)){

                return true;
            }
        }

        return false;
    }

    public static boolean phoneAlreadyExists(String phone){

        if(User.getUserList().isEmpty()){

            return false;
        }

        for(User user : User.getUserList()){

            if(user.getPhone().equals(phone)){

                return true;
            }
        }

        return false;
    }

    public void registerActivity(String username, String password, String email, String phone) {
        if (User.getAdminUsername().equals(username) && User.getAdminPassword().equals(password)){
            Toast.makeText(RegisterActivity.this, "That username already exists", Toast.LENGTH_SHORT).show();
            return;
        }

        if (User.hasUser(username)){

            Toast.makeText(RegisterActivity.this, "That username already exists", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(username)){

            Toast.makeText(RegisterActivity.this, "Please choose a username", Toast.LENGTH_SHORT).show();
            return;

        }

        if(TextUtils.isEmpty(password)){

            Toast.makeText(RegisterActivity.this, "Please choose a password", Toast.LENGTH_SHORT).show();
            return;

        }

        if(TextUtils.isEmpty(email)){

            Toast.makeText(RegisterActivity.this, "Please choose enter an email", Toast.LENGTH_SHORT).show();
            return;

        }

        if(TextUtils.isEmpty(phone)){

            Toast.makeText(RegisterActivity.this, "Please use XXX-XXX-XXXX format in your phone number", Toast.LENGTH_SHORT).show();
            return;

        }

        if(phoneAlreadyExists(phone)){

            Toast.makeText(RegisterActivity.this, "This phone number is already associated to an account", Toast.LENGTH_SHORT).show();
            return;

        }

        if(emailAlreadyExists(email)){

            Toast.makeText(RegisterActivity.this, "This email is already associated to an account", Toast.LENGTH_SHORT).show();
            return;

        }

        if(password.length()<6){

            Toast.makeText(RegisterActivity.this, "Choose a password longer than 6 characters", Toast.LENGTH_SHORT).show();
            return;

        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){

            Toast.makeText(RegisterActivity.this, "Invalid email address", Toast.LENGTH_SHORT).show();
            return;

        }

        if(!isValidMobileNo(phone)){

            Toast.makeText(RegisterActivity.this, "Please use XXX-XXX-XXXX format in your phone number", Toast.LENGTH_SHORT).show();
            return;

        }

        if (employeeRadio.isChecked()){
            DB.getUserData();
            User.addUser(username,password,"Employee", email, phone);
            Toast.makeText(RegisterActivity.this, "Registered as an Employee: "+username, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class);
            DB.deleteAllServiceData();
            DB.insertServiceData();
            DB.deleteAllUserData();
            DB.insertUserData();
            startActivity(intent);
        }

        if (customerRadio.isChecked()){
            DB.getUserData();
            User.addUser(username,password,"Customer", email, phone);
            Toast.makeText(RegisterActivity.this, "Registered as a Customer: "+username, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class);
            DB.deleteAllServiceData();
            DB.insertServiceData();
            DB.deleteAllUserData();
            DB.insertUserData();
            startActivity(intent);
        }

        if ((!customerRadio.isChecked()) && (!employeeRadio.isChecked())){
            Toast.makeText(RegisterActivity.this, "Please choose Customer or Employee",Toast.LENGTH_SHORT).show();
            return;

        }
    }
}
