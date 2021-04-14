package com.example.mystory;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public Button questionListButton;
    public Button addQuestionButton;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        questionListButton = findViewById(R.id.questionListButton);
        addQuestionButton = findViewById(R.id.addStoryButton);
        Typeface typeface = getResources().getFont(R.font.comfortaa);
        questionListButton.setTypeface(typeface);
        addQuestionButton.setTypeface(typeface);

        questionListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToQuestionList();
            }
        });
        addQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToCustomQuestion();
            }
        });
    }

    private void goToQuestionList() {
        Intent myIntent = new Intent(MainActivity.this, ViewStoryActivity.class);
        MainActivity.this.startActivity(myIntent);
    }

    private void goToCustomQuestion() {
        Intent myIntent = new Intent(MainActivity.this, AddStoryActivity.class);
        MainActivity.this.startActivity(myIntent);

    }
}