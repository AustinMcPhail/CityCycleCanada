package src.city.cycle.canada;

import java.util.Date;

/**
 * Created by nicolas on 18/11/17.
 */

public class ForumPost {
    public String title;
    public int postID;
    public int userPostID;
    public int numOfComments;
    public String postContents;
    public int postScore;
    public String userName;

    Date postDate;

    public ForumPost(String title, int postID, int userPostID, int numOfComments, int postScore, String userName, Date postDate){
        this.title = title;
        this.postID = postID;
        this.userPostID = userPostID;
        this.numOfComments = numOfComments;
        this.postScore = postScore;
        this.userName = userName;
        this.postDate = postDate;
    }
}
