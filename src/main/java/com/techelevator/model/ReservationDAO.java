package com.techelevator.model;

import java.time.LocalDate;
import java.util.List;

public interface ReservationDAO {

    List<Reservation> getReservations(Site site);
    void bookReservation(Site siteChoice, LocalDate arrivalDate, LocalDate departureDate, String nameInput);
    Reservation getBookedReservation(Site siteChoice, LocalDate arrivalDate, LocalDate departureDate, String nameInput) ;


}