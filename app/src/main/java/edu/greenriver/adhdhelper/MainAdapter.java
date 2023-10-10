package edu.greenriver.adhdhelper;

import android.app.Activity;
import android.graphics.Color;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    Activity activity;
    ArrayList<String> arrayList;
    TextView textViewEmpty;
    MainViewModel mainViewModel;
    boolean isEnable = false;
    boolean isSelectAll = false;
    ArrayList<String> deleteList = new ArrayList<>();
    public MainAdapter(Activity activity, ArrayList<String> arrayList, TextView textView) {
        this.activity = activity;
        this.arrayList = arrayList;
        this.textViewEmpty = textView;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false);
        mainViewModel = new ViewModelProvider((FragmentActivity) activity).get(MainViewModel.class);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.ViewHolder holder, int position) {
        holder.textView.setText(arrayList.get(position));
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (!isEnable){
                    ActionMode.Callback callback = new ActionMode.Callback() {
                        @Override
                        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                            MenuInflater menuInflater = actionMode.getMenuInflater();
                            menuInflater.inflate(R.menu.menu, menu);
                            return true;
                        }

                        @Override
                        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                            isEnable = true;
                            ClickItem(holder);
                            mainViewModel.getText().observe((LifecycleOwner) activity,
                                    new Observer<String>() {
                                        @Override
                                        public void onChanged(String s) {
                                            actionMode.setTitle(String.format("%s Selected", s));
                                        }
                                    });
                            return true;
                        }

                        @Override
                        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                            int id = menuItem.getItemId();
                            switch (id){
                                case R.id.menu_delete:
                                    for (String s: deleteList){
                                        arrayList.remove(s);
                                    }
                                    if (arrayList.size() == 0){
                                        textViewEmpty.setVisibility(View.VISIBLE);
                                    }
                                    actionMode.finish();
                                    break;

                                case R.id.menu_select_all:
                                    if (deleteList.size() == arrayList.size()){
                                        isSelectAll = false;
                                        deleteList.clear();
                                    }
                                    else {
                                        isSelectAll = true;
                                        deleteList.clear();
                                        deleteList.addAll(arrayList);
                                    }
                                    mainViewModel.setText(String.valueOf(deleteList.size()));
                                    notifyDataSetChanged();
                                    break;
                            }
                            return true;
                        }

                        @Override
                        public void onDestroyActionMode(ActionMode actionMode) {
                            isEnable = false;
                            isSelectAll = false;
                            deleteList.clear();
                            notifyDataSetChanged();
                        }
                    };
                    ((AppCompatActivity) view.getContext()).startActionMode(callback);
                }
                else {
                    ClickItem(holder);
                }
                return true;
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEnable){
                    ClickItem(holder);
                }
                else {
                    Toast.makeText(activity, "You Clicked" + arrayList.get(holder.getAdapterPosition()), Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (isSelectAll){
            holder.checkbox.setVisibility(View.VISIBLE);
            holder.itemView.setBackgroundColor(Color.GRAY);
        }
        else {
            holder.checkbox.setVisibility(View.GONE);
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    private void ClickItem(ViewHolder holder){
        String s = arrayList.get(holder.getAdapterPosition());

        if (holder.checkbox.getVisibility() == View.GONE){
            holder.checkbox.setVisibility(View.VISIBLE);
            holder.itemView.setBackgroundColor(Color.GRAY);
            deleteList.add(s);
        }
        else {
            holder.checkbox.setVisibility(View.GONE);
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
            deleteList.remove(s);
        }

        mainViewModel.setText(String.valueOf(deleteList.size()));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        ImageView checkbox;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            textView = itemView.findViewById(R.id.text_view);
            checkbox = itemView.findViewById(R.id.check_box);
        }
    }
}
