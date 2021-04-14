package com.example.mystory;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ViewStoryActivity extends AppCompatActivity {
    MyRecyclerViewAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addstory);
        ArrayList<String> questions = new ArrayList<>();
        questions.add("What were you like as a student?");
        questions.add("What was your wedding day like?");
        questions.add("When is a Time You Feared For Your Life?");
        questions.add("When is a Time that you were spontaneous?");
        questions.add("What was your favorite family tradition?");
        questions.add("What did you do for fun when you were younger?");

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.questionListRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this, questions);
        recyclerView.setAdapter(adapter);
    }
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }

    class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

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
            View view = mInflater.inflate(R.layout.storylist_answered_row, parent, false);
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
            Button readButton;
            Button listenButton;
            ViewHolder(View itemView) {
                super(itemView);
                myTextView = itemView.findViewById(R.id.TitleTextAnswered);
                readButton = itemView.findViewById(R.id.ReadButton);
                listenButton = itemView.findViewById(R.id.ListenButton);
                listenButton.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), ListenActivity.class);
                        intent.putExtra("title", myTextView.getText());
                        startActivity(intent);
                    }
                });
                readButton.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), ReadActivity.class);
                        intent.putExtra("title", myTextView.getText());
                        startActivity(intent);
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
