package com.appsnipp.creativelogindesigns.classes;

import android.app.Activity;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class SpeechToTextHelper {

    private static final int REQUEST_CODE_SPEECH_INPUT = 1;

    private Activity activity;
    private OnSpeechResultListener listener;

    public SpeechToTextHelper(Activity activity) {
        this.activity = activity;
    }

    public void setOnSpeechResultListener(OnSpeechResultListener listener) {
        this.listener = listener;
    }

    public void startSpeechRecognition() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text");

        try {
            activity.startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
        } catch (Exception e) {
            Toast.makeText(activity, " " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_SPEECH_INPUT && resultCode == Activity.RESULT_OK && data != null) {
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (listener != null) {
                String userMessage = Objects.requireNonNull(result).get(0);
                listener.onSpeechResult(userMessage);
            }
        }
    }

    public interface OnSpeechResultListener {
        void onSpeechResult(String result);
    }
}

