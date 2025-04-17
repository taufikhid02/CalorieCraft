package com.example.caloriecraft;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    FloatingActionButton fabBottomDialog;
    DrawerLayout drawerLayout;
    BottomNavigationView bottomNavigationView;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    Intent goToBrowseFood, goToScanBarcode, goToQuickAdd, goToQuickCalculator, goToPortionGuide, goToContactSupport, goToAboutUs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        Intent intent = getIntent();
        int extra = intent.getIntExtra("Fragment Diary", 0);
        if (extra == 1){
            replaceFragment(new DiaryFragment());
        }



        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DashboardFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_dashboard);
        }

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        fabBottomDialog = findViewById(R.id.fab);

        bottomNavigationView.setBackground(null);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.bottommenu_dashboard:
                    replaceFragment(new DashboardFragment());
                    toolbar.setTitle("Dashboard");
                    break;
                case R.id.bottommenu_diary:
                    replaceFragment(new DiaryFragment());
                    toolbar.setTitle("Diary");
                    break;
                case R.id.bottommenu_explore:
                    replaceFragment(new ExploreFragment());
                    toolbar.setTitle("Explore");

                    break;
                case R.id.bottommenu_profile:
                    replaceFragment(new ProfileFragment());
                    toolbar.setTitle("Profile");
                    break;
            }
            return true;
        });

        fabBottomDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomDialog();
            }
        });
    }
    private void showBottomDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheetlayout);

        LinearLayout browseFoodLayout = dialog.findViewById(R.id.layoutBrowseFood);
        LinearLayout scanBarcodeLayout = dialog.findViewById(R.id.layoutScanBarcode);
        LinearLayout quickAddLayout = dialog.findViewById(R.id.layoutQuickAdd);
        ImageView cancelButton = dialog.findViewById(R.id.cancelButton);

        browseFoodLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                goToBrowseFood = new Intent(MainActivity.this, BrowseFoodActivity.class);
                startActivity(goToBrowseFood);
            }
        });

        scanBarcodeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                goToScanBarcode = new Intent(MainActivity.this, BarcodeScannerActivity.class);
                startActivity(goToScanBarcode);

            }
        });

        quickAddLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                goToQuickAdd = new Intent(MainActivity.this, QuickAddActivity.class);
                startActivity(goToQuickAdd);

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }
    private  void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_dashboard:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DashboardFragment()).commit();
                break;
            case R.id.nav_quick_calculator:
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ContactSupportFragment()).commit();
                goToQuickCalculator = new Intent(MainActivity.this, QuickCalculatorActivity.class);
                startActivity(goToQuickCalculator);
                break;
            case R.id.nav_portion_guide:
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ContactSupportFragment()).commit();
                goToPortionGuide = new Intent(MainActivity.this,PortionGuideActivity.class);
                startActivity(goToPortionGuide);
                break;
            case R.id.nav_contact_support:
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ContactSupportFragment()).commit();
                goToContactSupport = new Intent(MainActivity.this,ContactSupportActivity.class);
                startActivity(goToContactSupport);
                break;
            case R.id.nav_about:
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AboutUsFragment()).commit();
                goToAboutUs = new Intent(MainActivity.this,AboutUsActivity.class);
                startActivity(goToAboutUs);
                break;
            case R.id.nav_logout:
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AboutUsFragment()).commit();
                showAlertDialog();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onBackPressed() {
        showAlertDialog();
    }
    private void showAlertDialog() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setTitle("Exit App")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MainActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}