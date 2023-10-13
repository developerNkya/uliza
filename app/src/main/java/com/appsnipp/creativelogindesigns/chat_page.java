package com.appsnipp.creativelogindesigns;


import static android.content.ContentValues.TAG;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appsnipp.creativelogindesigns.chatbotAPI.ChatbotAPI;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import es.dmoral.toasty.Toasty;

public class chat_page extends AppCompatActivity {

    private EditText message;
    private ChatAdapter chatAdapter;
    private List<ChatMessage> chatMessages;

    private ImageView videoIcon;
    private ImageButton sendSMSButton; // Use ImageButton instead of ImageView
    private ImageButton micButton; // Use ImageButton instead of ImageView
    private boolean isTyping = false; // Add a flag to track typing
    private static final int REQUEST_CODE_SPEECH_INPUT = 1;

    TextView online_val;
    TextToSpeech t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); // Hide the title
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); // Show in full screen
        setContentView(R.layout.activity_chat);

        // Initialize your RecyclerView and set its adapter
        RecyclerView recyclerView = findViewById(R.id.chatrecycle);
        chatMessages = new ArrayList<>();
        chatAdapter = new ChatAdapter(this, chatMessages);
        recyclerView.setAdapter(chatAdapter);

        // LinearLayoutManager for vertical scrolling
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Initialize the send button, mic button, and message EditText
        sendSMSButton = findViewById(R.id.sendmsg);
        micButton = findViewById(R.id.mic_button);
        message = findViewById(R.id.message);
        videoIcon = findViewById(R.id.block);

        // Initially, show the mic button and hide the send SMS button
        micButton.setVisibility(View.VISIBLE);
        sendSMSButton.setVisibility(View.GONE);

         online_val = findViewById(R.id.onlinetv);

         // intializing the uliza AI:
        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });

        // Add a text change listener to the EditText
        message.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not used
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // When the user starts typing, switch to the send SMS button
                if (count > 0 && !isTyping) {
                    isTyping = true;
                    micButton.setVisibility(View.GONE);
                    sendSMSButton.setVisibility(View.VISIBLE);
                } else if (count == 0 && isTyping) {
                    isTyping = false;
                    micButton.setVisibility(View.VISIBLE);
                    sendSMSButton.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Not used
            }
        });

        videoIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //moving to video Page::
                Intent intent = new Intent(getApplicationContext(), VideoCallPage.class);
                startActivity(intent);

            }
        });

        sendSMSButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle sending SMS here
                String userMessage = message.getText().toString();
                chatMessages.add(new ChatMessage(userMessage, true)); // User's sent message
                chatAdapter.notifyItemInserted(chatMessages.size() - 1);
                online_val.setText("Typing ....");

                // Call the ChatbotAPI to get a response
                ChatbotAPI.sendMessageToChatbot(userMessage, new ChatbotAPI.ChatbotResponseListener() {
                    @Override
                    public void onChatbotResponse(String response) {
                        // Handle the chatbot's response here
                        Log.d("ChatBot Response", response);
                        String botResponse = response;
                        chatMessages.add(new ChatMessage(botResponse, false)); // Chatbot's response
                        chatAdapter.notifyItemInserted(chatMessages.size() - 1);
                        // Update the typing status
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                online_val.setText("Online");
//                                t1.speak(botResponse, TextToSpeech.QUEUE_FLUSH, null);

                            }
                        });
                    }
                });

                // Clear the EditText after sending
                message.setText("");
            }
        });

        //for the mic::
        micButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                            .makeText(chat_page.this, " " + e.getMessage(),
                                    Toast.LENGTH_SHORT)
                            .show();
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(
                        RecognizerIntent.EXTRA_RESULTS);

//                Toasty.success(chat_page.this,  Objects.requireNonNull(result).get(0), Toast.LENGTH_LONG).show();

                String userMessage = Objects.requireNonNull(result).get(0);
                chatMessages.add(new ChatMessage(userMessage, true)); // User's sent message
                chatAdapter.notifyItemInserted(chatMessages.size() - 1);

                ChatbotAPI.sendMessageToChatbot(userMessage, new ChatbotAPI.ChatbotResponseListener() {
                    @Override
                    public void onChatbotResponse(String response) {
                        // Handle the chatbot's response here
                        Log.d("ChatBot Response", response);
                        String botResponse = response;
                        chatMessages.add(new ChatMessage(botResponse, false)); // Chatbot's response
                        chatAdapter.notifyItemInserted(chatMessages.size() - 1);

                        // Update the typing status
                        t1.speak(botResponse, TextToSpeech.QUEUE_FLUSH, null);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                online_val.setText("Online");

                            }
                        });
                    }
                });

                // Save the recorded audio to a file (you need to implement this)
            }
        }
    }



}

