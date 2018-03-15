package com.techelevator.view;

import com.techelevator.model.Campground;
import com.techelevator.model.Park;
import com.techelevator.model.Site;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Locale;

public class FormattedDisplay {

    public void display(List<Site> sites, Campground campground, LocalDate arrivalDate, LocalDate departureDate) {
        System.out.println();
        System.out.println("Results Matching Your Search Criteria");

        String siteNumber;
        String occupancy;
        String accessible;
        String maxRVLength;
        String utility;
        String cost;

        StringBuilder siteString = new StringBuilder();
        Long daysBetween = ChronoUnit.DAYS.between(arrivalDate, departureDate);  //Get days between to pass to daily cost big decimal
        BigDecimal daysBetweenBD = new BigDecimal(daysBetween);

        siteString.append(String.format("%-9s %-13s %-13s %-13s %-13s %s", "Site No.", "Max Occup.", "Accessible?", "Max RV Length", "Utility", "Cost" + "\n"));

        for (Site site : sites) {
            siteNumber = site.getSiteId().toString();
            occupancy = site.getMaxOccupancy().toString();
            accessible = site.isAccessibleToString(site.isAccessible());
            maxRVLength = site.maxRVToString(site.getMaxRvLength());
            utility = site.isUtilitiesToString(site.isUtilities());
            cost = NumberFormat.getCurrencyInstance().format((campground.getDailyFee().multiply(daysBetweenBD)));

            siteString.append(String.format("%-9s %-13s %-13s %-13s %-13s %s", siteNumber, occupancy,
                    accessible, maxRVLength,
                    utility, cost + "\n"));
        }
        System.out.println(siteString);
    }

    public void display(Park park, List<Campground> campgrounds) {
        System.out.println();
        System.out.println(park.getName() + " National Park Campgrounds");
        System.out.println();

        String campName;
        String dateNames[] = {"", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        Integer openingDateInt;
        String openingDateString;
        Integer closingDateInt;
        String closingDateString;
        String dailyFee;
        int parkNumber = 0;
        StringBuilder campgroundsDisplayString = new StringBuilder();
        campgroundsDisplayString.append(String.format("%-5s %-25s %-15s %-13s %s", "", "Name", "Open", "Close", "Daily Fee" + "\n"));


        for (Campground campground : campgrounds) {
            campName = campground.getName();
            openingDateInt = Integer.parseInt(campground.getOpeningDate());
            openingDateString = dateNames[openingDateInt];
            closingDateInt = Integer.parseInt(campground.getClosingDate());
            closingDateString = dateNames[closingDateInt];
            dailyFee = NumberFormat.getCurrencyInstance().format(campground.getDailyFee());

            campgroundsDisplayString.append(String.format("%-5s %-25s %-15s %-13s %s", "#" + ++parkNumber, campName,
                    openingDateString, closingDateString, dailyFee + "\n"));
        }

        System.out.println(campgroundsDisplayString);
    }

    public void display(Park selectedPark) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy"); //US input format

        String location = selectedPark.getLocation();
        String established = selectedPark.getEstablishedDate().format(dateFormatter);
        String area = NumberFormat.getNumberInstance(Locale.US).format(selectedPark.getArea()); //add comma
        String visitors = NumberFormat.getNumberInstance(Locale.US).format(selectedPark.getVisitors());

        StringBuilder info = new StringBuilder();
        info.append(String.format("%-18s %s", "Location: ", location + "\n"));
        info.append(String.format("%-18s %s", "Established: ", established + "\n"));
        info.append(String.format("%-18s %s", "Area: ", area + " sq km\n"));
        info.append(String.format("%-18s %s", "Annual Visitors: ", visitors + "\n"));

        System.out.println();
        System.out.println(info);

        StringBuilder description = new StringBuilder(selectedPark.getDescription());
        int i = 0;
        while ((i = description.indexOf(" ", i + 60)) != -1) {
            description.replace(i, i + 1, "\n");
        }
        System.out.println(description.toString());
    }
}
