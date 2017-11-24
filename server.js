var express = require('express');
var bodyParser = require('body-parser');
var mongoose = require('mongoose');
var app = express();
var router = express.Router();

//Models
Comment = require('./models/comment');
Post = require('./models/post');
StolenBike = require('./models/stolenBike');

//Middleware
app.use(bodyParser.urlencoded({
    extended: false
}))
app.use(bodyParser.json());


//Connect to Mongo Database
mongoose.connect('mongodb://localhost/cityCycleCanada');


//ROUTES


//Stolen Bike Routes
app.get('/stolenBikes', function(req, res){
    
});

app.post('/stolenBikes/newReport', function(req, res){
    
});

app.post('/stolenBikes/myReports', function(req, res){
    
});

app.post('/stolenBikes/report', function(req, res){
    
});

//Forum Routes
app.get('/forum', function(req, res){
    console.log('Forum route hit.')
    Post.getPosts(function(err, posts){
        if (err){
            throw err;
            res.send('error');
        }
        else {
            res.send(posts);
        }
    })
});

app.post('/forum/myPosts', function(req, res){
    var userId = req.body.userId;
    Post.getUserPosts(userId, function(err, posts){
        if(err){
            throw err;
            res.send('error');
        }
        else {
            console.log(posts)
            res.send(posts)
        }
    })
});

app.post('/forum/newPost', function(req, res){
    console.log('Forum Post route hit!');
    var title = req.body.title;
    var content = req.body.content;
    var userId = req.body.userId;
    var userName = req.body.userName;
    
    var new_post = {
        userId: userId,
        title: title,
        content: content,
        userName: userName
    };
    
    Post.newPost(new_post, function(err, post){
        if(err){
            console.error(err);
            res.send('error');
        }
        else{
            console.log('success');
            res.send('success');
        }
    })
});

app.post('/forum/post', function(req, res){
    var postNum = req.body.postId;
    
    var string1 = 'ObjectId("';
    var string2 = '")';
    
    var postId = string1 + postNum + string2;
    
    Post.getPostById(postNum, function(err, post){
        if(err){
            throw err;
            res.sned('error');
        }
        else {
            res.send(post)
        }
    })
});

app.post('/forum/myComments', function(req, res){
    
});

app.post('/fourm/post/newComment', function(req, res){
    
});

app.post('/forum/post/comments', function(req, res){
    
});



app.listen(8080, '0.0.0.0', function(){
    console.log('App is listening on port 8080!')
});