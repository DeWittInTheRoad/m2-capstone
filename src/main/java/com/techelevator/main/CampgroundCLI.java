package com.techelevator.main;

import com.techelevator.model.*;
import com.techelevator.model.jdbc.JDBCCampgroundDAO;
import com.techelevator.model.jdbc.JDBCParkDAO;
import com.techelevator.model.jdbc.JDBCReservationDAO;
import com.techelevator.model.jdbc.JDBCSiteDAO;
import com.techelevator.model.AdvancedSearchHandler;
import com.techelevator.view.FormattedDisplay;
import com.techelevator.view.Menu;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.List;

public class CampgroundCLI {

    private static final String PARKS_INFO_INTERFACE_VIEW_CAMPGROUNDS = "View Campgrounds";
    private static final String PARKS_INFO_INTERFACE_SEARCH_RESERVATION = "Search For Reservation";
    private static final String PARKS_INFO_INTERFACE_ADVANCED_SEARCH = "Advanced Search";
    private static final String PARKS_INFO_INTERFACE_RETURN = "Return to Previous Screen";
    private static final String[] PARKS_INFO_INTERFACE = new String[]{PARKS_INFO_INTERFACE_VIEW_CAMPGROUNDS, PARKS_INFO_INTERFACE_SEARCH_RESERVATION, PARKS_INFO_INTERFACE_ADVANCED_SEARCH, PARKS_INFO_INTERFACE_RETURN};

    private static final String PARK_CAMPGROUNDS_SEARCH_RESERVATIONS = "Search For Available Reservation";
    private static final String PARK_CAMPGROUNDS_RETURN = "Return to Previous Screen";
    private static final String[] PARK_CAMPGROUNDS = new String[]{PARK_CAMPGROUNDS_SEARCH_RESERVATIONS, PARK_CAMPGROUNDS_RETURN};

    private final Menu menu;
    private final ParkDAO parkDAO;
    private final CampgroundDAO campgroundDAO;
    private final SiteDAO siteDAO;
    private final ReservationDAO reservationDAO;
    private final FormattedDisplay formattedDisplay;
    private final AdvancedSearchHandler ash;
    private boolean isAdvancedSearch;


    private CampgroundCLI(DataSource datasource) {
        menu = new Menu(System.in, System.out);

        parkDAO = new JDBCParkDAO(datasource);
        campgroundDAO = new JDBCCampgroundDAO(datasource);
        siteDAO = new JDBCSiteDAO(datasource);
        reservationDAO = new JDBCReservationDAO(datasource);
        formattedDisplay = new FormattedDisplay();
        ash = new AdvancedSearchHandler();
    }

    public static void main(String[] args) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres1");

