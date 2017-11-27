package src.city.cycle.canada;

import java.util.Date;

import city.cycle.canada.StolenBike;



public class StolenBikeReport {
    String stolenBikeReportID;
    //int pictureID;

    String userName;
    String serialNumber;
    String latitude;
    String longitude;
    String contact;
    String description;

    String stolenDate;

    public StolenBikeReport(String stolenBikeReportID, String userName, String serialNumber, String latitude, String longitude,  String contact, String description, String stolenDate){
        this.stolenBikeReportID = stolenBikeReportID;
        //this.pictureID = pictureID;
        this.userName = userName;
        this.serialNumber = serialNumber;
        this.latitude = latitude;
        this.longitude = longitude;
        this.contact = contact;
        this.description = description;
        this.stolenDate = stolenDate;
    }


}
