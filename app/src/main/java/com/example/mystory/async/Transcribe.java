package com.example.mystory.async;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.mystory.MainActivity;
import com.example.mystory.RecordStoryActivity;
import com.ibm.cloud.sdk.core.http.HttpMediaType;
import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.speech_to_text.v1.SpeechToText;
import com.ibm.watson.speech_to_text.v1.model.RecognizeOptions;
import com.ibm.watson.speech_to_text.v1.model.SpeechRecognitionResults;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class Transcribe extends AsyncTask<String, Void, String>
{
    String transcription;
    final String API_KEY = "xga_rLcRWOlD0q-9KITldbwCc3ijjmpUcrhSIQtxzfPl";
    final String URL = "https://api.us-south.speech-to-text.watson.cloud.ibm.com";
    final String TAG = "Transcribe";
    Context mContext;
    String audioFileURL;
    public Transcribe(Context context){
        this.mContext = context;
    }
    @Override
    protected String doInBackground(String... audioURL) {
        System.out.println("Starting Background");
        try {
            IamAuthenticator authenticator = new IamAuthenticator(API_KEY);
            SpeechToText speechToText = new SpeechToText(authenticator);
            speechToText.setServiceUrl(URL);
            System.out.println(audioURL[0]);
            audioFileURL = audioURL[0];
            File audioFile = new File(audioFileURL);

            RecognizeOptions options = new RecognizeOptions.Builder()
                    .audio(audioFile)
                    .contentType(HttpMediaType.AUDIO_OGG)
                    .model("en-AU_NarrowbandModel")
                    .build();
            System.out.println(audioFile.getName());
            final SpeechRecognitionResults transcript = speechToText.recognize(options).execute().getResult();
            System.out.println(transcript);
            transcription = transcript.getResults().get(0).getAlternatives().get(0).getTranscript();

        } catch (IOException e) {
            Log.e(TAG, "doInBackground: " + e.toString() );
            transcription = "";
        }
        String[] splitFile = audioFileURL.split("\\.");
        String newFile = splitFile[0] + '.' + splitFile[1]  + '.' + splitFile[2] + ".txt";
        System.out.println(newFile);
        File file = new File(newFile);
        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            try {
                stream.write(transcription.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } finally {
            try {
                stream.close();
                System.out.println("Done");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return transcription;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Toast.makeText(mContext, "Successfully Saved Story. Returning to Home Page", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(mContext, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }
}