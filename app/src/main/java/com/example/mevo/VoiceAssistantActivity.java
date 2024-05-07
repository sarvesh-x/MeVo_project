package com.example.mevo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import androidx.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.Locale;

public class VoiceAssistantActivity extends AppCompatActivity {

    private SpeechRecognizer speechRecognizer;
    private TextToSpeech textToSpeech;
    private TextView textView;
    private boolean isInputReceived = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_assistant);
        textView = findViewById(R.id.show_AssistantCommand);

        initializeTextToSpeech();
    }

    private void initializeTextToSpeech() {
        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    // TextToSpeech engine initialized successfully
                    startAssistant();
                } else {
                    Log.e("TextToSpeech", "Initialization failed");
                    // Handle initialization failure
                }
            }
        });
    }

    private void startAssistant() {
        textToSpeech.speak("How can I help you?", TextToSpeech.QUEUE_FLUSH, null, null);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 1);
        } else {
            startListening();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Release resources
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        if (speechRecognizer != null) {
            speechRecognizer.destroy();
        }
    }

    private void startListening() {
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {}

            @Override
            public void onBeginningOfSpeech() {}

            @Override
            public void onRmsChanged(float rmsdB) {}

            @Override
            public void onBufferReceived(byte[] buffer) {}

            @Override
            public void onEndOfSpeech() {}

            @Override
            public void onError(int error) {
                Log.e("SpeechRecognizer", "Error: " + error);
                noInputReceived();
            }

            @Override
            public void onResults(Bundle results) {
                ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if (matches != null && !matches.isEmpty()) {
                    String result = matches.get(0);
                    textView.setText(result);
                    textToSpeech.speak("You said: " + result, TextToSpeech.QUEUE_FLUSH, null, null);
                    isInputReceived = true;
                    handleCommand(result.toLowerCase());
                } else {
                    noInputReceived();
                }
            }

            @Override
            public void onPartialResults(Bundle partialResults) {
                ArrayList<String> partials = partialResults.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if (partials != null && !partials.isEmpty()) {
                    String partialResult = partials.get(0);
                    textView.setText(partialResult);
                }
            }

            @Override
            public void onEvent(int eventType, Bundle params) {}
        });

        Intent recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);

        speechRecognizer.startListening(recognizerIntent);

        // Stop listening after a timeout
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isInputReceived) {
                    noInputReceived();
                }
            }
        }, 15000); // Extend timeout as needed
    }

    private void noInputReceived() {
        textView.setText("No input received.");
        textToSpeech.speak("No input received.", TextToSpeech.QUEUE_FLUSH, null, null);
        finish();
    }

    private void handleCommand(String command) {
        switch (command) {
            case "create patient":
                startActivity(new Intent(VoiceAssistantActivity.this, CreatePatientActivity.class));
                break;
            case "patient":
                startActivity(new Intent(VoiceAssistantActivity.this, ViewPatientsActivity.class));
                break;
            case "settings":
                startActivity(new Intent(VoiceAssistantActivity.this, SettingsActivity.class));
                break;
            default:
                textToSpeech.speak("Command not recognized.", TextToSpeech.QUEUE_FLUSH, null, null);
                finish();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startListening();
            } else {
                Log.e("SpeechRecognizer", "Permission denied");
            }
        }
    }
}