var mongoose = require('mongoose');
var commentSchema = new mongoose.Schema({
    postId : {type:String, required:true},
    userId : {type:String, required:true},
    userName : {type:String, required:true},
    content : {type:String, required:true},
    created : {type:Date, default:Date.now}
});

module.exports = mongoose.model('Comment', commentSchema);

module.exports.getCommentsByPostId = function(id, callback, limit){
    Comment.find({postId: id}, {userName: 1, content: 1, created: 1}, callback).limit(limit);
}

module.exports.newComment = function(comment, callback){
    Comment.create(comment, callback);
}