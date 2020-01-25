package threads;

import controllers.Controller;
import model.User;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Sender extends Thread {

    Receiver receiver;
    Socket socket;
    OutputStream outputStream;
    Controller controller;
    User user;

    public Sender(Socket socket, Controller controller, User user) throws IOException {
        this.socket = socket;
        this.outputStream = socket.getOutputStream();
        this.controller = controller;
        this.user = user;
    }

    public void broadcast(String message) {
        send("BCST " + message);
    }

    public void send(String message) {
        PrintWriter writer = new PrintWriter(outputStream);
        writer.println(message);
        writer.flush();
    }

    public void login(String username) {
        send("HELO " + username);
    }

    public void logout() {
        send("QUIT");
        user.setUsername("");
        user.setLoggedIn(false);
    }

    public void pong() {
        send("PONG");
    }

}
