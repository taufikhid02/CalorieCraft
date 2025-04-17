package com.example.caloriecraft.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caloriecraft.BMIInfoActivity;
import com.example.caloriecraft.Objects.Explore;
import com.example.caloriecraft.PortionGuideActivity;
import com.example.caloriecraft.QuickCalculatorActivity;
import com.example.caloriecraft.R;

import java.text.DecimalFormat;
import java.util.List;

public class ExploreRecyclerViewAdapter extends RecyclerView.Adapter<ExploreRecyclerViewAdapter.ExploreViewHolder> {

    List<Explore> exploreList;
    Context context;
    String title, description, goToPage;
    Intent intent;
    DecimalFormat df = new DecimalFormat("0.00");

    public ExploreRecyclerViewAdapter(Context context, List<Explore> exploreList){
        this.context = context;
        this.exploreList = exploreList;
    }

    @NonNull
    @Override
    public ExploreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View explore_row = LayoutInflater.from(parent.getContext()).inflate(R.layout.explore_row_double_column, parent, false);

        ExploreViewHolder exploreVH = new ExploreViewHolder(explore_row);
        return exploreVH;
    }

    @Override
    public void onBindViewHolder(@NonNull ExploreViewHolder holder, int position) {

        title = exploreList.get(position).getTitle();
        description = exploreList.get(position).getDescription();
        goToPage = exploreList.get(position).getGoToPage();
        holder.tvTitle.setText(title);
        holder.tvDescription.setText(description);
        holder.tvGoToPage.setText(goToPage);
        holder.imgPicture.setImageResource(exploreList.get(position).getImgExplore());
    }

    @Override
    public int getItemCount() {
        return exploreList.size();
    }

    public class ExploreViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvTitle, tvDescription, tvGoToPage;
        ImageView imgPicture;

        public ExploreViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title_double_column_explore);
            tvDescription = itemView.findViewById(R.id.tv_description_double_column_explore);
            tvGoToPage = itemView.findViewById(R.id.tv_go_to_page_double_column_explore);
            imgPicture = itemView.findViewById(R.id.img_picture_double_column_explore);
            itemView.setOnClickListener(this);
        }

        //this
        @Override
        public void onClick(View view) {
            if (getBindingAdapterPosition() == 0){
                intent = new Intent(view.getContext(), BMIInfoActivity.class);

            } else if (getBindingAdapterPosition() == 1){
                intent = new Intent(view.getContext(), QuickCalculatorActivity.class);
            } else {
                intent = new Intent(view.getContext(), PortionGuideActivity.class);
            }
            view.getContext().startActivity(intent);
        }

    }
}
