package com.example.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    interface CategoryIsClickedInteface{
        void categoryIsClick(Category category);
    }
    private ArrayList<Category> categories;
    private CategoryIsClickedInteface categoryIsClickedInteface;

    public RecyclerViewAdapter(ArrayList<Category> categories, CategoryIsClickedInteface categoryIsClickedInteface) {
        this.categories = categories;
        this.categoryIsClickedInteface = categoryIsClickedInteface;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // ethe apa view ch pura content_view inflate krta
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.content_view , parent, false);
        // ethe apa recyclerViewHolder ch oh view add krta
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        //after passing the view to holder ... here we assign the values to text of the holder
        holder.getTextView().setText(Integer.toString(position + 1));
        holder.getTextView2().setText(categories.get(position).getNames());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryIsClickedInteface.categoryIsClick(categories.get(position));

            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void addCategory(Category category){
        categories.add(category);
        notifyItemInserted(categories.size() - 1);
    }
}
