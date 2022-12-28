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

import java.util.Map;

public class FeedbackActivity extends AppCompatActivity {
    private DBHelper DB;

    Button submitButton;
    LinearLayout feedbackScroll;
    EditText commentField;
    EditText ratingField;
    static User currentBranch = new User();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        DB = new DBHelper(this);
        DB.getUserData();
        DB.getServiceData();

        feedbackScroll= findViewById(R.id.feedbackScroll);
        submitButton = findViewById(R.id.submitButton);
        commentField = findViewById(R.id.commentField);
        ratingField = findViewById(R.id.ratingField);

        refreshPage();



        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ratingField.getText().toString().equals("")){

                    Toast.makeText(FeedbackActivity.this, "Please provide a rating. Comment is optional", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(Double.parseDouble(ratingField.getText().toString())>5 || Double.parseDouble(ratingField.getText().toString())<0){

                    Toast.makeText(FeedbackActivity.this, "Please provide a rating between 0 and 5.", Toast.LENGTH_SHORT).show();
                    return;

                }

                if(commentField.getText().toString().length()>500){

                    Toast.makeText(FeedbackActivity.this, "Comment must be shorter than 500 characters", Toast.LENGTH_SHORT).show();
                    return;

                }

                String rating = ratingField.getText().toString();
                String comment = commentField.getText().toString();

                if(commentField.getText().toString().equals("")){
                    comment="-";
                }

                currentBranch.getCommentsList().add(comment);
                currentBranch.getRatingList().add(rating);


                currentBranch.setRating(Double.parseDouble(rating));
                DB.deleteAllUserData();
                DB.deleteAllServiceData();
                DB.insertServiceData();
                DB.insertUserData();

                openBranchProfileActivity();

                Toast.makeText(FeedbackActivity.this, "Feedback shared with the branch", Toast.LENGTH_SHORT).show();
            }
        });



    }

    public void openBranchProfileActivity(){

        Intent intent = new Intent(this, BranchProfileActivity.class);
        startActivity(intent);

    }

    public void refreshPage() {

        feedbackScroll.removeAllViewsInLayout();

        for(int i=0;i<currentBranch.getRatingList().size();i++) {

                TextView text = new TextView(this);
                text.setText("Rating: " + currentBranch.getRatingList().get(i) + "/5");
                text.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.FILL_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));

                TextView text2 = new TextView(this);
            text2.setText("Comment: " + currentBranch.getCommentsList().get(i));
            text2.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.FILL_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));

                TextView text3 = new TextView(this);
                text3.setText("");
                text3.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.FILL_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));

                feedbackScroll.addView(text);
                feedbackScroll.addView(text2);
                feedbackScroll.addView(text3);



        }
    }

    public static void setCurrentBranch(User user){

        currentBranch = user;
    }


    }
