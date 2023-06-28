package com.example.firebasecrudapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;

public class PageActivity extends AppCompatActivity {

    private ImageView imageArrowLeft;
    private TextView txtCreateNewAccount;
    private ImageView imageHome;
    private TextView txtByCreatingAccOne;
    private ImageView imageSearch;
    private TextView txtByCreatingAccTwo;
    private ImageView imagePlay;
    private TextView txtByCreatingAccThree;
    private ImageView imageCall;
    private TextView txtByCreatingAccFour;
    private ImageView imageSettings;
    private TextView txtByCreatingAccFive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);


        Toolbar toolbarToolbar = findViewById(R.id.toolbarToolbar);
        setSupportActionBar(toolbarToolbar);


        imageArrowLeft = findViewById(R.id.imageArrowleft);
        txtCreateNewAccount = findViewById(R.id.txtCreteNewAccou);
        imageHome = findViewById(R.id.imageHome);
        txtByCreatingAccOne = findViewById(R.id.txtBycreatingaccOne);
        imageSearch = findViewById(R.id.imageSearch);
        txtByCreatingAccTwo = findViewById(R.id.txtBycreatingaccTwo);
        imagePlay = findViewById(R.id.imagePlay);
        txtByCreatingAccThree = findViewById(R.id.txtBycreatingaccThree);
        imageCall = findViewById(R.id.imageCall);
        txtByCreatingAccFour = findViewById(R.id.txtBycreatingaccFour);
        imageSettings = findViewById(R.id.imageSettings);
        txtByCreatingAccFive = findViewById(R.id.txtBycreatingaccFive);

        imageArrowLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the HomeActivity
                Intent intent = new Intent(PageActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Optional: finish the current activity if you don't want it to remain in the back stack
            }
        });

        txtByCreatingAccFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Sign out the user
                FirebaseAuth.getInstance().signOut();

                // Redirect the user to the login screen or any other desired activity
                Intent intent = new Intent(PageActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); // Optional: finish the current activity if you don't want it to remain in the back stack
            }
        });


    }
}
