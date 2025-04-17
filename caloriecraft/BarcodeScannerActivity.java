package com.example.caloriecraft;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.caloriecraft.Interfaces.NutritionixGetItemInfoApi;
import com.example.caloriecraft.Objects.NutritionResponse;
import com.example.caloriecraft.Objects.TitleCaseConverter;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BarcodeScannerActivity extends AppCompatActivity {

    TextView tvCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_scanner);
        tvCode = findViewById(R.id.tv_code_barcode_scanner);
        IntentIntegrator intentIntegrator = new IntentIntegrator(BarcodeScannerActivity.this);
        intentIntegrator.setOrientationLocked(true);
        intentIntegrator.setPrompt("Scan a barcode");
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        intentIntegrator.initiateScan();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null){
            String contents = intentResult.getContents();
            if(contents != null){
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://trackapi.nutritionix.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                NutritionixGetItemInfoApi api = retrofit.create(NutritionixGetItemInfoApi.class);
                api.getItemInfo(contents).enqueue(new Callback<NutritionResponse>() {
                    @Override
                    public void onResponse(Call<NutritionResponse> call, Response<NutritionResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            NutritionResponse nutritionResponse = response.body();
                            NutritionResponse.Food food = nutritionResponse.getFoods().get(0);
                            Bundle bundle = new Bundle();
                            TitleCaseConverter titleCaseConverter = new TitleCaseConverter();
                            Intent goToFoodDetails = new Intent(BarcodeScannerActivity.this, FoodDetailsActivity.class);
                            bundle.putString("Food Title", titleCaseConverter.convertToTitleCaseIteratingChars(food.getFoodName()));
                            bundle.putString("Food Brand", titleCaseConverter.convertToTitleCaseIteratingChars(food.getBrandName()));
                            bundle.putDouble("Food Calorie", food.getCalories());
                            bundle.putDouble("Food Protein", food.getProteins());
                            bundle.putDouble("Food Fat", food.getFats());
                            bundle.putDouble("Food Carb", food.getCarbohydrates());
                            bundle.putString("Serving Unit", food.getServingUnit());
                            bundle.putString("Serving Size", food.getServingQty());
                            goToFoodDetails.putExtras(bundle);
                            startActivity(goToFoodDetails);
                            Toast.makeText(BarcodeScannerActivity.this, contents, Toast.LENGTH_SHORT).show();

                        }
                        else {
                            // Handle request errors
                            Toast.makeText(BarcodeScannerActivity.this, "No data", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }
                    }

                    @Override
                    public void onFailure(Call<NutritionResponse> call, Throwable t) {
                        // Handle failure, e.g., no internet connection
                        Log.e("API", "Network Error :: ", t);
                    }
                });
                tvCode.setText(contents);
            }
            else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }
    }
}