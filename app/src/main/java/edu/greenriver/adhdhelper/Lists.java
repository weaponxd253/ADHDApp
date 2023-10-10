package edu.greenriver.adhdhelper;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;


public class Lists extends Fragment {

    RecyclerView recyclerView;
    TextView textView;
    ArrayList<String> arrayList;
    MainAdapter adapter;
    View currentView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        currentView = inflater.inflate(R.layout.fragment_lists, container, false);
        initialize();
        return currentView;
    }

    private void initialize(){
        arrayList = new ArrayList<>();
        recyclerView = currentView.findViewById(R.id.recycler_view);
        textView = currentView.findViewById(R.id.tv_empty);
        arrayList.addAll(Arrays.asList("A","B","C","D","E","F","G","H","I","J","K","L"));
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        adapter = new MainAdapter(this.getActivity(),arrayList,textView);
        recyclerView.setAdapter(adapter);
    }
}