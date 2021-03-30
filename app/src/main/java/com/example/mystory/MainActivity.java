package com.example.mystory;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
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
    public Button orderBookButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        questionListButton = findViewById(R.id.questionListButton);
        addQuestionButton = findViewById(R.id.addStoryButton);

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
        new Toast(this.getApplicationContext()).makeText(this.getApplicationContext(),"Clicked on Question List", Toast.LENGTH_LONG).show();
    }

    private void goToCustomQuestion() {
        Intent myIntent = new Intent(MainActivity.this, AddStoryActivity.class);
        MainActivity.this.startActivity(myIntent);

    }
}