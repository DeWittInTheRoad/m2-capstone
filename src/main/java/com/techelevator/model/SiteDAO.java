package com.techelevator.model;



import java.time.LocalDate;
import java.util.List;

public interface SiteDAO {
    List<Site> getAvailableSites(Campground campground, LocalDate fromDate, LocalDate toDate);
    List<Site> getAvailableSites(Campground campground, LocalDate fromDate, LocalDate toDate, Integer maxOccupancy, Integer maxRvLength, boolean utilities, boolean accessible);
}

