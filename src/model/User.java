package model;

import util.MessageType;

import java.util.LinkedList;

public class User {

    private String username;
    private boolean loggedIn;
    private boolean inGroup;
    private Group group;
    private LinkedList<Message> sentMessages = new LinkedList<>();

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

    public void printSentMessages() {
        for (Message message : sentMessages) {
            System.out.println(message);
        }
    }
}
