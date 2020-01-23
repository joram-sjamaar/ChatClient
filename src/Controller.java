import model.User;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Controller {

    private Socket socket;

    private Receiver receiver;
    private Sender sender;

    private String login_error = "";
    private boolean waiting_for_login = false;
    public boolean exit = false;

    private User user = new User();

    public static void main(String[] args) {
        Controller controller = new Controller();
        controller.init();
        controller.run();
    }

    private void run() {

        Scanner scanner = new Scanner(System.in);

        while (!exit) {

            // No user logged in yet
            if (!user.isLoggedIn()) {
                if (!waiting_for_login) {

                    System.out.println("Please enter a username: ");
                    String username = scanner.nextLine();
                    login(username);
                    waiting_for_login = true;

                } else if (!login_error.equals("")) {
                    System.out.println(login_error);
                    waiting_for_login = false;
                    login_error = "";
                }
            }

            // Logged in user
            else {
                System.out.println("Type a message: ");
                String message = scanner.nextLine();

                if (message.equals(".logout")) {
                    sender.logout();
                }

                else if (message.equals(".sent")) {
                    user.printSentMessages();
                }

                else {
                    sender.broadcast(message);
                }
            }
        }

        System.out.println("Goodbye.");
        System.exit(0);

    }

    private void init() {
        try {
            String SERVER_ADDRESS = "127.0.0.1";
            InetAddress address = InetAddress.getByName(SERVER_ADDRESS);
            int SERVER_PORT = 1337;
            socket = new Socket(address, SERVER_PORT);
            receiver = new Receiver(socket, this, user);
            sender = new Sender(socket, this, user);

            receiver.setSender(sender);
            receiver.start();
            sender.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setLoginError(String login_error) {
        this.login_error = login_error;
    }

    public void setWaiting_for_login(boolean waiting_for_login) {
        this.waiting_for_login = waiting_for_login;
    }

    private void login(String username) {
        sender.login(username);
    }

}
