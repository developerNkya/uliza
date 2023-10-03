package com.appsnipp.creativelogindesigns;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class chat_page extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText message;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //show the activity in full screen
        setContentView(R.layout.activity_chat);


        // Assuming you have chatMessages as a List<ChatMessage> containing chat messages.
        List<ChatMessage> chatMessages = new ArrayList<>();

// Initialize your RecyclerView and set its adapter
        RecyclerView recyclerView = findViewById(R.id.chatrecycle);
        ChatAdapter chatAdapter = new ChatAdapter(this, chatMessages);
        recyclerView.setAdapter(chatAdapter);

// LinearLayoutManager for vertical scrolling
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        //on cliking send sms:
        ImageView sendmsg = (ImageView) findViewById(R.id.sendmsg);
        message = findViewById(R.id.message);

        sendmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userMessage = message.getText().toString();
                chatMessages.add(new ChatMessage(userMessage, true)); // Assuming it's a user's sent message
                chatAdapter.notifyItemInserted(chatMessages.size() - 1); // Notify adapter for new message

                // Clear the EditText after sending
                message.setText("");
            }
        });

    }
}
