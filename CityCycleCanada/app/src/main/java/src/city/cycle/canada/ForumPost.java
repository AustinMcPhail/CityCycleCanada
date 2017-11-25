package src.city.cycle.canada;

import java.util.Date;

/**
 * Created by nicolas on 18/11/17.
 */

public class ForumPost {
    public String title;
    public String postID;
    public String userPostID;
    public int numOfComments;
    public String postContents;
    public int postScore;
    public String userName;

    public String postDate;

    public ForumPost(String title, String postID, String userPostID, int numOfComments, int postScore, String userName, String postDate){
        this.title = title;
        this.postID = postID;
        this.userPostID = userPostID;
        this.numOfComments = numOfComments;
        this.postScore = postScore;
        this.userName = userName;
        this.postDate = postDate;
    }
}
