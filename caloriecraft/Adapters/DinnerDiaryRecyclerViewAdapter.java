package com.example.caloriecraft.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caloriecraft.EditFoodDetailsActivity;
import com.example.caloriecraft.Objects.FoodDiary;
import com.example.caloriecraft.R;

import java.text.DecimalFormat;
import java.util.List;

public class DinnerDiaryRecyclerViewAdapter extends RecyclerView.Adapter<DinnerDiaryRecyclerViewAdapter.DinnerViewHolder> {

    List<FoodDiary> dinnerDiaryList;
    Context context;
    DecimalFormat df = new DecimalFormat("0.00");

    public DinnerDiaryRecyclerViewAdapter(Context context, List<FoodDiary> dinnerDiaryList){
        this.context = context;
        this.dinnerDiaryList = dinnerDiaryList;
    }

    @NonNull
    @Override
    public DinnerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View Dinner_diary_row = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_diary_row, parent, false);

        DinnerViewHolder dinnerVH = new DinnerViewHolder(Dinner_diary_row);
        return dinnerVH;
    }

    @Override
    public void onBindViewHolder(@NonNull DinnerViewHolder holder, int position) {
        holder.tvFoodTitle.setText(dinnerDiaryList.get(position).getFoodTitle());
        holder.tvFoodCalorie.setText(String.valueOf(df.format(dinnerDiaryList.get(position).getFoodCalorie())) + " Cal");
    }

    @Override
    public int getItemCount() {
        return dinnerDiaryList.size();
    }

    public class DinnerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvFoodTitle, tvFoodCalorie;

        public DinnerViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFoodTitle = itemView.findViewById(R.id.tv_food_title_food_diary);
            tvFoodCalorie = itemView.findViewById(R.id.tv_food_calorie_food_diary);

            //one click listener
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            //Toast.makeText(view.getContext(), dinnerDiaryList.get(getBindingAdapterPosition()).getFoodTitle(), Toast.LENGTH_SHORT).show();

            //To be sent to EditFoodDetails Activity
            Intent intent = new Intent(view.getContext(), EditFoodDetailsActivity.class);
            intent.putExtra("Food Title", dinnerDiaryList.get(getBindingAdapterPosition()).getFoodTitle());
            intent.putExtra("Meal Time", "Dinner");
            intent.putExtra("Food Calorie", dinnerDiaryList.get(getBindingAdapterPosition()).getFoodCalorie());
            intent.putExtra("Food Protein", dinnerDiaryList.get(getBindingAdapterPosition()).getFoodProtein());
            intent.putExtra("Food Fat", dinnerDiaryList.get(getBindingAdapterPosition()).getFoodFat());
            intent.putExtra("Food Carb", dinnerDiaryList.get(getBindingAdapterPosition()).getFoodCarb());
            intent.putExtra("Serving Number", dinnerDiaryList.get(getBindingAdapterPosition()).getServingNumber());
            intent.putExtra("Serving Size", dinnerDiaryList.get(getBindingAdapterPosition()).getServingSize());
            intent.putExtra("Serving Unit", dinnerDiaryList.get(getBindingAdapterPosition()).getServingUnit());
            view.getContext().startActivity(intent);
        }
    }
}

