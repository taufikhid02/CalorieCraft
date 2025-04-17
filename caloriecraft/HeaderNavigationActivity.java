package com.example.caloriecraft;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.caloriecraft.Objects.UserProfileInformation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HeaderNavigationActivity extends AppCompatActivity {
    //TextView tvFirstLastName, tvEmail;
    DatabaseReference databaseReference;
    String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_header_navigation);
        //tvFirstLastName = findViewById(R.id.tv_first_last_name_header_navigation);
        //tvEmail = findViewById(R.id.tv_email_header_navigation);

        /*userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userID);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()){
                    retrieveProfileInformationData(snapshot);
                }
                else {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/
    }

    /*private void retrieveProfileInformationData(DataSnapshot snapshot) {
        DataSnapshot profileInformationSnapshot = snapshot.child("profileInformation");
        if (profileInformationSnapshot.exists()){
            UserProfileInformation userProfileInformation = profileInformationSnapshot.getValue(UserProfileInformation.class);
            if (profileInformationSnapshot != null){

                String firstName = userProfileInformation.getFirstName();
                String lastName = userProfileInformation.getLastName();
                String email = userProfileInformation.getEmailAddress();

                // set text view according to the calculated values
                tvFirstLastName.setText(firstName + " " + lastName);
                tvEmail.setText(email);

            }
            else {

            }
        }
        else {

        }
    }*/
}