var express = require('express');
var bodyParser = require('body-parser');
var mongoose = require('mongoose');
var app = express();
var router = express.Router();

//Connect to Mongoose
//mongoose.connect('mongodb://localhost/');
//var db = mongoose.connection;

router.get('/hello', function(req, res){
    console.log('Route hit.');
    object = {
        "message": "Hello from the other side."
    }
    res.send(object);
});


//Stolen Bike Routes
app.route('/stolenBikes')
    .get(function(req, res){
        res.send('Getting location of all stolen Bikes.');
    })
    
    .post(function(req, res){
        res.send('Submitting location of new stolen bike');
    })


//Forum Routes
app.route('/forum')
    .get(function(req, res){
        res.send('Getting all forum posts.');
    })
    .post(function(req, res){
        res.send('Posting to the forum.');
    })

app.use('/', router);

app.listen(8080, function(){
    console.log('App is listening on port 3000!')
});