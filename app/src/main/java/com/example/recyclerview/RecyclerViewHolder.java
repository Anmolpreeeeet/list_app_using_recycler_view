package com.example.recyclerview;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    private TextView textView;
    private TextView textView2;
    public RecyclerViewHolder(@NonNull View itemView) {
        super(itemView);

        textView = itemView.findViewById(R.id.text1);
        textView2 = itemView.findViewById(R.id.text2);
    }

    public TextView getTextView() {
        return textView;
    }

    public TextView getTextView2() {
        return textView2;
    }
}
