package com.example.caloriecraft.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caloriecraft.DiaryFragment;
import com.example.caloriecraft.EditFoodDetailsActivity;
import com.example.caloriecraft.MainPage2Activity;
import com.example.caloriecraft.Objects.Date;
import com.example.caloriecraft.Objects.FoodDiary;
import com.example.caloriecraft.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.util.List;

public class BreakfastDiaryRecyclerViewAdapter extends RecyclerView.Adapter<BreakfastDiaryRecyclerViewAdapter.BreakfastViewHolder> {

    List<FoodDiary> breakfastDiaryList;
    Context context;
    String foodTitle;
    DecimalFormat df = new DecimalFormat("0.00");

    public BreakfastDiaryRecyclerViewAdapter(Context context, List<FoodDiary> breakfastDiaryList){
        this.context = context;
        this.breakfastDiaryList = breakfastDiaryList;
    }

    @NonNull
    @Override
    public BreakfastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View breakfast_diary_row = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_diary_row, parent, false);

        BreakfastViewHolder breakfastVH = new BreakfastViewHolder(breakfast_diary_row);
        return breakfastVH;
    }

    @Override
    public void onBindViewHolder(@NonNull BreakfastViewHolder holder, int position) {

        foodTitle = breakfastDiaryList.get(position).getFoodTitle();
        holder.tvFoodTitle.setText(foodTitle);
        holder.tvFoodCalorie.setText(String.valueOf(df.format(breakfastDiaryList.get(position).getFoodCalorie())) + " Cal");
    }

    @Override
    public int getItemCount() {
        return breakfastDiaryList.size();
    }

    public class BreakfastViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvFoodTitle, tvFoodCalorie;

        public BreakfastViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFoodTitle = itemView.findViewById(R.id.tv_food_title_food_diary);
            tvFoodCalorie = itemView.findViewById(R.id.tv_food_calorie_food_diary);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            //Toast.makeText(view.getContext(), breakfastDiaryList.get(getBindingAdapterPosition()).getFoodTitle(), Toast.LENGTH_SHORT).show();

            //To be sent to EditFoodDetails Activity
            Intent intent = new Intent(view.getContext(), EditFoodDetailsActivity.class);
            intent.putExtra("Food Title", breakfastDiaryList.get(getBindingAdapterPosition()).getFoodTitle());
            intent.putExtra("Meal Time", "Breakfast");
            intent.putExtra("Food Calorie", breakfastDiaryList.get(getBindingAdapterPosition()).getFoodCalorie());
            intent.putExtra("Food Protein", breakfastDiaryList.get(getBindingAdapterPosition()).getFoodProtein());
            intent.putExtra("Food Fat", breakfastDiaryList.get(getBindingAdapterPosition()).getFoodFat());
            intent.putExtra("Food Carb", breakfastDiaryList.get(getBindingAdapterPosition()).getFoodCarb());
            intent.putExtra("Serving Number", breakfastDiaryList.get(getBindingAdapterPosition()).getServingNumber());
            intent.putExtra("Serving Size", breakfastDiaryList.get(getBindingAdapterPosition()).getServingSize());
            intent.putExtra("Serving Unit", breakfastDiaryList.get(getBindingAdapterPosition()).getServingUnit());
            view.getContext().startActivity(intent);
        }

    }
}
