package com.example.recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CategoryItemActivity extends AppCompatActivity {

    private RecyclerView itemsRecyclerView;
    private FloatingActionButton addItemsFloatingActionButton;
    Category category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_item);

        category = (Category) getIntent().getSerializableExtra(MainActivity.CATEGORY_OBJECT_KEY);

        itemsRecyclerView = findViewById(R.id.items_recycler_view);
        itemsRecyclerView.setAdapter(new ItemsRecyclerAdapter(category));
        itemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        addItemsFloatingActionButton = findViewById(R.id.add_item_button);

        setTitle(category.getNames());
        addItemsFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayItemCreationDialog();
            }
        });
    }
    private void displayItemCreationDialog(){
        final EditText itemEditText = new EditText(this);
        itemEditText.setInputType(InputType.TYPE_CLASS_TEXT);

        new AlertDialog.Builder(this)
                .setTitle("Please enter the name of the item here...")
                .setView(itemEditText)
                .setPositiveButton("Create Item", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String itemName = itemEditText.getText().toString();
                        category.getList().add(itemName);

                        ItemsRecyclerAdapter itemsRecyclerAdapter =(ItemsRecyclerAdapter) itemsRecyclerView.getAdapter();
                        itemsRecyclerAdapter.notifyItemInserted(category.getList().size()  - 1);
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }

    @Override
    public void onBackPressed() {
        Bundle bundle = new Bundle();
        bundle.putSerializable(MainActivity.CATEGORY_OBJECT_KEY, category);
        Intent intent = new Intent();
        intent.putExtras(bundle);
        setResult(Activity.RESULT_OK, intent);
        super.onBackPressed();
    }
}