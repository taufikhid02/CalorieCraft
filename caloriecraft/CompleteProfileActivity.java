package com.example.caloriecraft;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class CompleteProfileActivity extends AppCompatActivity {
    RadioGroup rgActivityLevel, rgGender;
    RadioButton rbMale, rbFemale, rbSedentary, rbLight, rbModerate, rbHeavy, rbAthlete;
    TextInputLayout tfAge, tfHeight, tfWeight;
    TextInputEditText etAge, etHeight, etWeight;
    Switch swGender;
    ScrollView svCompleteProfile;
    Button btnNext;
    int age, gender, activity;
    double height, weight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_profile);
        svCompleteProfile = findViewById(R.id.sv_complete_profile);
        tfAge = findViewById(R.id.ti_age_complete_profile);
        tfHeight = findViewById(R.id.ti_height_complete_profile);
        tfWeight = findViewById(R.id.ti_weight_complete_profile);
        etAge = findViewById(R.id.et_age_complete_profile);
        etHeight = findViewById(R.id.et_height_complete_profile);
        etWeight = findViewById(R.id.et_weight_complete_profile);
        //rgGender = findViewById(R.id.rg_gender_complete_profile);
        swGender = findViewById(R.id.switch_gender_complete_profile);
        //rbMale = findViewById(R.id.rb_gender_male_complete_profile);
        //rbFemale = findViewById(R.id.rb_gender_female_complete_profile);
        rgActivityLevel = findViewById(R.id.rg_physical_level_complete_profile);
        rbSedentary = findViewById(R.id.rb_physical_sedentary_complete_profile);
        rbLight = findViewById(R.id.rb_physical_light_complete_profile);
        rbModerate = findViewById(R.id.rb_physical_moderate_complete_profile);
        rbHeavy = findViewById(R.id.rb_physical_heavy_complete_profile);
        rbAthlete = findViewById(R.id.rb_physical_athlete_complete_profile);
        btnNext = findViewById(R.id.btn_next_complete_profile);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!etAge.getText().toString().isEmpty() && !etWeight.getText().toString().isEmpty() && !etHeight.getText().toString().isEmpty() && rgActivityLevel.getCheckedRadioButtonId()!=-1){
                    age = Integer.parseInt(etAge.getText().toString());
                    weight = Double.parseDouble(etWeight.getText().toString());
                    height = Double.parseDouble(etHeight.getText().toString());
                    /*if (rbMale.isChecked()){
                        gender = 0;
                    }
                    else if (rbFemale.isChecked()){
                        gender = 1;
                    }*/
                    int selectedId = rgActivityLevel.getCheckedRadioButtonId();
                    if (selectedId == rbSedentary.getId()){
                        activity = 0;
                    }
                    else if (selectedId == rbLight.getId()){
                        activity = 1;
                    }
                    else if (selectedId == rbModerate.getId()){
                        activity = 2;
                    }
                    else if (selectedId == rbHeavy.getId()){
                        activity = 3;
                    }
                    else if (selectedId == rbAthlete.getId()){
                        activity = 4;
                    }
                    Bundle bundle = new Bundle();
                    bundle.putDouble("Weight", weight);
                    bundle.putInt("Age", age);
                    bundle.putDouble("Height", height);
                    bundle.putInt("Activity", activity);
                    bundle.putInt("Gender", gender);

                    Intent goToWeightGoal = new Intent(CompleteProfileActivity.this, WeightGoalActivity.class);
                    goToWeightGoal.putExtras(bundle);
                    startActivity(goToWeightGoal);
                }
                if (etAge.getText().toString().isEmpty()){
                    tfAge.setError("Field is empty");
                }
                if (etHeight.getText().toString().isEmpty()){
                    tfHeight.setError("Field is empty");
                }
                if (etWeight.getText().toString().isEmpty()){
                    tfWeight.setError("Field is empty");
                }
                if (rgActivityLevel.getCheckedRadioButtonId() == -1){
                    Toast.makeText(CompleteProfileActivity.this, "Please select your activity level", Toast.LENGTH_SHORT).show();
                }
                /*if (rgGender.getCheckedRadioButtonId() == -1){
                    Toast.makeText(CompleteProfileActivity.this, "Please select your gender", Toast.LENGTH_SHORT).show();
                }*/
            }
        });
        gender = 0;
        swGender.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){
                    gender = 1;
                    swGender.setText("Female");

                }
                else {
                    gender = 0;
                    swGender.setText("Male");
                }
            }
        });
        rgActivityLevel.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                scrollToBottom();
            }
        });
        etAge.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (etAge.getText().toString().isEmpty()){
                    tfAge.setError("Field is empty");
                }
                else {
                    tfAge.setError("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        etHeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (etHeight.getText().toString().isEmpty()){
                    tfHeight.setError("Field is empty");
                }
                else {
                    tfHeight.setError("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        etWeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (etWeight.getText().toString().isEmpty()){
                    tfWeight.setError("Field is empty");
                }
                else {
                    tfWeight.setError("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void scrollToBottom() {
        svCompleteProfile.post(new Runnable() {
            @Override
            public void run() {
                svCompleteProfile.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    @Override
    public void onBackPressed(){

    }
}