var mongoose = require('mongoose');

var Pin = {
    latitude : Number,
    longitude : Number
}

var stolenBikeSchema = new mongoose.Schema({
    photoId: {type:String, unique:true},
    dateStolen: {type:Date},
    location: {type:Pin},
    serialNumber: {type:String},
    description: {type:String},
    userId: {type:String},
    created: {type:Date, default:Date.now}
});

module.exports = mongoose.model('StolenBike', stolenBikeSchema);