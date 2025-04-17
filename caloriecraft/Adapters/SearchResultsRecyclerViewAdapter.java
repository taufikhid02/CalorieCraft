package com.example.caloriecraft.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caloriecraft.FoodDetailsActivity;
import com.example.caloriecraft.Interfaces.NutritionixGetNutritionInfoApi;
import com.example.caloriecraft.Objects.NutritionResponse;
import com.example.caloriecraft.Objects.SearchResults;
import com.example.caloriecraft.Objects.TitleCaseConverter;
import com.example.caloriecraft.R;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchResultsRecyclerViewAdapter extends RecyclerView.Adapter<SearchResultsRecyclerViewAdapter.SearchResultsViewHolder>{

    public List<SearchResults> searchResultsList;
    public Context context;
    TitleCaseConverter titleCaseConverter = new TitleCaseConverter();
    String foodTitle_TitleCase, foodTagName_TitleCase;

    public SearchResultsRecyclerViewAdapter(Context context, List<SearchResults> searchResultsList) {
        this.context = context;
        this.searchResultsList = searchResultsList;
    }

    @NonNull
    @Override
    public SearchResultsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View searchResults_row = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_results_row, null);
        SearchResultsViewHolder searchResultsVH = new SearchResultsViewHolder(searchResults_row);
        return searchResultsVH;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultsViewHolder holder, int position) {
        foodTitle_TitleCase = titleCaseConverter.convertToTitleCaseIteratingChars(searchResultsList.get(position).getFoodItemTitle());
        foodTagName_TitleCase = titleCaseConverter.convertToTitleCaseIteratingChars(searchResultsList.get(position).getFoodItemTagName());
        holder.tvFoodTitle.setText(foodTitle_TitleCase);
        holder.tvFoodTagName.setText(foodTagName_TitleCase);
    }

    @Override
    public int getItemCount() {
        return searchResultsList.size();
    }

    public class SearchResultsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView tvFoodTitle, tvFoodTagName;


        public SearchResultsViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFoodTitle = itemView.findViewById(R.id.tv_food_title_browse_food);
            tvFoodTagName = itemView.findViewById(R.id.tv_food_calorie_browse_food);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent goToFoodDetails = new Intent(view.getContext(), FoodDetailsActivity.class);
            Bundle bundle = new Bundle();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://trackapi.nutritionix.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            NutritionixGetNutritionInfoApi api = retrofit.create(NutritionixGetNutritionInfoApi.class);
            String searchInput = "{\"query\":\""+searchResultsList.get(getBindingAdapterPosition()).getFoodItemTitle()+"\"}";
            RequestBody body = RequestBody.create(MediaType.parse("application/json"), searchInput);
            Call<NutritionResponse> call = api.getNutritionInfo(body);

            call.enqueue(new Callback<NutritionResponse>() {
                @Override
                public void onResponse(Call<NutritionResponse> call, Response<NutritionResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        // Process the response, e.g., update the UI
                        NutritionResponse nutritionResponse = response.body();
                        NutritionResponse.Food food = nutritionResponse.getFoods().get(0);
                            //Log.d("API", food.getFoodName() + ": " + food.getCalories() + " calories");
                        bundle.putString("Food Title", titleCaseConverter.convertToTitleCaseIteratingChars(food.getFoodName()));
                        bundle.putString("Food Brand", "-");
                        bundle.putDouble("Food Calorie", food.getCalories());
                        bundle.putDouble("Food Protein", food.getProteins());
                        bundle.putDouble("Food Fat", food.getFats());
                        bundle.putDouble("Food Carb", food.getCarbohydrates());
                        bundle.putString("Serving Unit", food.getServingUnit());
                        bundle.putString("Serving Size", food.getServingQty());
                        goToFoodDetails.putExtras(bundle);
                        //goToFoodDetails.putExtra("Food Title", searchResultsList.get(getBindingAdapterPosition()).getFoodItemTitle());
                        //goToFoodDetails.putExtra("Food Calorie", searchResultsList.get(getBindingAdapterPosition()).getFoodItemCalorie());
                        view.getContext().startActivity(goToFoodDetails);


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


            /*bundle.putString("Food Title", searchResultsList.get(getBindingAdapterPosition()).getFoodItemTitle());
            bundle.putString("Food Calorie", searchResultsList.get(getBindingAdapterPosition()).getFoodItemCalorie());
            goToFoodDetails.putExtras(bundle);
            //goToFoodDetails.putExtra("Food Title", searchResultsList.get(getBindingAdapterPosition()).getFoodItemTitle());
            //goToFoodDetails.putExtra("Food Calorie", searchResultsList.get(getBindingAdapterPosition()).getFoodItemCalorie());
            view.getContext().startActivity(goToFoodDetails);*/

        }

    }

}
