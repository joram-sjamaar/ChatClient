package handlers.commands;

import controllers.Controller;
import exceptions.TooFewArgumentsException;
import handlers.ErrorHandler;
import model.User;
import threads.Sender;

import java.io.*;

public class FileTransferHandler {

    private static int PACKET_SIZE = 8196;

    public static void transfer(Sender sender, String line, User user, Controller controller) {

        try {

            // Remove command from line
            String details = line.replace("transfer ", "");

            String[] parts = details.split(" ");

            if (parts.length < 2)
                throw new TooFewArgumentsException("Correct usage: .transfer <filename> <username>");

            String fileName = parts[0];

            String username = parts[1];

            sendFile(fileName, username, sender);

            // Format for protocol
            // CMD GROUP CREATE <groupName>
//            String format = String.format("CMD GROUP CREATE %s", groupName);

//            sender.send(format);

        }

        catch(TooFewArgumentsException | IOException e) {

            ErrorHandler.handleError(e.getMessage(), controller);

        }

    }

    private static void sendFile(String fileName, String username, Sender sender) throws IOException {

        String filePath = String.format("%s/files/%s", System.getProperty("user.dir"), fileName);

        File file = new File(filePath);

        FileInputStream fis = new FileInputStream(file);

        byte[] bytes = new byte[PACKET_SIZE];

        BufferedInputStream bis = new BufferedInputStream(fis);

        int readLength = -1;

        int nr_of_packets = (int) Math.ceil(file.length() / (PACKET_SIZE + 0.0));

        System.out.println(nr_of_packets);

        int package_nr = 1;
        while ((readLength = bis.read(bytes)) > 0) {
//            System.out.println(new String(bytes));
            sender.sendFile(username, file.getName(), package_nr, nr_of_packets, bytes);
            System.out.printf("Sending package %d/%d: %d bytes...\n", package_nr, nr_of_packets, readLength);
//            output.write(bytes, 0, readLength);

            package_nr++;
        }

        bis.close();

    }

}
