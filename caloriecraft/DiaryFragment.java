package com.example.caloriecraft;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.caloriecraft.Adapters.BreakfastDiaryRecyclerViewAdapter;
import com.example.caloriecraft.Adapters.DinnerDiaryRecyclerViewAdapter;
import com.example.caloriecraft.Adapters.LunchDiaryRecyclerViewAdapter;
import com.example.caloriecraft.Adapters.SnacksDiaryRecyclerViewAdapter;
import com.example.caloriecraft.Objects.FoodDiary;
import com.example.caloriecraft.Objects.Date;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class DiaryFragment extends Fragment {
    DatabaseReference databaseReference, databaseCalorieProgress, databaseProteinProgress, databaseFatProgress, databaseCarbProgress;
    Date todayDate;
    DecimalFormat df = new DecimalFormat("0.00");
    TextView tvBreakfastNettCalorie, tvLunchNettCalorie, tvDinnerNettCalorie, tvSnacksNettCalorie;
    LinearLayoutManager breakfastLinearLayoutManager, lunchLinearLayoutManager, dinnerLinearLayoutManager, snacksLinearLayoutManager;
    RecyclerView breakfastRecyclerView, lunchRecyclerView, dinnerRecyclerView, snacksRecyclerView;
    String date, userID;
    double overallCalorieProgress, overallProteinProgress, overallFatProgress, overallCarbProgress;
    double breakfastCalorieProgress, breakfastProteinProgress, breakfastFatProgress, breakfastCarbProgress;
    double lunchCalorieProgress, lunchProteinProgress, lunchFatProgress, lunchCarbProgress;
    double dinnerCalorieProgress, dinnerProteinProgress, dinnerFatProgress, dinnerCarbProgress;
    double snacksCalorieProgress, snacksProteinProgress, snacksFatProgress, snacksCarbProgress;

    //Callback Interfaces
    public interface BreakfastDiaryCallback{
        void onCallback(List<FoodDiary> breakfastDiaryList);
    }
    public interface LunchDiaryCallback {
        void onCallback(List<FoodDiary> lunchDiaryList);
    }
    public interface DinnerDiaryCallback {
        void onCallback(List<FoodDiary> dinnerDiaryList);
    }
    public interface SnacksDiaryCallback {
        void onCallback(List<FoodDiary> snacksDiaryList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_diary, container, false);

        tvBreakfastNettCalorie = view.findViewById(R.id.tv_breakfastNettCalorie);
        tvLunchNettCalorie = view.findViewById(R.id.tv_lunchNettCalorie);
        tvDinnerNettCalorie = view.findViewById(R.id.tv_dinnerNettCalorie);
        tvSnacksNettCalorie = view.findViewById(R.id.tv_snacksNettCalorie);
        breakfastRecyclerView = view.findViewById(R.id.rv_breakfast_diary_food_diary);
        lunchRecyclerView = view.findViewById(R.id.rv_lunch_diary_food_diary);
        dinnerRecyclerView = view.findViewById(R.id.rv_dinner_diary_food_diary);
        snacksRecyclerView = view.findViewById(R.id.rv_snacks_diary_food_diary);

        breakfastRecyclerView.setHasFixedSize(true);
        lunchRecyclerView.setHasFixedSize(true);
        dinnerRecyclerView.setHasFixedSize(true);
        snacksRecyclerView.setHasFixedSize(true);

        breakfastLinearLayoutManager = new LinearLayoutManager(this.getContext());
        lunchLinearLayoutManager = new LinearLayoutManager(this.getContext());
        dinnerLinearLayoutManager = new LinearLayoutManager(this.getContext());
        snacksLinearLayoutManager = new LinearLayoutManager(this.getContext());

        breakfastRecyclerView.setLayoutManager(breakfastLinearLayoutManager);
        lunchRecyclerView.setLayoutManager(lunchLinearLayoutManager);
        dinnerRecyclerView.setLayoutManager(dinnerLinearLayoutManager);
        snacksRecyclerView.setLayoutManager(snacksLinearLayoutManager);

        todayDate = new Date();
        date = todayDate.getTodayDate();
        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        getAllBreakfastDiaryInfo(new BreakfastDiaryCallback() {
            @Override
            public void onCallback(List<FoodDiary> breakfastDiaryList) {
                BreakfastDiaryRecyclerViewAdapter breakfastDiaryRecyclerViewAdapter = new BreakfastDiaryRecyclerViewAdapter(getContext(), breakfastDiaryList);
                breakfastRecyclerView.setAdapter(breakfastDiaryRecyclerViewAdapter);
            }
        });
        getAllLunchDiaryInfo(new LunchDiaryCallback() {
            @Override
            public void onCallback(List<FoodDiary> lunchDiaryList) {
                LunchDiaryRecyclerViewAdapter lunchDiaryRecyclerViewAdapter = new LunchDiaryRecyclerViewAdapter(getContext(), lunchDiaryList);
                lunchRecyclerView.setAdapter(lunchDiaryRecyclerViewAdapter);
            }

        });
        getAllDinnerDiaryInfo(new DinnerDiaryCallback() {
            @Override
            public void onCallback(List<FoodDiary> dinnerDiaryList) {
                DinnerDiaryRecyclerViewAdapter dinnerDiaryRecyclerViewAdapter = new DinnerDiaryRecyclerViewAdapter(getContext(), dinnerDiaryList);
                dinnerRecyclerView.setAdapter(dinnerDiaryRecyclerViewAdapter);
            }

        });
        getAllSnacksDiaryInfo(new SnacksDiaryCallback() {
            @Override
            public void onCallback(List<FoodDiary> snacksDiaryList) {
                SnacksDiaryRecyclerViewAdapter snacksDiaryRecyclerViewAdapter = new SnacksDiaryRecyclerViewAdapter(getContext(), snacksDiaryList);
                snacksRecyclerView.setAdapter(snacksDiaryRecyclerViewAdapter);
            }

        });

        databaseCalorieProgress = FirebaseDatabase.getInstance().getReference("Users").child(userID).child("mealsDiary").child(date).child("calorieProgress");
        databaseProteinProgress = FirebaseDatabase.getInstance().getReference("Users").child(userID).child("mealsDiary").child(date).child("proteinProgress");
        databaseFatProgress = FirebaseDatabase.getInstance().getReference("Users").child(userID).child("mealsDiary").child(date).child("fatProgress");
        databaseCarbProgress = FirebaseDatabase.getInstance().getReference("Users").child(userID).child("mealsDiary").child(date).child("carbProgress");

        return view;
    }

    private List<FoodDiary> getAllBreakfastDiaryInfo(BreakfastDiaryCallback breakfastDiarycallback) {
        List<FoodDiary> breakfastDiaryList = new ArrayList<FoodDiary>();
        breakfastCalorieProgress = 0;
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userID).child("mealsDiary").child(date).child("Breakfast");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String breakfastFoodTitle, breakfastServingUnit;
                double breakfastFoodCalorie, breakfastFoodProtein, breakfastFoodFat, breakfastFoodCarb, breakfastServingNumber, breakfastServingSize;
                for (DataSnapshot breakfastSnapshot : snapshot.getChildren()){
                    breakfastFoodTitle = breakfastSnapshot.getKey();
                    breakfastFoodCalorie = breakfastSnapshot.child("foodCalorie").getValue(Double.class);
                    breakfastFoodProtein = breakfastSnapshot.child("foodProtein").getValue(Double.class);
                    breakfastFoodFat = breakfastSnapshot.child("foodFat").getValue(Double.class);
                    breakfastFoodCarb = breakfastSnapshot.child("foodCarb").getValue(Double.class);
                    breakfastServingNumber = breakfastSnapshot.child("servingNumber").getValue(Double.class);
                    breakfastServingSize = breakfastSnapshot.child("servingSize").getValue(Double.class);
                    breakfastServingUnit = breakfastSnapshot.child("servingUnit").getValue(String.class);

                    breakfastDiaryList.add(new FoodDiary(breakfastFoodTitle, breakfastFoodCalorie, breakfastFoodProtein, breakfastFoodFat, breakfastFoodCarb, breakfastServingNumber, breakfastServingSize, breakfastServingUnit));
                    breakfastCalorieProgress = breakfastCalorieProgress + breakfastFoodCalorie;
                    breakfastProteinProgress = breakfastProteinProgress + breakfastFoodProtein;
                    breakfastFatProgress = breakfastFatProgress + breakfastFoodFat;
                    breakfastCarbProgress = breakfastCarbProgress + breakfastFoodCarb;
                }
                breakfastDiarycallback.onCallback(breakfastDiaryList);
                tvBreakfastNettCalorie.setText(df.format(breakfastCalorieProgress) + " Cal");
                saveProgress();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return breakfastDiaryList;
    }
    private List<FoodDiary> getAllLunchDiaryInfo(LunchDiaryCallback lunchDiaryCallback) {
        List<FoodDiary> lunchDiaryList = new ArrayList<FoodDiary>();
        lunchCalorieProgress = 0;
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userID).child("mealsDiary").child(date).child("Lunch");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String lunchFoodTitle, lunchServingUnit;
                double lunchFoodCalorie, lunchFoodProtein, lunchFoodFat, lunchFoodCarb, lunchServingNumber, lunchServingSize;
                for (DataSnapshot lunchSnapshot : snapshot.getChildren()){
                    lunchFoodTitle = lunchSnapshot.getKey();
                    lunchFoodCalorie = lunchSnapshot.child("foodCalorie").getValue(Double.class);
                    lunchFoodProtein = lunchSnapshot.child("foodProtein").getValue(Double.class);
                    lunchFoodFat = lunchSnapshot.child("foodFat").getValue(Double.class);
                    lunchFoodCarb = lunchSnapshot.child("foodCarb").getValue(Double.class);
                    lunchServingNumber = lunchSnapshot.child("servingNumber").getValue(Double.class);
                    lunchServingSize = lunchSnapshot.child("servingSize").getValue(Double.class);
                    lunchServingUnit = lunchSnapshot.child("servingUnit").getValue(String.class);
                    lunchDiaryList.add(new FoodDiary(lunchFoodTitle, lunchFoodCalorie, lunchFoodProtein, lunchFoodFat, lunchFoodCarb, lunchServingNumber, lunchServingSize, lunchServingUnit));
                    lunchCalorieProgress = lunchCalorieProgress + lunchFoodCalorie;
                    lunchProteinProgress =  lunchProteinProgress +  lunchFoodProtein;
                    lunchFatProgress =  lunchFatProgress +  lunchFoodFat;
                    lunchCarbProgress =  lunchCarbProgress +  lunchFoodCarb;
                }
                lunchDiaryCallback.onCallback(lunchDiaryList);
                tvLunchNettCalorie.setText(df.format(lunchCalorieProgress) + " Cal");
                //databaseNutritionProgress.setValue(breakfastCalorieProgress + lunchCalorieProgress + dinnerCalorieProgress + snacksCalorieProgress);
                saveProgress();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return lunchDiaryList;
    }
    private List<FoodDiary> getAllDinnerDiaryInfo(DinnerDiaryCallback dinnerDiaryCallback) {
        List<FoodDiary> dinnerDiaryList = new ArrayList<FoodDiary>();
        dinnerCalorieProgress = 0;
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userID).child("mealsDiary").child(date).child("Dinner");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String dinnerFoodTitle, dinnerServingUnit;
                double dinnerFoodCalorie, dinnerFoodProtein, dinnerFoodFat, dinnerFoodCarb, dinnerServingNumber, dinnerServingSize;
                for (DataSnapshot dinnerSnapshot : snapshot.getChildren()){
                    dinnerFoodTitle = dinnerSnapshot.getKey();
                    dinnerFoodCalorie = dinnerSnapshot.child("foodCalorie").getValue(Double.class);
                    dinnerFoodProtein = dinnerSnapshot.child("foodProtein").getValue(Double.class);
                    dinnerFoodFat = dinnerSnapshot.child("foodFat").getValue(Double.class);
                    dinnerFoodCarb = dinnerSnapshot.child("foodCarb").getValue(Double.class);
                    dinnerServingNumber = dinnerSnapshot.child("servingNumber").getValue(Double.class);
                    dinnerServingSize = dinnerSnapshot.child("servingSize").getValue(Double.class);
                    dinnerServingUnit = dinnerSnapshot.child("servingUnit").getValue(String.class);
                    dinnerDiaryList.add(new FoodDiary(dinnerFoodTitle, dinnerFoodCalorie, dinnerFoodProtein, dinnerFoodFat, dinnerFoodCarb, dinnerServingNumber, dinnerServingSize, dinnerServingUnit));
                    dinnerCalorieProgress = dinnerCalorieProgress + dinnerFoodCalorie;
                    dinnerProteinProgress =  dinnerProteinProgress +  dinnerFoodProtein;
                    dinnerFatProgress =  dinnerFatProgress +  dinnerFoodFat;
                    dinnerCarbProgress =  dinnerCarbProgress +  dinnerFoodCarb;
                }
                dinnerDiaryCallback.onCallback(dinnerDiaryList);
                tvDinnerNettCalorie.setText(df.format(dinnerCalorieProgress) + " Cal");
                //databaseNutritionProgress.setValue(breakfastCalorieProgress + lunchCalorieProgress + dinnerCalorieProgress + snacksCalorieProgress);
                saveProgress();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return dinnerDiaryList;
    }
    private List<FoodDiary> getAllSnacksDiaryInfo(SnacksDiaryCallback snacksDiarycallback) {
        List<FoodDiary> snacksDiaryList = new ArrayList<FoodDiary>();
        snacksCalorieProgress = 0;
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userID).child("mealsDiary").child(date).child("Snacks");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String snacksFoodTitle, snacksServingUnit;
                double snacksFoodCalorie, snacksFoodProtein, snacksFoodFat, snacksFoodCarb, snacksServingNumber, snacksServingSize;
                for (DataSnapshot snacksSnapshot : snapshot.getChildren()){
                    snacksFoodTitle = snacksSnapshot.getKey();
                    snacksFoodCalorie = snacksSnapshot.child("foodCalorie").getValue(Double.class);
                    snacksFoodProtein = snacksSnapshot.child("foodProtein").getValue(Double.class);
                    snacksFoodFat = snacksSnapshot.child("foodFat").getValue(Double.class);
                    snacksFoodCarb = snacksSnapshot.child("foodCarb").getValue(Double.class);
                    snacksServingNumber = snacksSnapshot.child("servingNumber").getValue(Double.class);
                    snacksServingSize = snacksSnapshot.child("servingSize").getValue(Double.class);
                    snacksServingUnit = snacksSnapshot.child("servingUnit").getValue(String.class);
                    snacksDiaryList.add(new FoodDiary(snacksFoodTitle, snacksFoodCalorie, snacksFoodProtein, snacksFoodFat, snacksFoodCarb, snacksServingNumber, snacksServingSize, snacksServingUnit));
                    snacksCalorieProgress = snacksCalorieProgress + snacksFoodCalorie;
                    snacksProteinProgress =  snacksProteinProgress +  snacksFoodProtein;
                    snacksFatProgress =  snacksFatProgress +  snacksFoodFat;
                    snacksCarbProgress =  snacksCarbProgress +  snacksFoodCarb;
                }
                snacksDiarycallback.onCallback(snacksDiaryList);
                tvSnacksNettCalorie.setText(df.format(snacksCalorieProgress) + " Cal");
                //databaseNutritionProgress.setValue(breakfastCalorieProgress + lunchCalorieProgress + dinnerCalorieProgress + snacksCalorieProgress);
                saveProgress();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return snacksDiaryList;
    }
    private void saveProgress() {
        overallCalorieProgress = breakfastCalorieProgress + lunchCalorieProgress + dinnerCalorieProgress + snacksCalorieProgress;
        overallProteinProgress = breakfastProteinProgress + lunchProteinProgress + dinnerProteinProgress + snacksProteinProgress;
        overallFatProgress = breakfastFatProgress + lunchFatProgress + dinnerFatProgress + snacksFatProgress;
        overallCarbProgress = breakfastCarbProgress + lunchCarbProgress + dinnerCarbProgress + snacksCarbProgress;

        databaseCalorieProgress.setValue(overallCalorieProgress);
        databaseProteinProgress.setValue(overallProteinProgress);
        databaseFatProgress.setValue(overallFatProgress);
        databaseCarbProgress.setValue(overallCarbProgress);
    }
}

