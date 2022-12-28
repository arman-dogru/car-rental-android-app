package com.example.bybloscar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AccountManagerActivity extends AppCompatActivity {
    private DBHelper DB;

    private LinearLayout accountsLayout;
    private EditText usernameField;
    private Button deleteAccountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_manager);
        DB = new DBHelper(this);
        DB.getUserData();
        DB.getServiceData();

        accountsLayout = findViewById(R.id.accounts_scrollView);

        refreshList();

        deleteAccountButton = findViewById(R.id.delete_account_button);
        deleteAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usernameField = findViewById(R.id.delete_username_field);
                String username = usernameField.getText().toString().trim();

                if(TextUtils.isEmpty(username)){
                    return;
                }

                if(!User.hasUser(username)){

                    Toast.makeText(AccountManagerActivity.this, "That user doesn't exist", Toast.LENGTH_SHORT).show();
                    return;
                }
                User.removeUser(username);
                DB.deleteAllUserData();
                DB.deleteAllServiceData();
                DB.insertServiceData();
                DB.insertUserData();
                refreshList();
            }
        });
    }


    public void refreshList(){
        this.accountsLayout.removeAllViewsInLayout();

        for (User user : User.getUserList()) {
            TextView userText = new TextView(this);
            userText.setText(user.getUsername());
            userText.setId(user.getUsername().hashCode());
            userText.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            this.accountsLayout.addView(userText);
        }
    }
}