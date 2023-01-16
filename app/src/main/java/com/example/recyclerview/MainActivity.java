package com.example.recyclerview;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CategoryFragment.OnCategoryInteractionListener {


    private FloatingActionButton floatingActionButton;
    public static final String CATEGORY_OBJECT_KEY = "CATEGORY_KEY";
    public static final int MAIN_ACTIVITY_REQUEST_CODE = 1000;
    private CategoryFragment mCategoryFragment;
    private boolean isTablet = false;
    private CategoryItemsFragment mCategoryItemsFragment;
    private FrameLayout categoryItemsFragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCategoryFragment = (CategoryFragment) getSupportFragmentManager().findFragmentById(R.id.category_fragment);
        categoryItemsFragmentContainer = findViewById(R.id.category_items_fragment_container);

        isTablet = categoryItemsFragmentContainer != null;
        floatingActionButton = findViewById(R.id.floatingAction);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayCreateCategoryDilaog();
            }
        });
    }

    private void displayCreateCategoryDilaog(){
        String alertTitle = "Enter the name of the category";
        String positiveButton = "Create";
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        EditText editText = new EditText(this);
        editText.setInputType(InputType.TYPE_CLASS_TEXT);

        alertBuilder.setTitle(alertTitle);
        alertBuilder.setView(editText);
        alertBuilder.setPositiveButton(positiveButton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Category category = new Category(editText.getText().toString(), new ArrayList<String>());
                mCategoryFragment.addCategoryToManager(category);
                dialog.dismiss();
                displayCategoryItems(category);
            }
        });
        alertBuilder.create().show();
    }

    private void displayCategoryItems(Category category){
        if(!isTablet) {
            Intent categoryIntent = new Intent(this, CategoryItemActivity.class);
            categoryIntent.putExtra(CATEGORY_OBJECT_KEY, category);
            startActivityForResult(categoryIntent, MAIN_ACTIVITY_REQUEST_CODE);
        }else {
            if(mCategoryItemsFragment != null){
                getSupportFragmentManager().beginTransaction()
                        .remove(mCategoryItemsFragment)
                        .commit();
                mCategoryItemsFragment = null;
            }
            setTitle(category.getNames());
            mCategoryItemsFragment = CategoryItemsFragment.newInstance(category);
            if(mCategoryItemsFragment != null){
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.category_items_fragment_container, mCategoryItemsFragment)
                        .addToBackStack(null)
                        .commit();
            }
            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    displayCreateCategoryItemDilaog();
                }
            });
        }
    }

    private void displayCreateCategoryItemDilaog() {
        final EditText itemEditText = new EditText(this);
        itemEditText.setInputType(InputType.TYPE_CLASS_TEXT);
        new android.app.AlertDialog.Builder(this)
                .setTitle("Enter the item name here")
                .setView(itemEditText)
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String item = itemEditText.getText().toString();
                        mCategoryItemsFragment.addItemToCategory(item);
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == MAIN_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            if( data != null){
                mCategoryFragment.saveCategory((Category) data.getSerializableExtra(CATEGORY_OBJECT_KEY));
            }
        }
    }
    @Override
    public void categoryIsTapped(Category category) {
        displayCategoryItems(category);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setTitle(getString(R.string.app_name));
        if(mCategoryItemsFragment.category != null){
            mCategoryFragment.getmCategoryManager().saveCategory(mCategoryItemsFragment.category);
        }
        if(mCategoryItemsFragment != null){
            getSupportFragmentManager().beginTransaction()
                    .remove(mCategoryItemsFragment)
                    .commit();
            mCategoryItemsFragment = null;
        }
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayCreateCategoryDilaog();
            }
        });
    }
}