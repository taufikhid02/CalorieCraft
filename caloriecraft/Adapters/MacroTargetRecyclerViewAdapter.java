package com.example.caloriecraft.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caloriecraft.MacroTargetActivity;
import com.example.caloriecraft.MainActivity;
import com.example.caloriecraft.Objects.MacroTarget;
import com.example.caloriecraft.Objects.UserBodyStatsInformation;
import com.example.caloriecraft.PortionGuideDetailsActivity;
import com.example.caloriecraft.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.collection.LLRBNode;

import java.text.DecimalFormat;
import java.util.List;

public class MacroTargetRecyclerViewAdapter extends RecyclerView.Adapter<MacroTargetRecyclerViewAdapter.MacroTargetViewHolder> {

    List<MacroTarget> macroTargetList;
    Context context;
    DecimalFormat df = new DecimalFormat("0.00");
    int proteinGoal, fatGoal, carbGoal;
    String sex, physicalLevel;

    int age, weightGoal, calorieTarget;
    double height, weight, bmi;
    DatabaseReference databaseReference, databaseUserList;

    public MacroTargetRecyclerViewAdapter(Context context, List<MacroTarget> macroTargetList){
        this.context = context;
        this.macroTargetList = macroTargetList;
    }

    @NonNull
    @Override
    public MacroTargetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View macro_target_row = LayoutInflater.from(parent.getContext()).inflate(R.layout.macro_target_row, parent, false);

        MacroTargetViewHolder macroTargetVH = new MacroTargetViewHolder(macro_target_row);
        return macroTargetVH;
    }

    @Override
    public void onBindViewHolder(@NonNull MacroTargetViewHolder holder, int position) {
        holder.cvMacroTarget.setCardBackgroundColor(Color.parseColor(macroTargetList.get(position).getCardViewColor()));
        holder.tvMacroPlan.setText(macroTargetList.get(position).getMacroPlan());
        holder.tvProteinGoal.setText(macroTargetList.get(position).getProteinGoal()+"g");
        holder.tvFatGoal.setText(macroTargetList.get(position).getFatGoal()+"g");
        holder.tvCarbGoal.setText(macroTargetList.get(position).getCarbGoal()+"g");
    }

    @Override
    public int getItemCount() {
        return macroTargetList.size();
    }

    public class MacroTargetViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvMacroPlan, tvProteinGoal, tvFatGoal, tvCarbGoal;
        CardView cvMacroTarget;

        public MacroTargetViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMacroPlan = itemView.findViewById(R.id.tv_macro_plan_macro_target);
            tvProteinGoal = itemView.findViewById(R.id.tv_proteinGoal_macro_target);
            tvFatGoal = itemView.findViewById(R.id.tv_fatGoal_macro_target);
            tvCarbGoal = itemView.findViewById(R.id.tv_carbGoal_macro_target);
            cvMacroTarget = itemView.findViewById(R.id.cv_macro_target);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setMessage("Are you sure with the choice?").setTitle("Confirm selection").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    sex = macroTargetList.get(getBindingAdapterPosition()).getSex();
                    age = macroTargetList.get(getBindingAdapterPosition()).getAge();
                    height = macroTargetList.get(getBindingAdapterPosition()).getHeight();
                    weight = macroTargetList.get(getBindingAdapterPosition()).getWeight();
                    physicalLevel = macroTargetList.get(getBindingAdapterPosition()).getPhysicalLevel();
                    calorieTarget = macroTargetList.get(getBindingAdapterPosition()).getCalorieTarget();
                    bmi = macroTargetList.get(getBindingAdapterPosition()).getBmi();
                    weightGoal = macroTargetList.get(getBindingAdapterPosition()).getWeightGoal();
                    proteinGoal = macroTargetList.get(getBindingAdapterPosition()).getProteinGoal();
                    fatGoal = macroTargetList.get(getBindingAdapterPosition()).getFatGoal();
                    carbGoal = macroTargetList.get(getBindingAdapterPosition()).getCarbGoal();
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

        private void saveProfile() {
            String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
            databaseReference = FirebaseDatabase.getInstance().getReference("Users");
            databaseUserList = databaseReference.child(userID);
            UserBodyStatsInformation userBodyStatsInformation = new UserBodyStatsInformation(sex, age, height, weight, physicalLevel, calorieTarget, bmi, weightGoal, proteinGoal, fatGoal, carbGoal);
            databaseUserList.child("bodyStatistics").setValue(userBodyStatsInformation);
            Intent goToDashboard = new Intent(itemView.getContext(), MainActivity.class);
            itemView.getContext().startActivity(goToDashboard);
        }
    }
}

