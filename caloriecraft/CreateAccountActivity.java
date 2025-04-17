package com.example.caloriecraft;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.caloriecraft.Objects.Database;
import com.example.caloriecraft.Objects.UserProfileInformation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateAccountActivity extends AppCompatActivity {
    private TextView tvMessage;
    protected EditText etFirstName, etLastName, etEmail, etPassword, etConfirmPassword;
    TextInputLayout tfPassword, tfConfirmPassword, tfFirstName, tfLastName, tfEmail;
    private Button btnSignup;
    String password, repassword, emailAddress, firstName, lastName, passwordInput, confirmPasswordInput, passwordInput2, confirmPasswordInput2;
    FirebaseAuth mFirebaseAuth;
    DatabaseReference databaseReference, databaseUserList;
    Database database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        mFirebaseAuth = FirebaseAuth.getInstance();
        tfPassword = findViewById(R.id.tf_password_sign_up);
        tfConfirmPassword = findViewById(R.id.tf_confirm_password_sign_up);
        tfFirstName = findViewById(R.id.tf_first_name_sign_up);
        tfLastName = findViewById(R.id.tf_last_name_sign_up);
        tfEmail = findViewById(R.id.tf_email_sign_up);
        etFirstName = findViewById(R.id.et_first_name_sign_up);
        etLastName = findViewById(R.id.et_lastname_sign_up);
        etEmail = findViewById(R.id.et_email_sign_up);
        etPassword = findViewById(R.id.et_password_sign_up);
        etConfirmPassword = findViewById(R.id.et_confirm_password_sign_up);
        tvMessage = findViewById(R.id.tv_message_sign_up);
        btnSignup = findViewById(R.id.btn_next_complete_profile);

        //Check validity of password. Must contain at least 8 chars and include minimum of 1 special char.
        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                passwordInput = charSequence.toString();
                passwordInput2 = etPassword.getText().toString();
                Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");
                Matcher matcher = pattern.matcher(passwordInput);
                boolean isPwdContainsSpeChar = matcher.find();
                if (!passwordInput2.isEmpty()){
                    tfPassword.setError("");
                    etConfirmPassword.setText("");
                    if (passwordInput.length()>=8){
                        if (isPwdContainsSpeChar){
                            tfPassword.setHelperText("Strong Password");
                            tfPassword.setError("");
                        }
                        else if (passwordInput.length()<8){
                            tfPassword.setHelperText("");
                            tfPassword.setError("Weak Password. Include minimum 1 special character.");
                        }
                    }
                    else {
                        tfPassword.setHelperText("");
                        tfPassword.setError("Enter minimum of 8 chars");
                    }
                } else if (passwordInput2.isEmpty()) {
                    tfPassword.setError("Please enter a password");
                }

            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        etConfirmPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE){
                    createAccount();
                }
                return false;
            }
        });
        etConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                confirmPasswordInput = charSequence.toString();
                confirmPasswordInput2 = etConfirmPassword.getText().toString();

                if (!confirmPasswordInput2.isEmpty()){
                    tfConfirmPassword.setError("");
                    if (!confirmPasswordInput.equals(passwordInput)){
                        tfConfirmPassword.setHelperText("");
                        tfConfirmPassword.setError("Password does not match");
                    }
                    else if (confirmPasswordInput.equals(passwordInput)) {
                        tfConfirmPassword.setHelperText("Password match");
                        tfConfirmPassword.setError("");
                    }
                }
                else if (confirmPasswordInput2.isEmpty()){
                    tfConfirmPassword.setError("Please re-enter a password");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        tvMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount();
            }
        });
    }

    private void createAccount() {
        password = etPassword.getText().toString();
        repassword = etConfirmPassword.getText().toString();
        emailAddress = etEmail.getText().toString();
        firstName = etFirstName.getText().toString();
        lastName = etLastName.getText().toString();
        password = password.trim();
        repassword = repassword.trim();
        emailAddress = emailAddress.trim();

        if (!emailAddress.isEmpty() && !repassword.isEmpty() && !password.isEmpty() && !firstName.isEmpty() && !lastName.isEmpty()){
            Toast.makeText(CreateAccountActivity.this, "Creating account...Please wait", Toast.LENGTH_SHORT).show();
            mFirebaseAuth.createUserWithEmailAndPassword(emailAddress,repassword).addOnCompleteListener(CreateAccountActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        saveProfile();
                        //database = new Database();
                        //database.saveProfile(CreateAccountActivity.this, firstName, lastName, emailAddress);
                    }
                    else{
                        Toast.makeText(CreateAccountActivity.this, "Email is already in used or retype password", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else {
            Toast.makeText(CreateAccountActivity.this, "Please complete the form", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveProfile() {
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseUserList = databaseReference.child(userID);
        UserProfileInformation userProfileInformation = new UserProfileInformation(firstName, lastName, emailAddress);
        databaseUserList.child("profileInformation").setValue(userProfileInformation);
        Toast.makeText(CreateAccountActivity.this, "Account creation successful", Toast.LENGTH_SHORT).show();
        Intent goToCompleteProfile = new Intent(CreateAccountActivity.this, CompleteProfileActivity.class);
        startActivity(goToCompleteProfile);
    }
}