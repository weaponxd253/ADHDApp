package edu.greenriver.adhdhelper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView textView;
    ArrayList<String> arrayList;
    MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
        arrayList.addAll(Arrays.asList("A","B","C","D","E"));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MainAdapter(this,arrayList,textView);
        recyclerView.setAdapter(adapter);
    }

    private void initialize(){
        arrayList = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler_view);
        textView = findViewById(R.id.tv_empty);
    }
}