package com.appsnipp.creativelogindesigns;

public class ChatMessage {
    private String messageText;
    private boolean isSent; // true if the message is sent by the user, false if received from the AI

    public ChatMessage(String messageText, boolean isSent) {
        this.messageText = messageText;
        this.isSent = isSent;
    }

    public String getMessageText() {
        return messageText;
    }

    public boolean isSent() {
        return isSent;
    }
}

