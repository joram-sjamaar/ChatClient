package model;

import util.MessageType;

import java.util.ArrayList;
import java.util.LinkedList;

public class User {

    private String username;
    private boolean loggedIn;
    private boolean inGroup;
    private Group group;
    private LinkedList<Message> sentMessages = new LinkedList<>();
    private ArrayList<Recipient> recipients = new ArrayList<>();

    public User() {
        this.username = "";
        this.loggedIn = false;
        this.inGroup = false;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isInGroup() {
        return inGroup;
    }

    public void setInGroup(boolean inGroup) {
        this.inGroup = inGroup;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public void logSentMessage(String message, MessageType type) {
        sentMessages.addFirst(new Message(message, type));
    }

    public ArrayList<Recipient> getRecipients() {
        return recipients;
    }

    public void addRecipient(String username, String privateKey) {
        if (hasRecipient(username))
            getRecipient(username).setPrivateKey(privateKey);
        else
            recipients.add(new Recipient(username, privateKey));
    }

    public boolean hasRecipient(String username) {

        for (Recipient r : recipients)
            if (r.getUsername().equals(username)) return true;

        return false;

    }

    public Recipient getRecipient(String username) {
        for (Recipient r : recipients)
            if (r.getUsername().equals(username)) return r;

        return null;
    }

    public void printSentMessages() {
        for (Message message : sentMessages) {
            System.out.println(message);
        }
    }
}
