package com.techelevator.model;


import java.util.Scanner;

public class AdvancedSearchHandler {
    private Integer maxOccupancy = -1;
    private Integer maxRvLength = -1;
    private boolean accessible;
    private boolean utilities;

    public int getMaxOccupancy() {
        return maxOccupancy;
    }


    public int getMaxRvLength() {
        return maxRvLength;
    }

    public boolean isAccessible() {
        return accessible;
    }

    public boolean isUtilities() {
        return utilities;
    }


    public void getUserInputs(){
        Scanner in = new Scanner(System.in);
        String accessibleCheck = "";
        String utilitiesCheck = "";

        while (maxOccupancy < 0) {
            System.out.println("How many people will be staying? (press 0 if you do not care.)");
            String userInput = in.nextLine();
            if (userInput.matches("\\d+")) {  //Checks to make sure it is an int
                maxOccupancy = Integer.valueOf(userInput);
            }

        }

        while (maxRvLength < 0) {
            System.out.println("What size RV do you have? (press 0 if you are not bringing an RV)");
            String userInput = in.nextLine();
            if (userInput.matches("\\d+")) {  //Checks to make sure it is an int
                maxRvLength = Integer.valueOf(userInput);
            }

        }

        while (!accessibleCheck.toUpperCase().equals("Y") && !accessibleCheck.toUpperCase().equals("N")) {
            System.out.println("Do you need wheelchair access? (Y/N)");
            accessibleCheck = in.nextLine().toUpperCase();

            accessible = (accessibleCheck.equals("Y"));

        }

        while (!utilitiesCheck.toUpperCase().equals("Y") && !utilitiesCheck.toUpperCase().equals("N")) {
            System.out.println("Do you need utilities access? (Y/N)");
            utilitiesCheck = in.nextLine().toUpperCase();

            utilities = (utilitiesCheck.equals("Y"));

        }
    }
}
