var express = require('express');
var bodyParser = require('body-parser');
var mongoose = require('mongoose');
var app = express();
var router = express.Router();

//Models
Pin = require('./models/pin');
Comment = require('./models/comment');
Post = require('./models/post');
StolenBike = require('./models/stolenBike');
User = require('./models/user');


app.use(bodyParser.urlencoded({
    extended: false
}))
app.use(bodyParser.json());


//Connect to Mongoose
mongoose.connect('mongodb://localhost/cityCycleCanada');
var db = mongoose.connection;

function makeid() {
  var text = "";
  var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

  for (var i = 0; i < 5; i++)
    text += possible.charAt(Math.floor(Math.random() * possible.length));

  return text;
}

router.get('/hello', function(req, res){
    console.log('Route hit.');
    object = {
        "message": "Hello from the other side."
    }
    res.send(object);
});

//Testing Only
app.post('/addTestUser', function(req, res){
    var id = req.body.userId;
    var user = {userId : id};
    User.addUser(user, function(err, user){
        if(err){
            console.error(err);
            res.send('error');
        }
        else{
            console.log('success')
            res.send('success');
        }
    }) 
})

app.get('/getTestUsers', function(req, res){
    User.getUsers(function(err, user){
        if(err)
            throw err;
        res.json(user)
    })
})



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