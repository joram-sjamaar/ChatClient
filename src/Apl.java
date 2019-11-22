import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Apl {

    private static String SERVER_ADDRESS = "127.0.0.1";
    private static int SERVER_PORT = 1337;
    private static Socket socket;

    public static void main(String[] args) {
        new Apl().run();
    }

    private void run() {
        try {
            InetAddress address = InetAddress.getByName(SERVER_ADDRESS);
            socket = new Socket(address, SERVER_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Receiver receiver = new Receiver(socket);
        receiver.start();

        Sender sender = null;
        try {
            sender = new Sender(socket);
            sender.start();
            sender.login("Joram");
            sender.broadcast("Hi!");
            sender.logout();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
