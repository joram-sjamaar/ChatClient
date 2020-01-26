package handlers.commands;

import controllers.Controller;
import exceptions.TooFewArgumentsException;
import handlers.ErrorHandler;
import model.User;
import threads.Sender;

import java.io.*;
import java.util.Base64;

public class FileTransferHandler {

    private static int PACKET_SIZE;

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

        }

        catch(TooFewArgumentsException | IOException e) {

            ErrorHandler.handleError(e.getMessage(), controller);

        }

    }

    private static void sendFile(String fileName, String username, Sender sender) throws IOException {

        String filePath = String.format("%s/files/%s", System.getProperty("user.dir"), fileName);

        File file = new File(filePath);

        FileInputStream fis = new FileInputStream(file);

        byte[] bytes = new byte[(int) file.length()];

        BufferedInputStream bis = new BufferedInputStream(fis);

        int readLength = -1;

        int nr_of_packets = (int) Math.ceil(file.length() / (file.length() + 0.0));

        System.out.println(nr_of_packets);

        int package_nr = 1;
        while ((readLength = bis.read(bytes)) > 0) {
            sender.sendFile(username, file.getName(), package_nr, nr_of_packets, Base64.getEncoder().encodeToString(bytes));
            System.out.printf("Sending package %d/%d: %d bytes...\n", package_nr, nr_of_packets, readLength);

            package_nr++;
        }

        bis.close();

    }

    public static void handleFileTransfer(String line, User user) {

        FileOutputStream fos = null;

        try {
            String[] parts = line.split(" ");

            String fileName = parts[1];

            String username = parts[2];

            int package_number = Integer.parseInt(parts[3]);

            int total_packages = Integer.parseInt(parts[4]);

            String content = line.replace(String.format("FILETRANSFER %s %s %d %d ", fileName, username, package_number, total_packages), "");

            byte[] bytes = Base64.getDecoder().decode(content);

            fos = new FileOutputStream(fileName);

            BufferedOutputStream bos = new BufferedOutputStream(fos);

            bos.write(bytes, 0, bytes.length);

            bos.close();

            System.out.printf("You received file '%s' from %s\n", fileName, username);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
