package com.techelevator.view;

import com.techelevator.model.Campground;
import com.techelevator.model.Park;
import com.techelevator.model.Site;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Menu {

    private final PrintWriter out;
    private final Scanner in;

    public Menu(InputStream input, OutputStream output) {
        out = new PrintWriter(output);
        in = new Scanner(input);
    }

    public Object getChoiceFromOptions(Object[] options) {
        Object choice = null;
        while (choice == null) {
            displayMenuOptions(options);
            choice = getChoiceFromUserInput(options);
        }
        return choice;
    }

    private Object getChoiceFromUserInput(Object[] options) {
        Object choice = null;
        String userInput = in.nextLine();
        try {
            int selectedOption = Integer.valueOf(userInput);
            if (selectedOption > 0 && selectedOption <= options.length) {
                choice = options[selectedOption - 1];
            }
        } catch (NumberFormatException e) {
            // eat the exception, an error message will be displayed below since choice will be null
        }
        if (choice == null) {
            out.println("\n*** " + userInput + " is not a valid option ***\n");
        }
        return choice;
    }

    private void displayMenuOptions(Object[] options) {
        out.println();
        for (int i = 0; i < options.length; i++) {
            int optionNum = i + 1;
            out.println(optionNum + ") " + options[i]);
        }
        out.print("\nPlease choose an option >>> ");
        out.flush();
    }

    public Park getParkFromInput(List<Park> parks) {
        Park choice = null;
        while (choice == null) {
            displayParkOptions(parks);
            choice = getParkSelectionFromUser(parks);
        }
        return choice;
    }

    private Park getParkSelectionFromUser(List<Park> parks) {
        Park parkChoice = null;
        String userInput = in.nextLine();
        try {
            int selectedNumber = Integer.valueOf(userInput);
            if (selectedNumber <= parks.size()) {
                parkChoice = parks.get(selectedNumber - 1);
            } else if (selectedNumber == parks.size() + 1) {
                System.exit(0);
            }
        } catch (NumberFormatException e) {

        }
        if (parkChoice == null) {
            out.println("\n*** " + userInput + " is not a valid park ***\n");
        }
        return parkChoice;
    }

    public Campground getCampgroundSelectionFromUser(List<Campground> campgrounds) {
        Campground campgroundChoice = null;
        String userInput = in.nextLine();
        try {
            int selectedNumber = Integer.valueOf(userInput);
            if (selectedNumber > 0 && selectedNumber <= campgrounds.size()) {
                campgroundChoice = campgrounds.get(selectedNumber - 1);
            } else if (selectedNumber == 0) {
                return null;
            }
        } catch (NumberFormatException e) {

        }
        if (campgroundChoice == null) {
            out.println("\n*** " + userInput + " is not a valid campground ***\n");
        }
        return campgroundChoice;
    }

    public Site getSiteSelectionFromUser(List<Site> sites) {
        Site siteChoice = null;
        String userInput = in.nextLine();
        try {
            int selectedNumber = Integer.valueOf(userInput);
            if (selectedNumber == 0) {
                return null;
            }
            for (Site site : sites) {
                if (selectedNumber == site.getSiteId()) { //Need check here for invalid site selection
                    siteChoice = site;
                }
            }
        } catch (NumberFormatException e) {
        }
        if (siteChoice == null) {  //Check is coming too late
//            out.println("\n*** " + userInput + " is not a valid site ***\n");
        }
        return siteChoice;
    }

    public String getReservationName() {
        return in.nextLine();
    }

    public LocalDate getDateFromUser() {
        String userInput = in.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy"); //Grabs a readable (US) input from user and format it for the computer
        LocalDate date;
        try {
            date = LocalDate.parse(userInput, formatter);  //Passes it out in sql format
        } catch (Exception e) {
            System.out.println("\n***Please enter a valid date***\n");
            return null;
        }
        return date;
    }

    private void displayParkOptions(List<Park> parks) {
        out.println();
        int number = 0;
        for (int i = 0; i < parks.size(); i++) {
            number = i + 1;
            out.println(number + ") " + parks.get(i).getName());
        }
        number++;
        out.println(number + ") Quit");
        out.flush();
    }

}