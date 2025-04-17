package com.example.caloriecraft;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.caloriecraft.Objects.TitleCaseConverter;
import com.example.caloriecraft.Objects.UserBodyStatsInformation;
import com.example.caloriecraft.Objects.UserProfileInformation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ProfileFragment extends Fragment {
    TextView tvGender, tvAge, tvActivityLevel, tvHeight, tvWeight, tvWeightGoal, tvCalorieTarget, tvFirstLastName, tvEmail;
    String userID;
    DatabaseReference databaseReference;
    ImageView imgEditBodyStats;
    TitleCaseConverter titleCaseConverter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        tvGender = view.findViewById(R.id.tv_gender_profile);
        tvAge = view.findViewById(R.id.tv_age_profile);
        tvActivityLevel = view.findViewById(R.id.tv_activity_level_profile);
        tvHeight = view.findViewById(R.id.tv_height_profile);
        tvWeight = view.findViewById(R.id.tv_weight_profile);
        tvWeightGoal = view.findViewById(R.id.tv_weight_goal_profile);
        tvCalorieTarget = view.findViewById(R.id.tv_calorie_target_profile);
        tvFirstLastName = view.findViewById(R.id.tv_first_last_name_profile);
        tvEmail = view.findViewById(R.id.tv_email_profile);
        imgEditBodyStats = view.findViewById(R.id.img_edit_body_stats_profile);

        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userID);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    retrieveProfileInformationData(snapshot);
                    retrieveBodyStatsData(snapshot);
                }
                else {
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        imgEditBodyStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Edit Information");
                builder.setMessage("If proceed, certain data may be affected");
                builder
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent goToCompleteProfile = new Intent(getActivity(), CompleteProfileActivity.class);
                                startActivity(goToCompleteProfile);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                builder.show();

            }
        });

        return view;
    }

    private void retrieveBodyStatsData(DataSnapshot snapshot) {
        DataSnapshot bodyStatsSnapshot = snapshot.child("bodyStatistics");
        if (bodyStatsSnapshot.exists()){
            UserBodyStatsInformation userBodyStatsInformation = bodyStatsSnapshot.getValue(UserBodyStatsInformation.class);
            if (userBodyStatsInformation != null){
                String gender = userBodyStatsInformation.getSex();
                int age = userBodyStatsInformation.getAge();
                double height = userBodyStatsInformation.getHeight();
                double weight = userBodyStatsInformation.getWeight();
                String physicalLevel = userBodyStatsInformation.getPhysicalLevel();
                int calorieTarget = userBodyStatsInformation.getCalorieTarget();
                int weightGoal = userBodyStatsInformation.getWeightGoal();

                // set text view according to the calculated values
                titleCaseConverter = new TitleCaseConverter();
                tvGender.setText(titleCaseConverter.convertToTitleCaseIteratingChars(gender));
                tvAge.setText(String.valueOf(age));
                tvHeight.setText(String.valueOf(height));
                tvWeight.setText(String.valueOf(weight));
                tvActivityLevel.setText(titleCaseConverter.convertToTitleCaseIteratingChars(physicalLevel));
                tvCalorieTarget.setText(String.valueOf(calorieTarget));
                tvWeightGoal.setText(String.valueOf(weightGoal));
            }
            else {

            }
        }
        else {

        }
    }
    private void retrieveProfileInformationData(DataSnapshot snapshot) {
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
    }
}