        CampgroundCLI application = new CampgroundCLI(dataSource);
        application.run();
    }

    private void run() {

        displayBanner();

        while (true) {

            printHeading("Select a park for further details");

            List<Park> allAvailableParksList = parkDAO.getAllAvailableParks();

            Park parkChoice = menu.getParkFromInput(allAvailableParksList);
            handleParkInformationScreen(parkChoice);
        }
    }

    private void handleParkInformationScreen(Park park) {

        printHeading(park.getName() + " National Park");
        formattedDisplay.display(park);
        List<Campground> allAvailableCampgroundsList = campgroundDAO.getAvailableCampgrounds(park);

        topOfMenu:
        while (true) {

            printHeading("Select a Command");
            String choice = (String) menu.getChoiceFromOptions(PARKS_INFO_INTERFACE);
            switch (choice) {
                case PARKS_INFO_INTERFACE_VIEW_CAMPGROUNDS:
                    handleViewCampgrounds(park, allAvailableCampgroundsList);
                    break;
                case PARKS_INFO_INTERFACE_SEARCH_RESERVATION:
                    handleSearchForCampgroundReservation(park, allAvailableCampgroundsList);
                    System.out.println();
                    break;
                case PARKS_INFO_INTERFACE_ADVANCED_SEARCH:
                    isAdvancedSearch = true;
                    System.out.println();
                    handleSearchForCampgroundReservation(park, allAvailableCampgroundsList);
                case PARKS_INFO_INTERFACE_RETURN:
                    break topOfMenu;
            }
        }
    }


    private void handleViewCampgrounds(Park park, List<Campground> campgroundList) {
        formattedDisplay.display(park, campgroundList);

        while (true) {

            String choice = (String) menu.getChoiceFromOptions(PARK_CAMPGROUNDS);
            if (choice.equals(PARK_CAMPGROUNDS_SEARCH_RESERVATIONS)) {
                handleSearchForCampgroundReservation(park, campgroundList);
            } else if (choice.equals(PARK_CAMPGROUNDS_RETURN)) {
                break;
            }
        }
    }

    private void handleSearchForCampgroundReservation(Park park, List<Campground> campgroundList) {
        Campground campgroundChoice;
        LocalDate arrivalDate = null;
        LocalDate departureDate = null;

        while (true) {
            formattedDisplay.display(park, campgroundList);
            System.out.println("Which campground (enter 0 to cancel)? ");
            campgroundChoice = menu.getCampgroundSelectionFromUser(campgroundList);
            if (campgroundChoice == null) {
                break;
            }
            while (arrivalDate == null) {
                System.out.println("What is the arrival date (MM/DD/YYYY)? ");

                arrivalDate = menu.getDateFromUser();

            }
            while (departureDate == null || departureDate.isBefore(arrivalDate)) {
                System.out.println("What is the departure date(MM/DD/YYYY)? ");
                departureDate = menu.getDateFromUser();
            }

            if (isAdvancedSearch) ash.getUserInputs();

            handleMakingReservation(campgroundChoice, arrivalDate, departureDate, isAdvancedSearch);
        }
    }


    private void handleMakingReservation(Campground campgroundChoice, LocalDate arrivalDate, LocalDate departureDate, boolean isAdvancedSearch) {

        boolean inMenu = true;

        List<Site> sitesInCampground = (isAdvancedSearch) ?
                siteDAO.getAvailableSites(campgroundChoice, arrivalDate, departureDate, ash.getMaxOccupancy(), ash.getMaxRvLength(), ash.isUtilities(), ash.isAccessible()) :
                siteDAO.getAvailableSites(campgroundChoice, arrivalDate, departureDate); //advanced switch

        formattedDisplay.display(sitesInCampground, campgroundChoice, arrivalDate, departureDate);
        Site siteChoice;
        String reservationName = null;

        if (sitesInCampground.size() == 0) {
            inMenu = false;
            System.out.println("No sites are available between those dates.");
        }

        while (inMenu) {
            System.out.println("Which site should be reserved (enter 0 to cancel)? "); //if site isnt available needs check
            siteChoice = menu.getSiteSelectionFromUser(sitesInCampground);
            if (siteChoice == null) {
                System.out.println("Invalid Site Selection");
                return;
            }
            if (campgroundChoice == null) {
                break;
            }
            while (reservationName == null) {
                System.out.println("What name should the reservation be made under? ");
                reservationName = menu.getReservationName();
                if (reservationName != null) {
                    reservationDAO.bookReservation(siteChoice, arrivalDate, departureDate, reservationName);
                    Reservation reservation = reservationDAO.getBookedReservation(siteChoice, arrivalDate, departureDate, reservationName);
                    String reservationId = reservation.getReservationId().toString();
                    System.out.println("The reservation has been made and the confirmation id is " + reservationId + ".");
                    run();
                } else {
                    System.out.println("Please enter a name!\n");
                }
            }
        }

    }

    private void displayBanner() {
        System.out.println("______________________________________________________________________________________________");
        System.out.println("	     _  _   __   ____ _   __    _  _ __   _       ___    __     ___   _   _          ");
        System.out.println("	    ||\\|| //__\\\\   ||  || |  | ||\\||  /__\\  ||     ||__|  /__\\  ||_||  || //          ");
        System.out.println("	    || \\| ||  ||  ||  || |__| || \\| ||  ||  ||__   ||    ||  ||  || \\  || \\          ");
        System.out.println(" 	            __    ___  __  _   ___  _   _ _____  _  __   ___   ___  ___                 ");
        System.out.println(" 	          /__\\  || || || ||  ||__ ||\\ ||  ||  ||  ||  ||_|| ||__ ||__                 ");
        System.out.println(" 	          ||  ||  ||_// \\//  ||__ || \\||  ||   \\__//  || \\ ||__  ___|                ");
        System.out.println("______________________________________________________________________________________________");

    }


    private void printHeading(String headingText) {
        System.out.println("\n" + headingText);
        for (int i = 0; i < headingText.length(); i++) {
            System.out.print("-");
        }
        System.out.println();
    }

}



