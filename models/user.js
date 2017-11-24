var mongoose = require('mongoose');
var userSchema = new mongoose.Schema({
    userId : {type:String, required:true, unique:true, index:true}
});

var User = module.exports = mongoose.model('User', userSchema);

module.exports.getUsers = function(callback, limit){
    User.find(callback).limit(limit);
}

module.exports.addUser = function(user, callback){
    User.create(user, callback);
}