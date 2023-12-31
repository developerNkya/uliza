package com.appsnipp.creativelogindesigns;

import static android.content.ContentValues.TAG;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.text.Html;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Locale;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.appsnipp.creativelogindesigns.chatbotAPI.ChatbotAPI;

import org.apache.commons.text.StringEscapeUtils;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

import es.dmoral.toasty.Toasty;

public class VideoCallPage extends AppCompatActivity {

    private WebView webView;
    private ToggleButton speakButton;
    private SpeechRecognizer speechRecognizer;
    private TextToSpeech textToSpeech;

    private static final int REQUEST_CODE_SPEECH_INPUT = 1;

    TextToSpeech t1;
    private int charIndex = 0;
    private Handler textAnimationHandler;
    TextView cardboardText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_call_page);


        webView = findViewById(R.id.webview);
        speakButton = findViewById(R.id.speak_button);
        cardboardText = findViewById(R.id.cardboard_text);

        // Initialize WebView
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("file:///android_asset/video_call_layout.html");

        webView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        // Initialize TextToSpeech
        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });

        // Set up the speak button click listener
        speakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toasty.success(VideoCallPage.this, "Start Speaking", Toast.LENGTH_LONG).show();
                speakButton.setEnabled(false);
                //cleaning the cardboard::
                cardboardText.setText("");
                cardboardText.setVisibility(View.GONE);
                //calling speech recognition::
                startSpeechRecognition();


            }

        });
    }


    private void startSpeechRecognition() {
        Intent intent
                = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text");

        try {
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
        }
        catch (Exception e) {
            Toast
                    .makeText(VideoCallPage.this, " " + e.getMessage(),
                            Toast.LENGTH_SHORT)
                    .show();
        }
    }

    // Implement speech recognition and chatbot interaction logic
    @Override
     protected void onActivityResult(int requestCode, int resultCode,@Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(
                        RecognizerIntent.EXTRA_RESULTS);

                String userMessage = Objects.requireNonNull(result).get(0);
                ChatbotAPI.sendMessageToChatbot(userMessage, new ChatbotAPI.ChatbotResponseListener() {
                    @Override
                    public void onChatbotResponse(String response) {
                        // Handle the chatbot's response here
                        Log.d("ChatBot Response", response);
                        String botResponse = Html.fromHtml(response).toString();
                        Log.d(TAG, "onChatbotResponse: "+ botResponse);

                       String  botResponse1 = botResponse.replace("'", "\\'").replace("\"", "\\\"");
                        String javascriptCode = "SDK.canPlayVideo = true;\n" +
                                "web.addMessage('" + botResponse1 + "', '', '', '');" +
                                " web.processMessages();";


                        String dynamicText = botResponse;
                        cardboardText.setVisibility(View.VISIBLE);
                        cardboardText.setText(dynamicText);

                        textAnimationHandler = new Handler(Looper.getMainLooper());
                        startTextAnimation(botResponse);

                        Handler handler = new Handler();

                        // Delayed execution of the t1.speak method after 2 seconds
                        // Calculate the total number of words in userMessage
                        String[] words = botResponse.split(" ");
                        int totalWords = words.length;

// Calculate an estimated time for the robot to speak
                        int estimatedTimeInMillis = totalWords * 320; // Replace with your estimation logic

// Delayed execution of t1.speak method after 2.1 seconds
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                t1.speak(botResponse, TextToSpeech.QUEUE_FLUSH, null);

                                // Schedule JavaScript execution after estimatedTimeInMillis
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        // Execute your JavaScript code here
                                        String javascriptCode = "web.addMessage('', '', '', '');" +
                                                " web.processMessages();" +
                                                "SDK.canPlayAudio = null;";
                                        webView.evaluateJavascript(javascriptCode, null);

                                        speakButton.setChecked(false);
                                        speakButton.setEnabled(true);
                                    }
                                }, estimatedTimeInMillis);
                            }
                        }, 2100); // 2.1 seconds
                        webView.evaluateJavascript(javascriptCode, null);
                    }

                    private void startTextAnimation(String userMessage) {
                        textAnimationHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (charIndex <= userMessage.length()) {
                                    cardboardText.setText(userMessage.substring(0, charIndex));
                                    charIndex++;
                                    textAnimationHandler.postDelayed(this, 1); // Adjust the delay as needed
                                }
                            }
                        }, 100); // Initial delay, you can adjust this as well
                    }
                });

            }
        }
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }



}
