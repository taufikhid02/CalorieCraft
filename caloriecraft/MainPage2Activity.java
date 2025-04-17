package com.example.caloriecraft;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.caloriecraft.databinding.ActivityMainPage2Binding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainPage2Activity extends AppCompatActivity {
    ActivityMainPage2Binding binding;
    FloatingActionButton fabBrowse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainPage2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setBackground(null);
        replaceFragment(new DashboardFragment());
        fabBrowse = findViewById(R.id.fab_browse);
        fabBrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToBrowseFood = new Intent(MainPage2Activity.this, BrowseFoodActivity.class);
                startActivity(goToBrowseFood);
            }
        });
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.bottommenu_dashboard:
                    replaceFragment(new DashboardFragment());
                    break;
                case R.id.bottommenu_diary:
                    replaceFragment(new DiaryFragment());
                    break;
                case R.id.bottommenu_explore:
                    replaceFragment(new ExploreFragment());
                    break;
                case R.id.bottommenu_profile:
                    replaceFragment(new ProfileFragment());
                    break;
            }
            return true;
        });
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setTitle("Exit App")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MainPage2Activity.super.onBackPressed();
                        /*Intent intent = new Intent(MainPage2Activity.this, LoginActivity.class);
                        startActivity(intent);
                        // Optionally, if you want to clear the activity stack:
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        // Finish MainPage2Activity so user cannot go back to it with the back button
                        finish();*/
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.coordinatorLayout, fragment);
        fragmentTransaction.commit();
    }
}