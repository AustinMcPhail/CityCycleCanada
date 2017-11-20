var mongoose = require('mongoose');
var pinSchema = new mongoose.Schema({
    id : {type: Integer, unique:true, index:true, required:true},
    latitude : {type:Double, unique:false, required:true},
    longitude : {type:Double, unique:false,
    required:true},
    created: {type: Date, default:Date.now}
});

mongoose.model('Pin', pinSchema);