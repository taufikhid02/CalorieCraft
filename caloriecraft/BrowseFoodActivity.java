package com.example.caloriecraft;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.caloriecraft.Interfaces.NutritionixSearchInstantApi;
import com.example.caloriecraft.Objects.SearchResponse;
import com.example.caloriecraft.Objects.SearchResults;
import com.example.caloriecraft.Adapters.SearchResultsRecyclerViewAdapter;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BrowseFoodActivity extends AppCompatActivity {

    TextView tvSearchResultCount;
    //EditText etSearchFood;
    SearchView etSearchFood;
    CardView cvFoodItem1;
    //Button btnScanBarcode, btnQuickAdd;
    Toolbar toolbarBrowseFood;
    //TextInputLayout tfSearchFood;
    LinearLayoutManager linearLayoutManager;
    NutritionixSearchInstantApi api;
    Button btnScanBarcode, btnQuickAdd;
    int searchResultCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_food);

        RecyclerView searchResultsRecyclerView = findViewById(R.id.rv_search_results_browse_food);
        linearLayoutManager = new LinearLayoutManager(BrowseFoodActivity.this);
        searchResultsRecyclerView.setLayoutManager(linearLayoutManager);
        searchResultsRecyclerView.setHasFixedSize(true);
        btnScanBarcode = findViewById(R.id.btn_scan_barcode_browse_food);
        btnQuickAdd = findViewById(R.id.btn_quick_add_browse_food);
        etSearchFood = findViewById(R.id.et_search_food_browse_food);
        etSearchFood.clearFocus();
        tvSearchResultCount = findViewById(R.id.tv_search_result_count_browse_food);
        toolbarBrowseFood = findViewById(R.id.tb_browse_food);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://trackapi.nutritionix.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(NutritionixSearchInstantApi.class);

        /*etSearchFood.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH){
                    searchInstant(etSearchFood.getText().toString());
                }
                return false;
            }
        });*/

        etSearchFood.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchInstant(etSearchFood.getQuery().toString());
                searchResultsRecyclerView.setVisibility(View.VISIBLE);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        setSupportActionBar(toolbarBrowseFood);
        ActionBar myActionBar = getSupportActionBar();
        myActionBar.setDisplayShowHomeEnabled(true);
        myActionBar.setDisplayShowHomeEnabled(true);
        toolbarBrowseFood.setNavigationIcon(R.drawable.baseline_arrow_back_24_white);
        toolbarBrowseFood.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnScanBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToBarcodeScanner = new Intent(BrowseFoodActivity.this, BarcodeScannerActivity.class);
                startActivity(goToBarcodeScanner);
            }
        });
        btnQuickAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToQuickAdd = new Intent(BrowseFoodActivity.this, QuickAddActivity.class);
                startActivity(goToQuickAdd);
            }
        });
        tvSearchResultCount.setVisibility(View.GONE);
    }

    private void searchInstant(String query) {
        searchResultCount = 0;
        Call<SearchResponse> call = api.searchInstant(query);
        call.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    SearchResponse searchResponse = response.body();
                    List<SearchResults> searchResultsList = new ArrayList<>();
                    for (SearchResponse.Common item : searchResponse.getCommon()){
                        searchResultsList.add(new SearchResults(item.getFood_name(), item.getTag_name()));
                        searchResultCount ++;
                    }
                    updateRecyclerView(searchResultsList);
                    if (searchResultCount > 0){
                        tvSearchResultCount.setText("Search Result(s): " + searchResultCount);
                    } else {
                        tvSearchResultCount.setText("No match found. Please try another keyword");
                    }
                    tvSearchResultCount.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                // Handle failure
                Log.e("API", "Network Error :: ", t);
            }
        });
    }

    private void updateRecyclerView(List<SearchResults> searchResultsList) {
        SearchResultsRecyclerViewAdapter adapter = new SearchResultsRecyclerViewAdapter(BrowseFoodActivity.this, searchResultsList);
        RecyclerView recyclerView = findViewById(R.id.rv_search_results_browse_food);
        recyclerView.setLayoutManager(new LinearLayoutManager(BrowseFoodActivity.this));
        recyclerView.setAdapter(adapter);
    }

    /*private List<SearchResults> getSearchResultsList() {
        List<SearchResults> foodItemList = new ArrayList<SearchResults>();
        foodItemList.add(new SearchResults("Chef Instant Noodles (Mamee)", 400));
        foodItemList.add(new SearchResults("Chef Instant Noodles, Lontong", 550));
        foodItemList.add(new SearchResults("Chef, Instant Noodles", 450));
        foodItemList.add(new SearchResults("Instant Noodles", 580));
        foodItemList.add(new SearchResults("Chef Instant Noodles (Maggi)", 400));
        foodItemList.add(new SearchResults("Chef Instant Noodles, Asam Laksa", 550));
        foodItemList.add(new SearchResults("Chef, Instant Mee", 450));
        foodItemList.add(new SearchResults("Regular Noodles", 580));
        foodItemList.add(new SearchResults("Chef Instant Noodles (Ayam)", 400));
        foodItemList.add(new SearchResults("Chef Instant Noodles, Kari", 550));
        foodItemList.add(new SearchResults("Chef, Instant Mee Soup", 450));
        foodItemList.add(new SearchResults("Chicken Noodles Soup", 580));
        return foodItemList;
    }*/
}

//call API when search food
/*etSearchFood.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH){
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("https://trackapi.nutritionix.com/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    NutritionixApi api = retrofit.create(NutritionixApi.class);
                    String searchInput = "{\"query\":\""+etSearchFood.getText().toString()+"\"}";
                    RequestBody body = RequestBody.create(MediaType.parse("application/json"), searchInput);
                    Call<NutritionResponse> call = api.getNutritionInfo(body);

                    call.enqueue(new Callback<NutritionResponse>() {
                        @Override
                        public void onResponse(Call<NutritionResponse> call, Response<NutritionResponse> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                // Process the response, e.g., update the UI
                                NutritionResponse nutritionResponse = response.body();
                                for (NutritionResponse.Food food : nutritionResponse.getFoods()) {
                                    //Log.d("API", food.getFoodName() + ": " + food.getCalories() + " calories");
                                    List<SearchResults> searchResultsList = new ArrayList<SearchResults>();
                                    searchResultsList.add(new SearchResults(food.getFoodName(), (int)food.getCalories()));
                                    SearchResultsRecyclerViewAdapter searchResultsRecyclerViewAdapter = new SearchResultsRecyclerViewAdapter(BrowseFoodActivity.this, searchResultsList);
                                    recyclerView.setAdapter(searchResultsRecyclerViewAdapter);

                                }
                            } else {
                                // Handle request errors
                                Log.e("API", "Request Error :: " + response.errorBody());
                            }
                        }

                        @Override
                        public void onFailure(Call<NutritionResponse> call, Throwable t) {
                            // Handle failure, e.g., no internet connection
                            Log.e("API", "Network Error :: ", t);
                        }
                    });
                }
                return false;
            }
        });*/