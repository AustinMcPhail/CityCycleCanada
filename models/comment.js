var mongoose = require('mongoose');
var commentSchema = new mongoose.Schema({
    commentId : {type:Integer, required:true, unique:true, index:true},
    postId : {type:Integer, required:true},
    userId : {type:String, required:true},
    content : {type:String, required:true},
    created : {type:Date, default:Date.now},
    score : {type:Integer, default:0}
});

mongoose.model('Comment', commentSchema);