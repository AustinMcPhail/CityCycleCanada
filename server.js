var express = require('express');
var bodyParser = require('body-parser');
var mongoose = require('mongoose');
var app = express();

//Connect to Mongoose
//mongoose.connect('mongodb://localhost/');
//var db = mongoose.connection;

app.get('/hello', function(req, res){
    console.log('Route hit.');
    object = {
        "message": "Hello from the other side."
    }
    res.send(object);
});

app.listen(3000, function(){
    console.log('App is listening on port 3000!')
});