var mongoose = require('mongoose');
var postSchema = new mongoose.Schema({
    userId : {type:String, required:true},
    title : {type:String, required:true},
    content : {type:String, required:true},
    created : {type:Date, default:Date.now},
    score : {type:Number, default:0}
});

module.exports = mongoose.model('Post', postSchema);