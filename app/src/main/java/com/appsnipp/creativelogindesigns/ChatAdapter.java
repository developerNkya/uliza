package com.appsnipp.creativelogindesigns;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appsnipp.creativelogindesigns.ChatMessage;
import com.appsnipp.creativelogindesigns.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<ChatMessage> chatMessages;
    private final Context context;
    private static final int SENT_MESSAGE_TYPE = 1;
    private static final int RECEIVED_MESSAGE_TYPE = 2;


    public ChatAdapter(Context context, List<ChatMessage> chatMessages) {
        this.context = context;
        this.chatMessages = chatMessages;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Check the viewType to inflate the appropriate layout
        View view;
        if (viewType == SENT_MESSAGE_TYPE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_chat, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_chat_left, parent, false);
        }
        return new ChatViewHolder(view);
    }

    public interface OnlineStatusCallback {
        void updateOnlineStatus(String status);
    }

    private OnlineStatusCallback onlineStatusCallback;

    public void setOnlineStatusCallback(OnlineStatusCallback callback) {
        this.onlineStatusCallback = callback;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatMessage message = chatMessages.get(position);
        ChatViewHolder chatViewHolder = (ChatViewHolder) holder;

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String currentTime = sdf.format(new Date());

        // Set your chat message data here based on the message object
        chatViewHolder.messageTextView.setText(message.getMessageText());
        chatViewHolder.timeSent.setText(currentTime);

        //call function to trigger visibility:


        // Set visibility of other views (e.g., profile image, image view) based on message type (sent/received).
        // You can use message.isSent() to determine if it's a sent message.
        // Check if it's the last message and set online_val to "Online"
        if (position == chatMessages.size() - 1 && onlineStatusCallback != null) {
            onlineStatusCallback.updateOnlineStatus("Online");
        }
        //

    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    @Override
    public int getItemViewType(int position) {
        ChatMessage message = chatMessages.get(position);
        // Return a different view type based on whether the message is sent or received
        return message.isSent() ? SENT_MESSAGE_TYPE : RECEIVED_MESSAGE_TYPE;
    }


    public static class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView messageTextView;
        TextView timeSent;


        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            messageTextView = itemView.findViewById(R.id.msgc);
            timeSent = itemView.findViewById(R.id.timetv);


            // Initialize other views from your chat message layout here
        }
    }
}
