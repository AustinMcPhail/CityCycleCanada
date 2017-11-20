var mongoose = require('mongoose');
var Pin = require('./pin.js');

var stolenBikeSchema = new mongoose.Schema({
    id : {type:Integer, unique:true, index:true, required:true},
    photoId: {type:String, unique:true},
    dateStolen: {type:Date},
    location: {type:Pin},
    serialNumber: {type:String},
    description: {type:String},
    userId: {type:String},
    created: {type:Date, default:Date.now}
});

mongoose.model('StolenBike', stolenBikeSchema);