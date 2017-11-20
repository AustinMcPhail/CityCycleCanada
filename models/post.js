var mongoose = require('mongoose');
var postSchema = new mongoose.Schema({
    postId : {type:Integer, required:true, unique:true, index:true},
    userId : {type:String, required:true},
    title : {type:String, required:true},
    content : {type:String, required:true},
    created : {type:Date, default:Date.now},
    score : {type:Integer, default:0}
});

mongoose.model('Post', postSchema);