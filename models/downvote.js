var mongoose = require('mongoose');
var downvoteSchema = new mongoose.Schema({
    userId : {type:String, required:true},
    postId : {type:String, required:true}
});

module.exports = mongoose.model('Downvote', downvoteSchema);

module.exports.newDownvote = function(uId, pId, callback){
    Downvote.create({userId : uId, postId : pId}, callback);
}

module.exports.findByPostAndUser = function(uId, pId, callback){
	Downvote.find({userId: uId, postId: pId}, callback);
}

module.exports.deleteVote = function(uId, pId, callback){
	Downvote.deleteOne({userId: uId, postId: pId}, callback);
}