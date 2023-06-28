package com.example.firebasecrudapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import java.util.Calendar;

public class AddEntryActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private EditText nameEditText, addressEditText, contactNoEditText, panNumberEditText, aadhaarNumberEditText, dateOfBirthEditText, dateOfAnniversaryEditText, donationAmountEditText;
    private CheckBox maleCheckBox, femaleCheckBox;
    private Button registerButton;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String DonationID;

    private Spinner spinnerPurpose;

    // Regular expression for Aadhaar number validation
    private static final String AADHAAR_REGEX = "^[2-9]{1}[0-9]{3}\\s[0-9]{4}\\s[0-9]{4}$";
    // Regular expression for PAN number validation
    private static final String PAN_REGEX = "[A-Z]{5}[0-9]{4}[A-Z]{1}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        nameEditText = findViewById(R.id.idEdtCourseName);
        addressEditText = findViewById(R.id.idEdtCourseAdress);
        contactNoEditText = findViewById(R.id.idEdtCourseContact);
        panNumberEditText = findViewById(R.id.idEdtCoursePan);
        aadhaarNumberEditText = findViewById(R.id.idEdtCourseAadhar);
        dateOfBirthEditText = findViewById(R.id.idEdtCourseDob);
        dateOfAnniversaryEditText = findViewById(R.id.idEdtCourseDoa);
        maleCheckBox = findViewById(R.id.maleCheckBox);
        femaleCheckBox = findViewById(R.id.femaleCheckBox);
        registerButton = findViewById(R.id.registerButton);
        Spinner spinnerPurpose = findViewById(R.id.idSpinnerPurpose);
        String[] purposes = {"Donation", "Volunteer", "Other"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, purposes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPurpose.setAdapter(adapter);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Courses");

        dateOfBirthEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        contactNoEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactNoEditText.setText("+91"); // Set the default country code
                contactNoEditText.setSelection(contactNoEditText.getText().length()); // Move the cursor to the end of the text
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString().trim();
                String address = addressEditText.getText().toString().trim();
                String contactNo = contactNoEditText.getText().toString().trim();
                String dateOfBirth = dateOfBirthEditText.getText().toString().trim();
                String dateOfAnniversary = dateOfAnniversaryEditText.getText().toString().trim();
                String purpose = spinnerPurpose.getSelectedItem().toString();

                String gender = "";
                if (maleCheckBox.isChecked()) {
                    gender = "Male";
                } else if (femaleCheckBox.isChecked()) {
                    gender = "Female";
                }

                String panNumber = panNumberEditText.getText().toString().trim();
                // Perform PAN validation
                if (!panNumber.matches(PAN_REGEX)) {
                    Toast.makeText(AddEntryActivity.this, "Invalid PAN number", Toast.LENGTH_SHORT).show();
                    return; // Stop further execution
                }

                String aadhaarNumber = aadhaarNumberEditText.getText().toString().trim();
                // Perform Aadhaar validation
                if (!aadhaarNumber.matches(AADHAAR_REGEX)) {
                    Toast.makeText(AddEntryActivity.this, "Invalid Aadhaar number", Toast.LENGTH_SHORT).show();
                    return; // Stop further execution
                }

                // Perform validation on the input fields
                if (name.isEmpty() || address.isEmpty() || contactNo.isEmpty() ||
                        panNumber.isEmpty() || aadhaarNumber.isEmpty() || dateOfBirth.isEmpty() || dateOfAnniversary.isEmpty() || gender.isEmpty() || purpose.isEmpty()) {
                    Toast.makeText(AddEntryActivity.this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
                    return; // Stop further execution
                }

                // Validate the contact number using libphonenumber
                PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
                try {
                    String defaultCountryCode = "IN"; // Change this to your desired default country code
                    Phonenumber.PhoneNumber phoneNumber = phoneNumberUtil.parse(contactNo, defaultCountryCode);

                    if (!phoneNumberUtil.isValidNumber(phoneNumber)) {
                        Toast.makeText(AddEntryActivity.this, "Invalid contact number", Toast.LENGTH_SHORT).show();
                        return; // Stop further execution
                    }
                } catch (NumberParseException e) {
                    e.printStackTrace();
                    Toast.makeText(AddEntryActivity.this, "Error validating contact number", Toast.LENGTH_SHORT).show();
                    return; // Stop further execution
                }

                // Registration successful, perform further actions
                Toast.makeText(AddEntryActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();

                DonationID = name;
                EntryRvModal donationRVModal = new EntryRvModal(name, address, contactNo, panNumber, aadhaarNumber, dateOfBirth, dateOfAnniversary, purpose);
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databaseReference.child(DonationID).setValue(donationRVModal);
                        Toast.makeText(AddEntryActivity.this, "Donor registered", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddEntryActivity.this, MainActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(AddEntryActivity.this, "Failed to add Donor", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, this, year, month, day);
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        // Update the dateOfBirthEditText with the selected date
        String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
        dateOfBirthEditText.setText(selectedDate);
    }
}
