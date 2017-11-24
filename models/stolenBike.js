var mongoose = require('mongoose');

var Pin = {
    latitude : Number,
    longitude : Number
}

var stolenBikeSchema = new mongoose.Schema({
    photoId: {type:String, unique:true},
    dateStolen: {type:Date, required:true},
    location: {type:Pin, required:true},
    serialNumber: {type:String},
    description: {type:String, required:true},
    userId: {type:String, required:true},
    userName : {type:String, required:true},
    created: {type:Date, default:Date.now}
});

module.exports = mongoose.model('StolenBike', stolenBikeSchema);