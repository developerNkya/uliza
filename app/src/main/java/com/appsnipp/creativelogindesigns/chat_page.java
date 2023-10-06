package com.appsnipp.creativelogindesigns;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.appsnipp.creativelogindesigns.ChatAdapter;
import com.appsnipp.creativelogindesigns.ChatMessage;
import com.appsnipp.creativelogindesigns.R;
import com.appsnipp.creativelogindesigns.chatbotAPI.ChatbotAPI;
import java.util.ArrayList;
import java.util.List;

public class chat_page extends AppCompatActivity {

    private EditText message;
    private ChatAdapter chatAdapter;
    private List<ChatMessage> chatMessages;

    private ImageButton sendSMSButton; // Use ImageButton instead of ImageView
    private ImageButton micButton; // Use ImageButton instead of ImageView
    private boolean isTyping = false; // Add a flag to track typing

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

        // Initially, show the mic button and hide the send SMS button
        micButton.setVisibility(View.VISIBLE);
        sendSMSButton.setVisibility(View.GONE);

        TextView online_val = findViewById(R.id.onlinetv);

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
                            }
                        });
                    }
                });

                // Clear the EditText after sending
                message.setText("");
            }
        });
    }
}

