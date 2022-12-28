package com.example.bybloscar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class UserInfoActivity extends AppCompatActivity {
    private DBHelper DB;

    private static User currentUser = new User();

    LinearLayout userInfoScroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        DB = new DBHelper(this);
        DB.getUserData();
        DB.getServiceData();

        userInfoScroll = findViewById(R.id.user_info_scroll);

        TextView username = new TextView(this);
        username.setText("Username: "+currentUser.getUsername());
        userInfoScroll.addView(username);

        TextView password = new TextView(this);
        password.setText("Password: "+currentUser.getPassword());
        userInfoScroll.addView(password);

        TextView phone = new TextView(this);
        phone.setText("Phone: "+currentUser.getPhone());
        userInfoScroll.addView(phone);

        TextView role = new TextView(this);
        role.setText("Role: "+currentUser.getRole());
        userInfoScroll.addView(role);

        TextView email = new TextView(this);
        email.setText("Email: "+currentUser.getEmail());
        userInfoScroll.addView(email);
    }

    public static void setCurrentUser(User currentUser) {
        UserInfoActivity.currentUser = currentUser;
    }
}