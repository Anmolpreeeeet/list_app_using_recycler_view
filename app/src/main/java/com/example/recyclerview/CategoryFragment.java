package com.example.recyclerview;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class CategoryFragment extends Fragment implements RecyclerViewAdapter.CategoryIsClickedInteface{

    private RecyclerView mRecyclerView;
    private CategoryManager mCategoryManager;

    public CategoryManager getmCategoryManager() {
        return mCategoryManager;
    }

    interface OnCategoryInteractionListener {
        void categoryIsTapped (Category category);
    }
    private OnCategoryInteractionListener listenerObject;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

         if(context instanceof OnCategoryInteractionListener){

             listenerObject = (OnCategoryInteractionListener) context;
             mCategoryManager = new CategoryManager(context);

         }else{
             throw new RuntimeException("Hey Developer. The context or activity must implement the OnCategoryInteractionListener!!");
         }
    }

    public CategoryFragment() {

    }

    public static CategoryFragment newInstance() {

        return new CategoryFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayList<Category> categories = mCategoryManager.retrieveCategories();
        if (getView() != null) {
            mRecyclerView = getView().findViewById(R.id.recycler_view);
            mRecyclerView.setAdapter(new RecyclerViewAdapter(categories, this));
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listenerObject = null;
    }
    @Override
    public void categoryIsClick(Category category) {
        listenerObject.categoryIsTapped(category);
    }
    //helpful methods
    public void addCategoryToManager(Category category){
        mCategoryManager.saveCategory(category);
        RecyclerViewAdapter recyclerViewAdapter = (RecyclerViewAdapter) mRecyclerView.getAdapter();
        recyclerViewAdapter.addCategory(category);
    }
    public void saveCategory(Category category){
        mCategoryManager.saveCategory(category);
        updateRecyclerView();
    }

    private void updateRecyclerView() {
        ArrayList<Category> categories = mCategoryManager.retrieveCategories();
        mRecyclerView.setAdapter(new RecyclerViewAdapter(categories, this));
    }
}