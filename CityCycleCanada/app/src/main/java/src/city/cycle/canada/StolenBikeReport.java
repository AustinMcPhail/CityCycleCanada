package src.city.cycle.canada;

import java.util.Date;

import city.cycle.canada.StolenBike;



public class StolenBikeReport {
    int stolenBikeReportID;
    int pictureID;

    String userID;
    String serialNumber;
    String location;
    String contact;
    String description;

    String stolenDate;

    public StolenBikeReport(int stolenBikeReportID, int pictureID, String userID, String serialNumber, String location, String contact, String description, String stolenDate){
        this.stolenBikeReportID = stolenBikeReportID;
        this.pictureID = pictureID;

        this.userID = userID;
        this.serialNumber = serialNumber;
        this.location = location;
        this.contact = contact;
        this.description = description;

        this.stolenDate = stolenDate;
    }


}
