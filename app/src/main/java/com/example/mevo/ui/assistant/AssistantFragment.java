package com.example.mevo.ui.assistant;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.mevo.CreatePatientActivity;
import com.example.mevo.R;
import com.example.mevo.SettingsActivity;
import com.example.mevo.ViewPatientsActivity;
import com.example.mevo.databinding.FragmentAssistantBinding;

import java.util.ArrayList;

public class AssistantFragment extends Fragment {
    private FragmentAssistantBinding binding;
    private TextToSpeech textToSpeech;
    public static final Integer RecordAudioRequestCode = 1;
    private SpeechRecognizer speechRecognizer;
    private TextView editText;
    private ImageView micButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
        ViewGroup container, Bundle savedInstanceState) {
            binding = FragmentAssistantBinding.inflate(inflater, container, false);
            View root = binding.getRoot();

        ProgressBar pg = root.findViewById(R.id.assistantprogressBar);
        initializeTextToSpeech();
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){
            checkPermission();
        }
        textToSpeech.speak("How can I help you?", TextToSpeech.QUEUE_FLUSH, null, null);
        editText = root.findViewById(R.id.text);
        editText.setText("How can I help you?");
        micButton = root.findViewById(R.id.button);
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(getContext());

        micButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pg.setVisibility(View.VISIBLE);
                startSpeechRecognition();
            }
        });
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(getContext());
        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {}

            @Override
            public void onBeginningOfSpeech() {
                editText.setText("Listening..");
            }

            @Override
            public void onRmsChanged(float rmsdB) {}

            @Override
            public void onBufferReceived(byte[] buffer) {}

            @Override
            public void onEndOfSpeech() {}

            @Override
            public void onError(int error) {
                editText.setText("Error: " + error);
            }

            @Override
            public void onResults(Bundle results) {
                ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if (matches != null && !matches.isEmpty()) {
                    String recognizedText = matches.get(0);
                    editText.setText(recognizedText);
                    handleCommand(recognizedText);
                    pg.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPartialResults(Bundle partialResults) {}

            @Override
            public void onEvent(int eventType, Bundle params) {}
        });

        return root;
    }

    private void initializeTextToSpeech() {
        textToSpeech = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    // TextToSpeech engine initialized successfully
                } else {
                    Log.e("TextToSpeech", "Initialization failed");
                    // Handle initialization failure
                }
            }
        });
    }

    private void handleCommand(String command) {
        switch (command) {
            case "hello":
                Toast.makeText(getContext(),"Hello User",Toast.LENGTH_LONG).show();
                break;
            case "create patient":
                startActivity(new Intent(getActivity(), CreatePatientActivity.class));
                break;
            case "patient":
                startActivity(new Intent(getActivity(), ViewPatientsActivity.class));
                break;
            case "settings":
                startActivity(new Intent(getActivity(), SettingsActivity.class));
                break;
            default:
                textToSpeech.speak("Command not recognized.", TextToSpeech.QUEUE_FLUSH, null, null);
                break;
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        if (speechRecognizer != null) {
            speechRecognizer.destroy();
        }
    }

    private void startSpeechRecognition() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");

        // Start listening for speech
        speechRecognizer.startListening(intent);
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.RECORD_AUDIO},RecordAudioRequestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RecordAudioRequestCode && grantResults.length > 0 ){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Toast.makeText(getContext(),"Permission Granted",Toast.LENGTH_SHORT).show();
        }
    }
}
