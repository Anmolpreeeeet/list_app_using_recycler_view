package com.example.recyclerview;

import static com.example.recyclerview.R.id.items_recycler_view;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class CategoryItemsFragment extends Fragment {

    private RecyclerView itemsRecyclerView;
    Category category;
    private static final String CATEGORY_ARGS = "categoryargs";
    public CategoryItemsFragment() {
        // Required empty public constructor
    }

    public static CategoryItemsFragment newInstance(Category category) {
        CategoryItemsFragment categoryItemsFragment = new CategoryItemsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(CATEGORY_ARGS, category);
        categoryItemsFragment.setArguments(bundle);
        return categoryItemsFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null){
            category = (Category) getArguments().getSerializable(CATEGORY_ARGS);
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_items, container, false);
        if(view != null){

            itemsRecyclerView = view.findViewById(R.id.items_recycler_view);
            itemsRecyclerView.setAdapter(new ItemsRecyclerAdapter(category));
            itemsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }
        return view;
    }
    public void addItemToCategory(String item){
        category.getList().add(item);
        ItemsRecyclerAdapter itemsRecyclerAdapter = (ItemsRecyclerAdapter) itemsRecyclerView.getAdapter();
        itemsRecyclerAdapter.setCategory(category);
        itemsRecyclerAdapter.notifyDataSetChanged();
    }
}