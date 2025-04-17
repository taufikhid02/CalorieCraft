package com.example.caloriecraft.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caloriecraft.EditFoodDetailsActivity;
import com.example.caloriecraft.Objects.FoodDiary;
import com.example.caloriecraft.Objects.ServingUnit;
import com.example.caloriecraft.PortionGuideDetailsActivity;
import com.example.caloriecraft.R;

import java.text.DecimalFormat;
import java.util.List;

public class ServingUnitRecyclerViewAdapter extends RecyclerView.Adapter<ServingUnitRecyclerViewAdapter.ServingUnitViewHolder> {

    List<ServingUnit> servingUnitList;
    Context context;
    DecimalFormat df = new DecimalFormat("0.00");

    public ServingUnitRecyclerViewAdapter(Context context, List<ServingUnit> servingUnitList){
        this.context = context;
        this.servingUnitList = servingUnitList;
    }

    @NonNull
    @Override
    public ServingUnitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View portion_guide_row = LayoutInflater.from(parent.getContext()).inflate(R.layout.portion_guide_row, parent, false);

        ServingUnitViewHolder portionGuideVH = new ServingUnitViewHolder(portion_guide_row);
        return portionGuideVH;
    }

    @Override
    public void onBindViewHolder(@NonNull ServingUnitViewHolder holder, int position) {
        holder.tvServingUnitDetails.setText(servingUnitList.get(position).getServingUnit());
        holder.imgServingUnitPicture.setImageResource(servingUnitList.get(position).getImgGuidePicture());
    }

    @Override
    public int getItemCount() {
        return servingUnitList.size();
    }

    public class ServingUnitViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvServingUnitDetails;
        ImageView imgServingUnitPicture;

        public ServingUnitViewHolder(@NonNull View itemView) {
            super(itemView);
            tvServingUnitDetails = itemView.findViewById(R.id.tv_serving_unit_details);
            imgServingUnitPicture = itemView.findViewById(R.id.img_serving_unit_picture);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), PortionGuideDetailsActivity.class);
            intent.putExtra("Serving Unit Detail", servingUnitList.get(getBindingAdapterPosition()).getServingUnit());
            intent.putExtra("Serving Unit Picture", servingUnitList.get(getBindingAdapterPosition()).getImgGuidePicture());
            view.getContext().startActivity(intent);
        }
    }
}

