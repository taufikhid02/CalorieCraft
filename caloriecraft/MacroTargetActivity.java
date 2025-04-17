package com.example.caloriecraft;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.caloriecraft.Adapters.MacroTargetRecyclerViewAdapter;
import com.example.caloriecraft.Objects.BodyStatistics;
import com.example.caloriecraft.Objects.MacroTarget;
import com.example.caloriecraft.Objects.ServingUnit;
import com.example.caloriecraft.Objects.UserBodyStatsInformation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MacroTargetActivity extends AppCompatActivity {

    TextView tvModerateProteinGoal, tvModerateFatGoal, tvModerateCarbGoal, tvLowerProteinGoal, tvLowerFatGoal, tvLowerCarbGoal, tvHighProteinGoal, tvHighFatGoal, tvHighCarbGoal, tvInstruction2;
    double bmi, height, weight;
    int age, gender, activity, calorieTarget, weightGoal, proteinGoal, fatGoal, carbGoal;
    FloatingActionButton fabBack;
    String sex, physicalLevel;
    CardView cvModerateGoal, cvLowerGoal, cvHighGoal;
    List<MacroTarget> macroTargetList;
    RecyclerView rvMacroTarget;
    int moderateProteinGoal, moderateFatGoal, moderateCarbGoal, lowerProteinGoal, lowerFatGoal, lowerCarbGoal, highProteinGoal, highFatGoal, highCarbGoal;

    DatabaseReference databaseReference, databaseUserList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_macro_target);

        Bundle bundle = getIntent().getExtras();
        age = bundle.getInt("Age");
        height = bundle.getDouble("Height");
        weight = bundle.getDouble("Weight");
        gender = bundle.getInt("Gender");
        activity = bundle.getInt("Activity");
        calorieTarget = bundle.getInt("Calorie Target");
        weightGoal = bundle.getInt("Weight Goal");
        bmi = bundle.getDouble("BMI");

        sex = sexClassifier(gender);
        physicalLevel = activityClassifier(activity);

        /*cvModerateGoal = findViewById(R.id.cv_moderate_target_macro_target);
        cvLowerGoal = findViewById(R.id.cv_lower_target_macro_target);
        cvHighGoal = findViewById(R.id.cv_high_target_macro_target);
        tvModerateProteinGoal = findViewById(R.id.tv_proteinGoal_moderate_macro_target);
        tvModerateFatGoal = findViewById(R.id.tv_fatGoal_moderate_macro_target);
        tvModerateCarbGoal = findViewById(R.id.tv_carbGoal_moderate_macro_target);
        tvLowerProteinGoal = findViewById(R.id.tv_proteinGoal_lower_macro_target);
        tvLowerFatGoal = findViewById(R.id.tv_fatGoal_lower_macro_target);
        tvLowerCarbGoal = findViewById(R.id.tv_carbGoal_lower_macro_target);
        tvHighProteinGoal = findViewById(R.id.tv_proteinGoal_high_macro_target);
        tvHighFatGoal = findViewById(R.id.tv_fatGoal_high_macro_target);
        tvHighCarbGoal = findViewById(R.id.tv_carbGoal_high_macro_target);*/
        tvInstruction2 = findViewById(R.id.tv_instruction2);
        fabBack = findViewById(R.id.fab_back_set_macro_target);
        rvMacroTarget = findViewById(R.id.rv_macro_target_list_macro_target);

        fabBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        tvInstruction2.setText("This is calculated based on " + calorieTarget + " Cal");

        BodyStatistics bodyStatistics = new BodyStatistics();
        int [] array = bodyStatistics.getArrayMacroTarget(calorieTarget);
        moderateProteinGoal = array[0];
        moderateFatGoal = array[1];
        moderateCarbGoal = array[2];

        lowerProteinGoal = array[3];
        lowerFatGoal = array[4];
        lowerCarbGoal = array[5];

        highProteinGoal = array[6];
        highFatGoal = array[7];
        highCarbGoal = array[8];

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MacroTargetActivity.this);
        rvMacroTarget.setLayoutManager(linearLayoutManager);
        rvMacroTarget.setHasFixedSize(true);
        macroTargetList = getMacroTargetList();
        MacroTargetRecyclerViewAdapter macroTargetRecyclerViewAdapter = new MacroTargetRecyclerViewAdapter(MacroTargetActivity.this, macroTargetList);
        rvMacroTarget.setAdapter(macroTargetRecyclerViewAdapter);

        /*tvModerateProteinGoal.setText(String.valueOf(array[0])+"g");
        tvModerateFatGoal.setText(String.valueOf(array[1])+"g");
        tvModerateCarbGoal.setText(String.valueOf(array[2])+"g");

        tvLowerProteinGoal.setText(String.valueOf(array[3])+"g");
        tvLowerFatGoal.setText(String.valueOf(array[4])+"g");
        tvLowerCarbGoal.setText(String.valueOf(array[5])+"g");

        tvHighProteinGoal.setText(String.valueOf(array[6])+"g");
        tvHighFatGoal.setText(String.valueOf(array[7])+"g");
        tvHighCarbGoal.setText(String.valueOf(array[8])+"g");

        cvModerateGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MacroTargetActivity.this);
                builder.setMessage("Are you sure with the choice?").setTitle("Confirm selection").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        proteinGoal = array[0];
                        fatGoal = array[1];
                        carbGoal = array[2];
                        saveProfile();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        cvLowerGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MacroTargetActivity.this);
                builder.setMessage("Are you sure with the choice?").setTitle("Confirm selection").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        proteinGoal = array[3];
                        fatGoal = array[4];
                        carbGoal = array[5];
                        saveProfile();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        cvHighGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MacroTargetActivity.this);
                builder.setMessage("Are you sure with the choice?").setTitle("Confirm selection").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        proteinGoal = array[6];
                        fatGoal = array[7];
                        carbGoal = array[8];
                        saveProfile();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });*/

    }

    private List<MacroTarget> getMacroTargetList() {
        List<MacroTarget> macroTargetList = new ArrayList<>();
        macroTargetList.add(new MacroTarget("#B6E884", sex, age, height, weight, physicalLevel, calorieTarget, bmi, weightGoal, "Moderate Carb (30:35:35)", moderateProteinGoal, moderateFatGoal, moderateCarbGoal));
        macroTargetList.add(new MacroTarget("#FFDE59", sex, age, height, weight, physicalLevel, calorieTarget, bmi, weightGoal, "Lower Carb (40:40:20)", lowerProteinGoal, lowerFatGoal, lowerCarbGoal));
        macroTargetList.add(new MacroTarget("#50E5FF", sex, age, height, weight, physicalLevel, calorieTarget, bmi, weightGoal, "High Carb (30:20:50)", highProteinGoal, highFatGoal, highCarbGoal));

        return macroTargetList;
    }

    private String activityClassifier(int activity) {
        String physicalActivity = null;
        if (activity == 0) {
            physicalActivity = "sedentary";
        }
        if (activity == 1) {
            physicalActivity = "light exercise";
        }
        if (activity == 2) {
            physicalActivity = "moderate exercise";
        }
        if (activity == 3) {
            physicalActivity = "heavy exercise";
        }
        if (activity == 4) {
            physicalActivity = "athlete";
        }
        return physicalActivity;
    }

    private String sexClassifier(int gender) {
        String sex = null;
        if (gender == 0) {
            sex = "male";
        }
        if (gender == 1) {
            sex = "female";
        }
        return sex;
    }

    private void saveProfile() {
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseUserList = databaseReference.child(userID);
        UserBodyStatsInformation userBodyStatsInformation = new UserBodyStatsInformation(sex, age, height, weight, physicalLevel, calorieTarget, bmi, weightGoal, proteinGoal, fatGoal, carbGoal);
        databaseUserList.child("bodyStatistics").setValue(userBodyStatsInformation);
        Intent goToDashboard = new Intent(MacroTargetActivity.this, MainActivity.class);
        startActivity(goToDashboard);
    }
}

