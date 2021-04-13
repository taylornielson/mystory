package com.example.mystory;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mystory.async.Transcribe;
import com.ibm.cloud.sdk.core.http.HttpMediaType;
import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.speech_to_text.v1.SpeechToText;
import com.ibm.watson.speech_to_text.v1.model.RecognizeOptions;
import com.ibm.watson.speech_to_text.v1.model.SpeechRecognitionResults;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RecordStoryActivity extends AppCompatActivity {
    MediaRecorder mediaRecorder;
    String recordFile;
    final String API_KEY = "xga_rLcRWOlD0q-9KITldbwCc3ijjmpUcrhSIQtxzfPl";
    final String URL = "https://api.us-south.speech-to-text.watson.cloud.ibm.com";
    Boolean recording = false;
    Boolean started = false;
    private String recordPermission = Manifest.permission.RECORD_AUDIO;
    private int PERMISSION_CODE = 21;
    static final String TAG = "MediaRecording";
    private File[] allFiles;
    String savedFilePath;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkPermissions();
        setContentView(R.layout.activity_record);
        TextView questionText = findViewById(R.id.QuestionText);
        ImageButton recordButton = findViewById(R.id.recordButton);
        TextView statusText = findViewById(R.id.recordingText);
        Button finishRecording = findViewById(R.id.finishedButton);
        Intent intent = getIntent();
        questionText.setText(intent.getStringExtra("question"));
        String path = this.getExternalFilesDir("/").getAbsolutePath();
        File directory = new File(path);
        allFiles = directory.listFiles();
        for (File file : allFiles){
            System.out.println(file.getName());
        }
        //Creating MediaRecorder and specifying audio source, output format, encoder & output format

        recordButton.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                if (recording){
                    recordButton.setImageDrawable(getDrawable(R.drawable.ic_baseline_mic_24));
                    statusText.setText("Done");
                    onStop();
                    recording = false;
                }else {
                    recordButton.setImageDrawable(getDrawable(R.drawable.ic_baseline_pause_circle_outline_24));
                    statusText.setText("Recording");
                    recording = true;
                    startRecording();
                }
            }
        });
        finishRecording.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                try {
                    transcribe();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void startRecording(){
        System.out.println("starting");

        String recordPath = this.getExternalFilesDir("/").getAbsolutePath() + "/" + getIntent().getStringExtra("question");
        File newFolder = new File(recordPath);
        newFolder.mkdir();
        //Get current date and time
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss", Locale.US);
        Date now = new Date();

        //initialize filename variable with date and time at the end to ensure the new file wont overwrite previous file
        recordFile = getIntent().getStringExtra("question") + ".opus";
        savedFilePath = recordPath + "/" + recordFile;
        //Setup Media Recorder for recording
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.OGG);
        mediaRecorder.setOutputFile(savedFilePath);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.OPUS);

        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Start Recording
        mediaRecorder.start();
    }

    private boolean checkPermissions() {
        //Check permission
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), recordPermission) == PackageManager.PERMISSION_GRANTED) {
            //Permission Granted
            return true;
        } else {
            //Permission not granted, ask for permission
            ActivityCompat.requestPermissions(this, new String[]{recordPermission}, PERMISSION_CODE);
            return false;
        }
    }
    @Override
    public void onStop() {
        super.onStop();
        if(recording){
            stopRecording();
        }
    }
    private void stopRecording() {
        System.out.println("stopping");
        //Stop Timer, very obvious
        //Stop media recorder and set it to null for further use to record new audio
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;
    }

    private void transcribe() throws FileNotFoundException {
        if(recording){
            stopRecording();
        }
        System.out.println(savedFilePath);
        Toast.makeText(this.getApplicationContext(),"Transcribing Your Story: Please Wait", Toast.LENGTH_LONG).show();
        Transcribe t = new Transcribe(getApplicationContext());
        t.execute(savedFilePath);
    }
}


