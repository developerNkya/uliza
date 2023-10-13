package com.appsnipp.creativelogindesigns.chatbotAPI;


import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.appsnipp.creativelogindesigns.values.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.*;

import android.os.AsyncTask;
import android.util.Log;
import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import okhttp3.*;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ChatbotAPI {

    private static final Executor executor = Executors.newSingleThreadExecutor();
    private static final Handler mainHandler = new Handler(Looper.getMainLooper());

    public interface ChatbotResponseListener {
        void onChatbotResponse(String response);
    }

        public static void sendMessageToChatbot(String userMessage, ChatbotResponseListener listener) {
        String result = "Hello, this is Alice and this is a test message from the chatbot.";

                    // Post the result back to the main thread
                    mainHandler.post(() -> listener.onChatbotResponse(result));

        }

//    public static void sendMessageToChatbot(String userMessage, ChatbotResponseListener listener) {
//        executor.execute(() -> {
//            OkHttpClient client = new OkHttpClient();
//            String encodedMessage = Uri.encode(userMessage);
//            String apiUrl = "http://api.brainshop.ai/get?bid=178265&key=5XUgq3ToMThwFcqn&uid=[uid]&msg=" + encodedMessage;
//
//            Request request = new Request.Builder()
//                    .url(apiUrl)
//                    .get()
//                    .build();
//
//            try {
//                Response response = client.newCall(request).execute();
//
//                if (response.isSuccessful()) {
//                    String responseBody = response.body().string();
//                    JSONObject jsonResponse = new JSONObject(responseBody);
//                    String result = jsonResponse.getString("cnt");
//
//                    // Post the result back to the main thread
//                    mainHandler.post(() -> listener.onChatbotResponse(result));
//                } else {
//                    // Handle the error
//                    mainHandler.post(() -> listener.onChatbotResponse("Error: " + response.code()));
//                }
//            } catch (IOException | JSONException e) {
//                e.printStackTrace();
//                // Handle the error
//                mainHandler.post(() -> listener.onChatbotResponse("Error: " + e.getMessage()));
//            }
//        });
//    }

}