/*      tvModerateProteinGoal.setText(String.valueOf(bodyStatistics.getModerateProteinTarget(calorieTarget))+"g");
        tvModerateFatGoal.setText(String.valueOf(bodyStatistics.getModerateFatTarget(calorieTarget))+"g");
        tvModerateCarbGoal.setText(String.valueOf(bodyStatistics.getModerateCarbTarget(calorieTarget))+"g");

        tvHighProteinGoal.setText(String.valueOf(bodyStatistics.getHighProteinTarget(calorieTarget))+"g");
        tvHighFatGoal.setText(String.valueOf(bodyStatistics.getHighFatTarget(calorieTarget))+"g");
        tvHighCarbGoal.setText(String.valueOf(bodyStatistics.getHighCarbTarget(calorieTarget))+"g");

        tvLowerProteinGoal.setText(String.valueOf(bodyStatistics.getLowerProteinTarget(calorieTarget))+"g");
        tvLowerFatGoal.setText(String.valueOf(bodyStatistics.getLowerFatTarget(calorieTarget))+"g");
        tvLowerCarbGoal.setText(String.valueOf(bodyStatistics.getLowerCarbTarget(calorieTarget))+"g");*/

/*public class MacroTargetActivity extends AppCompatActivity {


    private SurfaceView surfaceView;
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private static final int REQUEST_CAMERA_PERMISSION = 201;
    //private ToneGenerator toneGen1;
    private TextView barcodeText, tvInfo;
    private String barcodeData;
    private Button btn_goToMainPage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
        surfaceView = findViewById(R.id.surface_view);
        barcodeText = findViewById(R.id.barcode_text);
        tvInfo = findViewById(R.id.tv_info);
        btn_goToMainPage = findViewById(R.id.btn_goToMain);
        btn_goToMainPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int clientID = 114069;
                Intent IntentMainPage = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(IntentMainPage);
                Uri webpage = Uri.parse("http://www.strava.com/oauth/authorize?client_id="+clientID+"&response_type=code&redirect_uri=http://localhost/exchange_token&approval_prompt=force&scope=read");
                Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
                if (webIntent.resolveActivity(getPackageManager()) != null){ //to verify whether if there is any suitable app or not to receive this intent. In this case, to make call
                    startActivity(webIntent);
                }
                else {
                    Toast.makeText(MainActivity.this, "There is no app available to complete this action",Toast.LENGTH_SHORT).show();
                }
            }
        });
        initialiseDetectorsAndSources();
    }

    private void initialiseDetectorsAndSources() {

        //Toast.makeText(getApplicationContext(), "Barcode scanner started", Toast.LENGTH_SHORT).show();

        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();

        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(1920, 1080)
                .setAutoFocusEnabled(true) //you should add this feature
                .build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(surfaceView.getHolder());
                    } else {
                        ActivityCompat.requestPermissions(MainActivity.this, new
                                String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });


        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
                // Toast.makeText(getApplicationContext(), "To prevent memory leaks barcode scanner has been stopped", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0) {

                    barcodeText.post(new Runnable() {

                        @Override
                        public void run() {

                            if (barcodes.valueAt(0).email != null) {
                                barcodeText.removeCallbacks(null);
                                barcodeData = barcodes.valueAt(0).email.address;
                                barcodeText.setText(barcodeData);
                                //toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP, 150);
                            } else {

                                barcodeData = barcodes.valueAt(0).displayValue;
                                barcodeText.setText(barcodeData);

                                //toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP, 150);

                            }
                        }
                    });

                }
            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        getSupportActionBar().hide();
        cameraSource.release();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportActionBar().hide();
        initialiseDetectorsAndSources();
    }

}*/