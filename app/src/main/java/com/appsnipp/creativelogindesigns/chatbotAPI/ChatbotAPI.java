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
        String result = "lorem ipsum dolor sit amet, consectetur adip, sed diam nonumy, sed diam nonumy";

                    // Post the result back to the main thread
                    mainHandler.post(() -> listener.onChatbotResponse(result));

        }

//    public static void sendMessageToChatbot(String userMessage, ChatbotResponseListener listener) {
//        executor.execute(() -> {
//            OkHttpClient client = new OkHttpClient();
//            String encodedMessage = Uri.encode(userMessage);
//
//            Request request = new Request.Builder()
//                    .url(Constants.API_URL + "?message=" + encodedMessage + "&uid=" + Constants.USER_ID)
//                    .get()
//                    .addHeader("X-RapidAPI-Key", Constants.API_KEY)
//                    .addHeader("X-RapidAPI-Host", Constants.API_HOST)
//                    .build();
//
//            try {
//                Response response = client.newCall(request).execute();
//
//                if (response.isSuccessful()) {
//                    String responseBody = response.body().string();
//                    JSONObject jsonResponse = new JSONObject(responseBody);
//                    JSONObject chatbotResponse = jsonResponse.getJSONObject("chatbot");
//                    String result = chatbotResponse.getString("response");
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




