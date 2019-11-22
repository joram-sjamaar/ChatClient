import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Sender extends Thread {

    private Socket socket;
    OutputStream outputStream;

    public Sender(Socket socket) throws IOException {
        this.socket = socket;
        outputStream = socket.getOutputStream();
    }

    public void run() {

    }

    public void broadcast(String message) {
        send("BCST " + message);
    }

    private void send(String message) {
        PrintWriter writer = new PrintWriter(outputStream);
        writer.println(message);
        writer.flush();
    }

    public void login(String username) {
        send("HELO " + username);
    }

    public void logout() {
        send("QUIT");
    }

}
