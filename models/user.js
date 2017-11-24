var mongoose = require('mongoose');

var User = module.exports = mongoose.model('User', userSchema);

module.exports.getUserNameById = function(id, callback, limit){
    User.find({userId: id}, {}. callback).limit(limit);
}