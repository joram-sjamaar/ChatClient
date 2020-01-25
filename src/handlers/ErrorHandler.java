package handlers;

import controllers.Controller;

public class ErrorHandler {

    public static void handleError(String error, Controller controller) {

        switch (error) {

            case "-ERR username has an invalid format (only characters, numbers and underscores are allowed)":
                controller.setLoginError("ERROR: Username has an invalid format (only characters, numbers and underscores are allowed)");
                break;

            case "-ERR user already logged in":
                controller.setLoginError("ERROR: User already logged in");
                break;

            case "-ERR There are no groups.":
                System.out.println("There are no groups to display.");
                break;

            default:
                System.out.println("ERROR: " + error);
                break;

        }

    }

}
