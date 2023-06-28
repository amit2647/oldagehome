package com.example.firebasecrudapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class UserinfoActivity extends AppCompatActivity {

    private EditText edtUserName, edtConfirmPassword, edtEmail;
    private RadioGroup rgGender;
    private Spinner spinnerPurpose;
    private Button btnRegister;
    private ProgressBar loadingProgressBar;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        // Initialize Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();

        // Initialize views
        edtUserName = findViewById(R.id.idEdtUserName);
        edtConfirmPassword = findViewById(R.id.idEdtConfirmPassword);
        edtEmail = findViewById(R.id.idEdtEmail);
        rgGender = findViewById(R.id.idRGGender);
        Spinner spinnerPurpose = findViewById(R.id.idSpinnerPurpose);
        String[] purposes = {"Donation", "Volunteer", "Other"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, purposes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPurpose.setAdapter(adapter);
        btnRegister = findViewById(R.id.idBtnRegister);
        loadingProgressBar = findViewById(R.id.idPBLoading);

        // Register button click listener
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserinfoActivity.this, MainActivity.class);
                startActivity(i);
                registerUser();
                finish();
            }
        });
    }

    private void registerUser() {
        // Get user input values
        String userName = edtUserName.getText().toString().trim();
        String password = edtConfirmPassword.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String gender = ((RadioButton) findViewById(rgGender.getCheckedRadioButtonId())).getText().toString();
        String purpose = spinnerPurpose.getSelectedItem().toString();

        // Perform any necessary validations

        // Show loading progress
        loadingProgressBar.setVisibility(View.VISIBLE);

        // Create a new user with Firebase Authentication
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // User registration successful
                            FirebaseUser user = firebaseAuth.getCurrentUser();

                            // Perform any additional actions or save user data to Firebase Realtime Database or Firestore

                            // Show a success message
                            Toast.makeText(UserinfoActivity.this, "Registration Successful!", Toast.LENGTH_SHORT).show();

                            // Open the RegistrationActivity
                            Intent intent = new Intent(UserinfoActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish(); // Optional: If you want to finish the current activity
                        } else {
                            // User registration failed
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthUserCollisionException e) {
                                // User with the same email already exists
                                Toast.makeText(UserinfoActivity.this, "User with this email already exists.", Toast.LENGTH_SHORT).show();
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                // Invalid email format
                                Toast.makeText(UserinfoActivity.this, "Invalid email format.", Toast.LENGTH_SHORT).show();
                            } catch (FirebaseAuthInvalidUserException e) {
                                // Invalid user credentials
                                Toast.makeText(UserinfoActivity.this, "Invalid email or password.", Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                // Generic error message
                                Toast.makeText(UserinfoActivity.this, "Registration failed. Please try again.", Toast.LENGTH_SHORT).show();
                            }
                        }

                        // Hide loading progress
                        loadingProgressBar.setVisibility(View.GONE);
                    }
                });
    }
}


