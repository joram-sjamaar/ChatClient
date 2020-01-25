package handlers;

import threads.Sender;

public class HeartBeatHandler {

    public static void sendPong(Sender sender) {

        sender.pong();

    }

    public static void handleTimout() {

        System.out.println("Pong timeout");

        System.exit(0);

    }

}
