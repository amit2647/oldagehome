package com.example.firebasecrudapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CourseDetailsActivity extends AppCompatActivity {
    private TextView courseNameTextView;
    private TextView courseAddressTextView;
    private TextView courseContactNoTextView;

    private TextView coursePanNoTextView;

    private TextView courseAadharNoTextView;


    private TextView courseDOBTextView;

    private TextView courseOccasionTextView;

    private TextView courseGenderTextView;

    private TextView coursePurposeTextView;
    // Add other TextViews for displaying other course details

    private DatabaseReference coursesRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        courseNameTextView = findViewById(R.id.textviewCourseName);
        courseAddressTextView = findViewById(R.id.textviewCourseAddress);
        courseContactNoTextView = findViewById(R.id.textviewCourseContactNo);
        coursePanNoTextView = findViewById(R.id.textviewCoursePanNo);
        courseAadharNoTextView = findViewById(R.id.textviewCourseAadharNo);
        courseDOBTextView = findViewById(R.id.textviewCourseDateofBirth);
        courseOccasionTextView = findViewById(R.id.textviewCourseOccasion);
        courseGenderTextView = findViewById(R.id.textviewCourseGender);
        coursePurposeTextView = findViewById(R.id.textviewCoursePurpose);



        // Initialize other TextViews for displaying other course details

        // Retrieve the selected course name from the intent
        String courseName = getIntent().getStringExtra("courseName");

        // Retrieve data from Firebase Realtime Database based on the selected name
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        coursesRef = database.getReference("Courses");

        coursesRef.orderByChild("name").equalTo(courseName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot courseSnapshot : dataSnapshot.getChildren()) {
                    EntryRvModal course = courseSnapshot.getValue(EntryRvModal.class);

                    if (course != null) {
                        courseNameTextView.setText(course.getName());
                        courseAddressTextView.setText(course.getAddress());
                        courseContactNoTextView.setText(course.getContactNo());
                        coursePanNoTextView.setText(course.getPanNumber());
                        courseAadharNoTextView.setText(course.getAadhaarNumber());
                        courseDOBTextView.setText(course.getDateOfBirth());
                        courseOccasionTextView.setText(course.getDateOfAnniversary());
                        coursePurposeTextView.setText(course.getPurpose());

                        // Set other TextViews with the corresponding course details
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error if needed
            }
        });
    }
}
