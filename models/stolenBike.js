var mongoose = require('mongoose');

var stolenBikeSchema = new mongoose.Schema({
    serialNumber: {type:String, required: true},
    description: {type:String, required:true},
    userId: {type:String, required:true},
    userName : {type:String, required:true},
    created: {type:Date, default:Date.now},
	longitude: {type:String, required: true},
	latitude: {type:String, required: true},
	address: {type:String, required: true},
	contact: {type:String, required: true}
});

module.exports = mongoose.model('StolenBike', stolenBikeSchema);

module.exports.newReport = function(report, callback){
    StolenBike.create(report, callback);
}

module.exports.getReports = function(callback, limit){
    StolenBike.find(callback).limit(limit);
}