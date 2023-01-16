package com.example.recyclerview;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

public class CategoryManager {

    private Context mContext;

    public CategoryManager(Context mContext) {
        this.mContext = mContext;
    }

    public void saveCategory(@NonNull Category category){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        HashSet itemHashSet = new HashSet(category.getList());
        editor.putStringSet(category.getNames(), itemHashSet);

        editor.apply();
    }

    public ArrayList<Category> retrieveCategories(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        Map<String, ?> data = sharedPreferences.getAll();

        ArrayList<Category> categories = new ArrayList<>();

        for(Map.Entry<String, ?> entry : data.entrySet()){
            Category category = new Category(entry.getKey(), new ArrayList<String>((HashSet) entry.getValue()));
            categories.add(category);
        }
        return categories;
    }
}
