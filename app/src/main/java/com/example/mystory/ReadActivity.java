package com.example.mystory;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ReadActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        Intent intent = getIntent();
        String title = intent.getExtras().getString("title");

        Button homeButton = findViewById(R.id.home_button);

        TextView storyTitle = findViewById(R.id.story_title);
        storyTitle.setText(title);

        String storyText;

        //TODO get story text from files using title

        TextView storyContent = findViewById(R.id.story);
        storyContent.setMovementMethod(new ScrollingMovementMethod());
        String filePath = this.getExternalFilesDir("/").getAbsolutePath() + "/" + getIntent().getStringExtra("title") + "/" + getIntent().getStringExtra("title") + ".txt";
        File file = new File(filePath);

//Read text from file
        StringBuilder text = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();
        }
        catch (IOException e) {
            //You'll need to add proper error handling here
        }
        String fileText = text.toString();
        /*storyContent.setText("this is my story. it's a fake story for right now but it will be real someday. Lorem ipsum dolor " +
                "sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Purus " +
                "gravida quis blandit turpis cursus in hac. Tincidunt tortor aliquam nulla facilisi. Iaculis urna id volutpat lacus " +
                "laoreet non. At tellus at urna condimentum mattis pellentesque id nibh tortor. Velit laoreet id donec ultrices tincidunt " +
                "arcu non. Praesent semper feugiat nibh sed pulvinar. Lacinia quis vel eros donec ac. Risus at ultrices mi tempus. " +
                "Elementum nibh tellus molestie nunc non blandit massa enim nec. Molestie at elementum eu facilisis. Nunc mi ipsum " +
                "faucibus vitae aliquet nec ullamcorper. Diam phasellus vestibulum lorem sed risus. Turpis egestas maecenas pharetra " +
                "convallis posuere morbi. Sed id semper risus in. Adipiscing elit ut aliquam purus. Integer quis auctor elit sed vulputate " +
                "mi sit amet mauris. Quis imperdiet massa tincidunt nunc pulvinar.ry for right now but it will be real someday. Lorem ipsum " +
                "dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunry for right now but it will be real someday. " +
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunry for right now but it will be real " +
                "someday. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididun");*/
        storyContent.setText(fileText);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReadActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

}
