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

public class SnacksDiaryRecyclerViewAdapter extends RecyclerView.Adapter<SnacksDiaryRecyclerViewAdapter.SnacksViewHolder> {

    List<FoodDiary> snacksDiaryList;
    Context context;
    String foodTitle;
    DecimalFormat df = new DecimalFormat("0.00");

    public SnacksDiaryRecyclerViewAdapter(Context context, List<FoodDiary> snacksDiaryList){
        this.context = context;
        this.snacksDiaryList = snacksDiaryList;
    }

    @NonNull
    @Override
    public SnacksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View snacks_diary_row = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_diary_row, parent, false);

        SnacksViewHolder snacksVH = new SnacksViewHolder(snacks_diary_row);
        return snacksVH;
    }

    @Override
    public void onBindViewHolder(@NonNull SnacksViewHolder holder, int position) {

        foodTitle = snacksDiaryList.get(position).getFoodTitle();
        holder.tvFoodTitle.setText(foodTitle);
        holder.tvFoodCalorie.setText(String.valueOf(df.format(snacksDiaryList.get(position).getFoodCalorie())) + " Cal");
    }

    @Override
    public int getItemCount() {
        return snacksDiaryList.size();
    }

    public class SnacksViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvFoodTitle, tvFoodCalorie;

        public SnacksViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFoodTitle = itemView.findViewById(R.id.tv_food_title_food_diary);
            tvFoodCalorie = itemView.findViewById(R.id.tv_food_calorie_food_diary);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            //Toast.makeText(view.getContext(), snacksDiaryList.get(getBindingAdapterPosition()).getFoodTitle(), Toast.LENGTH_SHORT).show();

            //To be sent to EditFoodDetails Activity
            Intent intent = new Intent(view.getContext(), EditFoodDetailsActivity.class);
            intent.putExtra("Food Title", snacksDiaryList.get(getBindingAdapterPosition()).getFoodTitle());
            intent.putExtra("Meal Time", "Snacks");
            intent.putExtra("Food Calorie", snacksDiaryList.get(getBindingAdapterPosition()).getFoodCalorie());
            intent.putExtra("Food Protein", snacksDiaryList.get(getBindingAdapterPosition()).getFoodProtein());
            intent.putExtra("Food Fat", snacksDiaryList.get(getBindingAdapterPosition()).getFoodFat());
            intent.putExtra("Food Carb", snacksDiaryList.get(getBindingAdapterPosition()).getFoodCarb());
            intent.putExtra("Serving Number", snacksDiaryList.get(getBindingAdapterPosition()).getServingNumber());
            intent.putExtra("Serving Size", snacksDiaryList.get(getBindingAdapterPosition()).getServingSize());
            intent.putExtra("Serving Unit", snacksDiaryList.get(getBindingAdapterPosition()).getServingUnit());
            view.getContext().startActivity(intent);
        }

    }
}
