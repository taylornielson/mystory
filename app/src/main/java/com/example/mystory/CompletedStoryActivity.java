package com.example.mystory;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;



public class CompletedStoryActivity extends AppCompatActivity {
    Button listenButton;
    Button readButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completedstory);
        Intent intent = getIntent();
        String title = intent.getExtras().getString("title");

        //TextView storyTitle = this.findViewById(R.id.story_title);
        //storyTitle.setText(title);

        //ImageView image = findViewById(R.id.image);
        listenButton = findViewById(R.id.listen_button);
        readButton = findViewById(R.id.read_button);

        listenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CompletedStoryActivity.this, ListenActivity.class);
                intent.putExtra("title", title);
                startActivity(intent);
            }
        });

        readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CompletedStoryActivity.this, ReadActivity.class);
                intent.putExtra("title", title);
                startActivity(intent);
            }
        });

/*
    }

    public void selectPicture(View view) {
        UploadPictureDialog dialog = new UploadPictureDialog();
        dialog.show(getSupportFragmentManager(), "UploadPictureDialog");
    }*/
    }
}
