var express = require('express');
var bodyParser = require('body-parser');
var mongoose = require('mongoose');
var cors = require('cors');
var app = express();
var router = express.Router();


//Models
Comment = require('./models/comment');
Post = require('./models/post');
StolenBike = require('./models/stolenBike');
Upvote = require('./models/upvote');
Downvote = require('./models/downvote');

//Middleware
app.use(bodyParser.urlencoded({
    extended: false
}))
app.use(bodyParser.json());
app.use(cors());


//Connect to Mongo Database
mongoose.connect('mongodb://localhost/cityCycleCanada');


//ROUTES

//Test Route
app.get('/', function(req, res){
    res.send("Hello");
});

//Stolen Bike Routes


//Stolen Bike Routes
app.get('/stolenBikes', function(req, res){
    console.log('Stolen Bikes route hit.')
    StolenBike.getReports(function(err, reports){
        if (err){
            throw err;
            res.send('error');
        }
        else {
            res.send(reports);
        }
    })
});

app.get('/stolenBikesMap', function(req, res){
    console.log('Stolen Bikes Map route hit.')
    StolenBike.getReports(function(err, reports){
        if (err){
            throw err;
            res.send('error');
        }
        else {
            res.json(reports);
        }
    })
});

app.post('/stolenBikes/newReport', function(req, res){
    console.log('New Report route hit!');
    var serialNumber = req.body.serialNumber;
    var description = req.body.description;
    var userId = req.body.userId;
    var userName = req.body.userName;
	var longitude = req.body.longitude;
	var latitude = req.body.latitude;
	var contact = req.body.contact;
	var address = req.body.address;
	
	var new_report = {
        userId: userId,
        userName: userName,
		serialNumber: serialNumber,
		description: description,
		longitude: longitude,
		latitude: latitude,
		contact : contact,
		address : address
    };
    
    StolenBike.newReport(new_report, function(err, post){
        if(err){
            console.error(err);
            res.send('error');
        }
        else{
            console.log('success');
            res.send("success");
        }
    })
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
    
    var success = [
        {
            "message" : "Success!"
        }
    ]
    
    Post.newPost(new_post, function(err, post){
        if(err){
            console.error(err);
            res.send('error');
        }
        else{
            console.log('success');
            res.send(post._id);
        }
    })
});

app.post('/forum/post', function(req, res){
    var postId = req.body[0].postId.replace('"','');
    postId = postId.replace('"','');
    Post.getPostById(postId, function(err, post){
        if(err){
            throw err;
            res.send('error');
        }
        else {
            res.send(post);
        }
    })
});

app.post('/forum/post/upvote', function(req, res) {
	console.log('Upvote Route Hit!')
	var postId = req.body.postId;
	var userId = req.body.userId;
	
	console.log(postId);
	console.log(userId);
	
	Upvote.findByPostAndUser(userId, postId, function(err, upvotes){
		if(err){
			console.error(err);
			throw err;
		}
		else if (upvotes.length == 0) {
			console.log('1');
			Upvote.newUpvote(userId, postId, function(err, newvote){
				if(err){
					console.error(err);
					throw err;
				}
				else {
					console.log('2');
					Post.upvote(userId, function(err, post){
						console.log(post);
						if(err){
							console.log(err);
							console.error(err);
							throw err;
						}
						else {
							console.log('3');
							Downvote.findByPostAndUser(userId, postId, function(err, downvotes){
								if(err){
									console.error(err);
									throw err;
								}
								else if(downvotes.length != 0) {
									console.log('4');
									Downvote.deleteVote(userId, postId, function(err){
										if(err){
											console.error(err);
											throw err;
										}
										else {
											console.log('5');
											Post.upvote(userId, function(err, post){
												if(err){
													console.error(err);
													throw err;
												}
												else {
													console.log('upvoted');
													console.log(post);
													res.send('upvoted');
												}
											})
										}
									})
								}
								else {
									console.log('upvoted');
									console.log(post);
									res.send('upvoted');
								}
							})
						}
					})
				}
			})
		}
		else {
			console.log('Exiting upvote for that user and post');
			res.send('error');
		}
	});
});

app.post('/forum/post/downvote', function(req, res) {
	console.log('Downvote Route Hit!')
	var postId = req.body.postId;
	var userId = req.body.userId;
	
	console.log(postId);
	console.log(userId);
	
	Downvote.findByPostAndUser(userId, postId, function(err, downvotes){
		if(err){
			console.error(err);
			throw err;
		}
		else if (downvotes.length == 0) {
			console.log('1');
			Downvote.newDownvote(userId, postId, function(err, newvote){
				if(err){
					console.error(err);
					throw err;
				}
				else {
					console.log('2');
					Post.downvote(userId, function(err, post){
						console.log(post);
						if(err){
							console.log(err);
							console.error(err);
							throw err;
						}
						else {
							console.log('3');
							Upvote.findByPostAndUser(userId, postId, function(err, upvotes){
								if(err){
									console.error(err);
									throw err;
								}
								else if(upvotes.length != 0) {
									console.log('4');
									Upvote.deleteVote(userId, postId, function(err){
										if(err){
											console.error(err);
											throw err;
										}
										else {
											console.log('5');
											Post.downvote(userId, function(err, post){
												if(err){
													console.error(err);
													throw err;
												}
												else {
													res.send('upvoted');
												}
											})
										}
									})
								}
								else {
									res.send('upvoted');
								}
							})
						}
					})
				}
			})
		}
		else {
			console.log('Exiting upvote for that user and post');
			res.send('error');
		}
	});
});

app.post('/forum/post/hasUpvoted', function(req, res) {
	console.log('HasUpvoted Route hit!');
	var postId = req.body.postId;
	var userId = req.body.userId;
	
	Upvote.findByPostAndUser(userId, postId, function(err, upvote){
		if(upvote.length == 0){
			res.send('false');
		} else {
			res.send('true');
		}
	});
	
});

app.post('/forum/post/hasDownvoted', function(req, res) {
	console.log('HasDownvoted Route hit!');
	var postId = req.body.postId;
	var userId = req.body.userId;
	
	Downvote.findByPostAndUser(userId, postId, function(err, downvote){
		if(downvote.length == 0){
			res.send('false');
		} else {
			res.send('true');
		}
	});
	
});

app.post('/forum/post/newComment', function(req, res){
    console.log('New Comment route hit!');
    var postId = req.body.postId;
    var content = req.body.content;
    var userId = req.body.userId;
    var userName = req.body.userName;
    
    var new_comment = {
        userId: userId,
        postId: postId,
        content: content,
        userName: userName
    };
    
    Comment.newComment(new_comment, function(err, comment){
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

app.post('/forum/post/comments', function(req, res){
    console.log('Post Comments route hit.')
    var postId = req.body[0].postId.replace('"','');
    postId = postId.replace('"','');
    Comment.getCommentsByPostId(postId, function(err, comments){
        if (err){
            throw err;
            res.send('error');
        }
        else {
            res.send(comments);
        }
    })
});



app.listen(3000, '0.0.0.0', function(){
    console.log('App is listening on port 3000!')
});