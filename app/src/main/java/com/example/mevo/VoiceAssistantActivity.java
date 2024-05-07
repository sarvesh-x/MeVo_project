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
    private Intent recognizerIntent;
    private boolean isInputReceived = false;
    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_assistant);
        textView = findViewById(R.id.show_AssistantCommand);

        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i != TextToSpeech.ERROR) {
                    textToSpeech.speak("How can I help you?", TextToSpeech.QUEUE_FLUSH, null, null);
                    // Start listening after 3 seconds
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startListening();
                        }
                    }, 10000);
                }
            }
        });

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 1);
        } else {
            startListening();
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isInputReceived) {
                    textView.setText("No input received.");
                    textToSpeech.speak("No input received.", TextToSpeech.QUEUE_FLUSH, null, null);
                    finish();
                }
            }
        }, 10000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
                textToSpeech.speak("Error occurred.", TextToSpeech.QUEUE_FLUSH, null, null);

                textView.setText("Error occurred.");

                finish();
            }

            @Override
            public void onResults(Bundle bundle) {
                ArrayList<String> matches = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if (matches != null && !matches.isEmpty()) {
                    String result = matches.get(0);
                    textView.setText(result);
                    textToSpeech.speak("You said: " + result, TextToSpeech.QUEUE_FLUSH, null, null);
                    isInputReceived = true;
                    finish();
                } else {
                    textView.setText("No input received.");
                    textToSpeech.speak("No input received.", TextToSpeech.QUEUE_FLUSH, null, null);
                    finish();
                }
            }

            @Override
            public void onPartialResults(Bundle bundle) {
            }

            @Override
            public void onEvent(int i, Bundle bundle) {
            }
        });

        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);

        speechRecognizer.startListening(recognizerIntent);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                speechRecognizer.stopListening();
                if (!isInputReceived) {
                    textView.setText("No input received.");
                    textToSpeech.speak("No input received.", TextToSpeech.QUEUE_FLUSH, null, null);
                    finish();
                }
            }
        }, 10000);
    }

//    private void finishAfterDelay(long delayMillis) {
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                finish();
//            }
//        }, delayMillis);
//    }

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