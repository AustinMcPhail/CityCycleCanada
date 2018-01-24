var mongoose = require('mongoose');
var upvoteSchema = new mongoose.Schema({
    userId : {type:String, required:true},
    postId : {type:String, required:true}
});

module.exports = mongoose.model('Upvote', upvoteSchema);

module.exports.newUpvote = function(uId, pId, callback){
    Upvote.create({userId : uId, postId : pId}, callback);
}

module.exports.findByPostAndUser = function(uId, pId, callback){
	Upvote.find({userId: uId, postId: pId}, callback);
}

module.exports.deleteVote = function(uId, pId, callback){
	Upvote.deleteOne({userId: uId, postId: pId}, callback);
}