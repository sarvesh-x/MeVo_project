package com.example.mevo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;

public class VoiceAssistantActivity extends AppCompatActivity {

    private SpeechRecognizer speechRecognizer;
    private TextToSpeech textToSpeech;

    private TextView textView;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_assistant);

        ActivityCompat.requestPermissions(this,
                new String[] {
                        Manifest.permission.RECORD_AUDIO
                },
                PackageManager.PERMISSION_GRANTED);

        textView = findViewById(R.id.show_AssistantCommand);

        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                }
        });

        intent= new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        speechRecognizer=SpeechRecognizer.createSpeechRecognizer(this);
        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {
            }

            @Override
            public void onBeginningOfSpeech() {
            }

            @Override
            public void onRmsChanged(float v) {
            }

            @Override
            public void onBufferReceived(byte[] bytes) {
            }

            @Override
            public void onEndOfSpeech() {
            }

            @Override
            public void onError(int i) {
            }

            @Override
            public void onResults(Bundle bundle) {
                ArrayList<String> matches= bundle.getStringArrayList(speechRecognizer.RESULTS_RECOGNITION);
                String string= "";
                textView.setText("");
                if(matches!=null){
                    string=matches.get(0);
                    textView.setText(string);

                    if(string.equals("Open"))

                        switch(string){
                            case "notifications":setContentView(R.layout.fragment_notifications);
                                                    textToSpeech.speak("Opened notifications", textToSpeech.QUEUE_FLUSH,null,null);
                                break;
                            case "bedrooms":setContentView(R.layout.fragment_rooms);
                                            textToSpeech.speak("Opened bedrooms", textToSpeech.QUEUE_FLUSH,null,null);
                                break;
                            case "settings":setContentView(R.layout.activity_settings);
                                            textToSpeech.speak("Opened settings", textToSpeech.QUEUE_FLUSH,null,null);
                                break;
                        }

                }
            }

            @Override
            public void onPartialResults(Bundle bundle) {
            }

            @Override
            public void onEvent(int i, Bundle bundle) {
            }
        });


    }
    public void assistant_call(View view){
        textToSpeech.speak("How Can I Help You?", TextToSpeech.QUEUE_FLUSH,null,null);

        try {
            Thread.sleep(3000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }

        speechRecognizer.startListening(intent);



    }

}
