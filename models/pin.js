var mongoose = require('mongoose');
var pinSchema = new mongoose.Schema({
    id : {type: Number, unique:true, index:true, required:true},
    latitude : {type:Number, unique:false, required:true},
    longitude : {type:Number, unique:false, required:true},
    created: {type: Date, default:Date.now}
});

var Pin = module.exports = mongoose.model('Pin', pinSchema);