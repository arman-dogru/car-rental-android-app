package com.example.bybloscar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private DBHelper DB;

    private Button registerButton;
    private Button loginButton;
    private EditText loginUsernameField;
    private EditText loginPasswordField;
    private static boolean isFirst=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loginUsernameField = findViewById(R.id.login_username_field);
        loginPasswordField = findViewById(R.id.login_password_field);

        if(isFirst){
            Service.setDefaultDocuments();
            Service.addService(new Service("Dental Checkup", 10,Service.getDentalDocuments()));
            Service.addService(new Service("Cavity Fill", 20,Service.getCavityDocuments()));
            Service.addService(new Service("Braces Appointment", 30,Service.getBracesDocuments()));
            isFirst=false;
        }

        User.setDefaultAccounts();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        DB = new DBHelper(LoginActivity.this);
        DB.getUserData();
        DB.getServiceData();

        registerButton = findViewById(R.id.or_register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegisterActivity();
            }
        });

        loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUsernameField = findViewById(R.id.login_username_field);
                loginPasswordField = findViewById(R.id.login_password_field);

                String username = loginUsernameField.getText().toString().trim();
                String password = loginPasswordField.getText().toString().trim();

                loginActivity(username,password);
            }
        });
    }

    public void loginActivity(String username, String password) {
        if (User.getAdminUsername().equals(username) && User.getAdminPassword().equals(password)){
            openAdminActivity();
            return;
        }

        if(User.hasPassword(username,password)==0){
            login(username, User.getRoleByUsername(username));
            return;
        }

        else if(User.hasPassword(username,password)==1){
            Toast.makeText(LoginActivity.this, "Invalid username-password combination", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(LoginActivity.this, "Invalid login, register first", Toast.LENGTH_SHORT).show();
        openRegisterActivity();
    }

    public void openRegisterActivity() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void login(String username, String role){
        if(role.equals("Employee")){
            openEmployeeActivity(username);
        } else {
            openCustomerActivity(username);
        }
    }

    public void openEmployeeActivity(String user){
        Intent intent = new Intent(this, EmployeeActivity.class);
        startActivity(intent);
        EmployeeActivity.setCurrentEmployee(User.getUserObject(user));
    }

    public void openCustomerActivity(String user){
        Intent intent = new Intent(this, CustomerActivity.class);
        startActivity(intent);
        CustomerActivity.setCurrentCustomer(User.getUserObject(user));

    }

    public void openAdminActivity(){
        Intent intent = new Intent(this, AdminActivity.class);
        startActivity(intent);
    }

    public void openBranchProfileActivity(){
        Intent intent = new Intent(this, AdminActivity.class);
        startActivity(intent);
    }
}
