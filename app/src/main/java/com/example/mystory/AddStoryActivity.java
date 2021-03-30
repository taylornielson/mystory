package com.example.mystory;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import static android.graphics.Color.rgb;

public class AddStoryActivity extends AppCompatActivity {

    MyRecyclerViewAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addstory);
        ArrayList<String> questions = new ArrayList<>();
        questions.add("Add Custom Question");
        questions.add("Tell Me About Your Wedding Day");
        questions.add("When is a Time You Feared For Your Life?");
        questions.add("When is a Time that you were spontaneous?");
        questions.add("What was your favorite family tradition?");

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.questionListRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this, questions);
        recyclerView.setAdapter(adapter);
    }
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }



    static class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

        private List<String> mData;
        private LayoutInflater mInflater;
        // data is passed into the constructor
        MyRecyclerViewAdapter(Context context, List<String> data) {
            this.mInflater = LayoutInflater.from(context);
            this.mData = data;
        }

        // inflates the row layout from xml when needed
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.storylist_row, parent, false);
            return new ViewHolder(view);
        }

        // binds the data to the TextView in each row
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            String question = mData.get(position);
            holder.myTextView.setText(question);
        }
        // total number of rows
        @Override
        public int getItemCount() {
            return mData.size();
        }
        // stores and recycles views as they are scrolled off screen
        class ViewHolder extends RecyclerView.ViewHolder {
            TextView myTextView;

            ViewHolder(View itemView) {
                super(itemView);
                myTextView = itemView.findViewById(R.id.TitleText);
                itemView.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new Toast(itemView.getContext()).makeText(itemView.getContext(), "Clicked on Question List " + myTextView.getText(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        }

        // convenience method for getting data at click position
        String getItem(int id) {
            return mData.get(id);
        }
    }
}
