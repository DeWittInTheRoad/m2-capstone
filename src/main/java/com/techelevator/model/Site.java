package com.techelevator.model;

public class Site {
    private Long siteId;
    private Long campgroundId;
    private Integer siteNumber;
    private Integer maxOccupancy;
    private boolean accessible;
    private Integer maxRvLength;
    private boolean utilities;

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    public Long getCampgroundId() {
        return campgroundId;
    }

    public void setCampgroundId(Long campgroundId) {
        this.campgroundId = campgroundId;
    }

    public Integer getSiteNumber() {
        return siteNumber;
    }

    public void setSiteNumber(Integer siteNumber) {
        this.siteNumber = siteNumber;
    }

    public Integer getMaxOccupancy() {
        return maxOccupancy;
    }

    public void setMaxOccupancy(Integer maxOccupancy) {
        this.maxOccupancy = maxOccupancy;
    }

    public boolean isAccessible() {

        return accessible;
    }

    public void setAccessible(boolean accessible) {
        this.accessible = accessible;
    }

    public String isAccessibleToString(boolean accessible) {
        return (accessible) ? "Yes" : "No";
    }


    public Integer getMaxRvLength() {
        return maxRvLength;
    }

    public void setMaxRvLength(Integer maxRvLength) {
        this.maxRvLength = maxRvLength;
    }

    public String maxRVToString(Integer maxRvLength) {
        return (maxRvLength == null || maxRvLength == 0) ? "N/A" : maxRvLength.toString() + " ft.";

    }

    public boolean isUtilities() {
        return utilities;
    }

    public void setUtilities(boolean utilities) {
        this.utilities = utilities;
    }

    public String isUtilitiesToString(boolean utilities) {
        return (utilities) ? "Yes" : "N/A";
    }

}