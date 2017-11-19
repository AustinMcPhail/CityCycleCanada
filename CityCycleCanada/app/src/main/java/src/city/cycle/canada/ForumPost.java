package src.city.cycle.canada;

/**
 * Created by nicolas on 18/11/17.
 */

public class ForumPost {
    String title;
    int postID;
    int userPostID;
    int numOfComments;

    public ForumPost(String title, int postID, int userPostID, int numOfComments){
        this.title = title;
        this.postID = postID;
        this.userPostID = userPostID;
        this.numOfComments = numOfComments;
    }
}
