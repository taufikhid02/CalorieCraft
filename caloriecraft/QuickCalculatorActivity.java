package com.example.caloriecraft;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class QuickCalculatorActivity extends AppCompatActivity {
    Button btnCalculate;
    Toolbar tbQuickCalculator;
    Switch swGender;
    Intent goToQuickCalculator2;
    EditText etAge, etHeight, etWeight;
    TextInputLayout tiAge, tiHeight, tiWeight;
    ScrollView svQuickCalculator;
    RadioGroup rgActivityLevel;
    RadioButton rbSedentary, rbLight, rbModerate, rbHeavy, rbAthlete;
    int gender,activity, age;
    double height, weight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_calculator);
        svQuickCalculator = findViewById(R.id.sv_quick_calculator);
        btnCalculate = findViewById(R.id.btn_next_quick_calculator);
        tbQuickCalculator = findViewById(R.id.tb_quick_calculator);
        swGender = findViewById(R.id.switch_gender_quick_calculator);
        tiAge = findViewById(R.id.ti_age_quick_calculator);
        tiHeight = findViewById(R.id.ti_height_quick_calculator);
        tiWeight = findViewById(R.id.ti_weight_quick_calculator);
        etAge = findViewById(R.id.et_age_quick_calculator);
        etHeight = findViewById(R.id.et_height_quick_calculator);
        etWeight = findViewById(R.id.et_weight_quick_calculator);
        rgActivityLevel = findViewById(R.id.rg_physical_level_quick_calculator);
        rbSedentary = findViewById(R.id.rb_physical_sedentary_quick_calculator);
        rbLight = findViewById(R.id.rb_physical_light_quick_calculator);
        rbModerate = findViewById(R.id.rb_physical_moderate_quick_calculator);
        rbHeavy = findViewById(R.id.rb_physical_heavy_quick_calculator);
        rbAthlete = findViewById(R.id.rb_physical_athlete_quick_calculator);
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
                    tiAge.setError("Field is empty");
                }
                else {
                    tiAge.setError("");
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
                    tiHeight.setError("Field is empty");
                }
                else {
                    tiHeight.setError("");
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
                    tiWeight.setError("Field is empty");
                }
                else {
                    tiWeight.setError("");
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!etAge.getText().toString().isEmpty() && !etHeight.getText().toString().isEmpty() && !etWeight.getText().toString().isEmpty() && rgActivityLevel.getCheckedRadioButtonId()!=-1) {
                    age = Integer.parseInt(etAge.getText().toString());
                    height = Double.parseDouble(etHeight.getText().toString());
                    weight = Double.parseDouble(etWeight.getText().toString());
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
                    bundle.putInt("Gender", gender);
                    bundle.putInt("Age", age);
                    bundle.putDouble("Height", height);
                    bundle.putDouble("Weight", weight);
                    bundle.putInt("Activity", activity);
                    goToQuickCalculator2 = new Intent(QuickCalculatorActivity.this, QuickCalculator2Activity.class);
                    goToQuickCalculator2.putExtras(bundle);
                    startActivity(goToQuickCalculator2);
                }
                if (etAge.getText().toString().isEmpty()){
                    tiAge.setError("Field is empty");
                }
                if (etHeight.getText().toString().isEmpty()){
                    tiHeight.setError("Field is empty");
                }
                if (etWeight.getText().toString().isEmpty()) {
                    tiWeight.setError("Field is empty");
                }
                if (rgActivityLevel.getCheckedRadioButtonId()==-1){
                    Toast.makeText(QuickCalculatorActivity.this, "Please select your activity level", Toast.LENGTH_SHORT).show();
                }
            }
        });

        setSupportActionBar(tbQuickCalculator);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        tbQuickCalculator.setNavigationIcon(R.drawable.baseline_arrow_back_24_white);
        tbQuickCalculator.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void scrollToBottom() {
        svQuickCalculator.post(new Runnable() {
            @Override
            public void run() {
                svQuickCalculator.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }
}