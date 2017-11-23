package src.city.cycle.canada;

import java.util.Date;

import city.cycle.canada.StolenBike;

/**
 * Created by nicolas on 23/11/17.
 */

public class StolenBikeReport {
    int stolenBikeReportID;
    int pictureID;

    String userID;
    String serialNumber;
    String location;
    String description;

    Date stolenDate;

    public StolenBikeReport(int stolenBikeReportID, int pictureID, String userID, String serialNumber, String location, String description, Date stolenDate){
        this.stolenBikeReportID = stolenBikeReportID;
        this.pictureID = pictureID;

        this.userID = userID;
        this.serialNumber = serialNumber;
        this.location = location;
        this.description = description;

        this.stolenDate = stolenDate;
    }


}
