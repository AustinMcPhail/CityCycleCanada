var mongoose = require('mongoose');
var postSchema = new mongoose.Schema({
    userId : {type:String, required:true},
    userName : {type:String, required:true},
    title : {type:String, required:true},
    content : {type:String, required:true},
    created : {type:Date, default:Date.now},
    score : {type:Number, default:0}
});

module.exports = mongoose.model('Post', postSchema);

module.exports.newPost = function(post, callback){
    Post.create(post, callback);
}

module.exports.getPosts = function(callback, limit){
    Post.find({}, {userId: 1, userName: 1, title: 1, content: 1, score: 1, created: 1}, callback).limit(limit);
}

module.exports.getPostById = function(id, callback, limit){
    Post.find({_id: id}, {userId: 1, userName: 1, title: 1, content: 1, score: 1, created: 1}, callback).limit(limit);
}


module.exports.getUserPosts = function(id, callback, limit) {
    console.log(id);
    Post.find({userId: id}, {userId: 1, title: 1, userName: 1, content: 1, score: 1, created: 1}, callback).limit(limit);
}