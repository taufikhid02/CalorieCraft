package com.example.caloriecraft;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ThrowOnExtraProperties;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private Button btnLogin;
    private TextView tvSignup;
    protected EditText etEmail, etPassword;
    protected TextInputLayout tfEmail, tfPassword;
    private FirebaseAuth mFirebaseAuth;
    String email, password, emailAddressInput, passwordInput;
    DatabaseReference databaseReference, databaseUserProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Element Declaration
        mFirebaseAuth = FirebaseAuth.getInstance();
        etEmail = findViewById(R.id.et_email_sign_in);
        etPassword = findViewById(R.id.et_password_sign_in);
        tfEmail = findViewById(R.id.tf_email_sign_in);
        tfPassword = findViewById(R.id.tf_password_sign_in);
        btnLogin = findViewById(R.id.btn_login);

        //EventListener
        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                emailAddressInput = etEmail.getText().toString();
                if (!emailAddressInput.isEmpty()){
                    tfEmail.setError("");
                }
                else if (emailAddressInput.isEmpty()){
                    tfEmail.setError("Please enter an email address");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                passwordInput = etPassword.getText().toString();
                if (!passwordInput.isEmpty()){
                    tfPassword.setError("");
                } else if (passwordInput.isEmpty()) {
                    tfPassword.setError("Please enter a password");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        etPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE){
                    loginToApps();
                }
                return false;
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginToApps();
            }
        });

        tvSignup = findViewById(R.id.tv_signup_login_activity);
        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToSignUpIntent = new Intent(LoginActivity.this, CreateAccountActivity.class);
                startActivity(goToSignUpIntent);
            }
        });
    }

    private void loginToApps() {
        email = etEmail.getText().toString();
        password = etPassword.getText().toString();

        email = email.trim();
        password = password.trim();

        if (email.isEmpty() || password.isEmpty()){
            tfEmail.setError("Please enter an email address");
            tfPassword.setError("Please enter a password");
            //Toast.makeText(LoginActivity.this, "Please enter an email and password", Toast.LENGTH_SHORT).show();
        }

        else{
            Toast.makeText(LoginActivity.this, "Logging in...Please wait", Toast.LENGTH_SHORT).show();
            mFirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        //Some user quit the app mid "Complete Profile" stage, leaving their profile empty although they have successfully created an account.
                        //Thus, when signing in using the recently created account, only user with incomplete profile will be navigated back to
                        //the "Complete Profile" page whereas user with complete profile will be navigated to the Dashboard Page as usual.
                        //The code below is created to handle the above situation.
                        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
                        databaseUserProfile = databaseReference.child(userID).child("bodyStatistics");
                        databaseUserProfile.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                //To check if user with userID XXXXXX has any data under the child "Profile".
                                //If exists, then it will go to Dashboard Page. Otherwise, it will go to the Complete Profile Page.
                                if (snapshot.exists()){
                                    Intent goToDashboardIntent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(goToDashboardIntent);
                                    Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Intent goToCompleteProfileIntent = new Intent(LoginActivity.this, CompleteProfileActivity.class);
                                    startActivity(goToCompleteProfileIntent);
                                    Toast.makeText(LoginActivity.this, "Please complete the profile first", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                    else{
                        Toast.makeText(LoginActivity.this, "Invalid credentials. Please try again", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}