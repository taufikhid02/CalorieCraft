package com.example.caloriecraft.Objects;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.caloriecraft.CompleteProfileActivity;
import com.example.caloriecraft.CreateAccountActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Database {
    DatabaseReference databaseReference, databaseUserList;
    public Database(){

    }
    public void saveProfile(Context context, String firstName, String lastName, String emailAddress){
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseUserList = databaseReference.child(userID);
        UserProfileInformation userProfileInformation = new UserProfileInformation(firstName, lastName, emailAddress);
        databaseUserList.child("profileInformation").setValue(userProfileInformation);
        Toast.makeText(context, "Account creation successful", Toast.LENGTH_SHORT).show();
        Intent goToCompleteProfile = new Intent(context, CompleteProfileActivity.class);
        context.startActivity(goToCompleteProfile);

    }
}
