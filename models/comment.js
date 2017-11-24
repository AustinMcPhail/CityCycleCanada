var mongoose = require('mongoose');
var commentSchema = new mongoose.Schema({
    postId : {type:Number, required:true},
    userId : {type:String, required:true},
    userName : {type:String, required:true},
    content : {type:String, required:true},
    created : {type:Date, default:Date.now},
    score : {type:Number, default:0}
});

module.exports = mongoose.model('Comment', commentSchema);

module.exports.getCommentByPostId = function(id, callback, limit){
    Comment.find({postId: id}, {userId: 1, userName: 1, content: 1, score: 1}, callback).limit(limit);
}