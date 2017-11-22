package src.city.cycle.canada;

/**
 * Created by rousseln on 2017-11-21.
 */

public class Comment {

    public String commentContent;
    public int commentID;
    public int userCommentID;
    public int score;

    public Comment(String commentContent, int commentID, int userCommentID, int score){
        this.commentContent = commentContent;
        this.commentID = commentID;
        this.userCommentID = userCommentID;
        this.score = score;
    }

}
