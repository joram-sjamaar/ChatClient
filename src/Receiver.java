import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Receiver extends Thread {

    private Socket socket;
    private InputStream inputStream;

    public Receiver(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            inputStream = socket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(true) {
            try {
                String message = getMessage();

                if (message != null)
                    System.out.println(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private String getMessage() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        return reader.readLine();
    }


}
