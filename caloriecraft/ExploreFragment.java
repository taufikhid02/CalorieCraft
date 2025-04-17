package com.example.caloriecraft;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.caloriecraft.Adapters.ExploreRecyclerViewAdapter;
import com.example.caloriecraft.Adapters.ServingUnitRecyclerViewAdapter;
import com.example.caloriecraft.Objects.Explore;
import com.example.caloriecraft.Objects.ServingUnit;

import java.util.ArrayList;
import java.util.List;

public class ExploreFragment extends Fragment {

    LinearLayoutManager linearLayoutManager;
    RecyclerView rvExplore;
    ExploreRecyclerViewAdapter exploreRecylerViewAdapter;
    List<Explore> exploreList;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_explore, container, false);
        rvExplore = view.findViewById(R.id.rv_explore_list_explore);
        linearLayoutManager = new LinearLayoutManager(getContext());
        //rvExplore.setLayoutManager(linearLayoutManager);
        rvExplore.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        rvExplore.setHasFixedSize(true);
        exploreRecylerViewAdapter = new ExploreRecyclerViewAdapter(getContext(), exploreList);
        rvExplore.setAdapter(exploreRecylerViewAdapter);
        updateRecyclerView();
        return view;
    }

    private void updateRecyclerView() {
        exploreList = getExploreList();
        exploreRecylerViewAdapter = new ExploreRecyclerViewAdapter(getContext(), exploreList);
        rvExplore.setAdapter(exploreRecylerViewAdapter);
    }

    private List<Explore> getExploreList() {
        List<Explore> exploreList = new ArrayList<>();
        exploreList.add(new Explore("BMI\nAwareness", getContext().getString(R.string.explore_bmi_awareness_description), "Read More", R.drawable.bg_bmi));
        exploreList.add(new Explore("Quick\nCalculator", getContext().getString(R.string.explore_quick_calculator_description), "Calculate Now", R.drawable.bg_quick_calculator));
        exploreList.add(new Explore("Portion\nGuide", getContext().getString(R.string.explore_portion_guide_description), "See Details", R.drawable.bg_portion_guide));
        return exploreList;
    }
}