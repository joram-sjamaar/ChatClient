package model;

import util.MessageType;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Message {

    private Date date;
    private String message;
    private MessageType messageType;

    public Message(String message, MessageType messageType) {
        this.date = new Date();
        this.message = message;
        this.messageType = messageType;
    }

    public Date getDate() {
        return date;
    }

    public String getMessage() {
        return message;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    @Override
    public String toString() {
        return String.format("(%s) %s - %s", this.messageType, new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(this.date), this.message);
    }
}
