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

public class LunchDiaryRecyclerViewAdapter extends RecyclerView.Adapter<LunchDiaryRecyclerViewAdapter.LunchViewHolder> {

    List<FoodDiary> lunchDiaryList;
    Context context;
    DecimalFormat df = new DecimalFormat("0.00");

    public LunchDiaryRecyclerViewAdapter(Context context, List<FoodDiary> lunchDiaryList){
        this.context = context;
        this.lunchDiaryList = lunchDiaryList;
    }

    @NonNull
    @Override
    public LunchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View lunch_diary_row = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_diary_row, parent, false);

        LunchViewHolder lunchVH = new LunchViewHolder(lunch_diary_row);
        return lunchVH;
    }

    @Override
    public void onBindViewHolder(@NonNull LunchViewHolder holder, int position) {
        holder.tvFoodTitle.setText(lunchDiaryList.get(position).getFoodTitle());
        holder.tvFoodCalorie.setText(String.valueOf(df.format(lunchDiaryList.get(position).getFoodCalorie())) + " Cal");
    }

    @Override
    public int getItemCount() {
        return lunchDiaryList.size();
    }

    public class LunchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvFoodTitle, tvFoodCalorie;

        public LunchViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFoodTitle = itemView.findViewById(R.id.tv_food_title_food_diary);
            tvFoodCalorie = itemView.findViewById(R.id.tv_food_calorie_food_diary);

            //one click listener
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            //Toast.makeText(view.getContext(), lunchDiaryList.get(getBindingAdapterPosition()).getFoodTitle(), Toast.LENGTH_SHORT).show();

            //To be sent to EditFoodDetails Activity
            Intent intent = new Intent(view.getContext(), EditFoodDetailsActivity.class);
            intent.putExtra("Food Title", lunchDiaryList.get(getBindingAdapterPosition()).getFoodTitle());
            intent.putExtra("Meal Time", "Lunch");
            intent.putExtra("Food Calorie", lunchDiaryList.get(getBindingAdapterPosition()).getFoodCalorie());
            intent.putExtra("Food Protein", lunchDiaryList.get(getBindingAdapterPosition()).getFoodProtein());
            intent.putExtra("Food Fat", lunchDiaryList.get(getBindingAdapterPosition()).getFoodFat());
            intent.putExtra("Food Carb", lunchDiaryList.get(getBindingAdapterPosition()).getFoodCarb());
            intent.putExtra("Serving Number", lunchDiaryList.get(getBindingAdapterPosition()).getServingNumber());
            intent.putExtra("Serving Size", lunchDiaryList.get(getBindingAdapterPosition()).getServingSize());
            intent.putExtra("Serving Unit", lunchDiaryList.get(getBindingAdapterPosition()).getServingUnit());
            view.getContext().startActivity(intent);
        }
    }
}

