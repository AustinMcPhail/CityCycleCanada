var mongoose = require('mongoose');
var userSchema = new mongoose.Schema({
    userId = {type:String, required:true, unique:true, index:true}
});

mongoose.model('User', userSchema);