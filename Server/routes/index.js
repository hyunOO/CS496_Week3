// routes/index.js

module.exports = function(app, Models)
{
	// Check Whether Server is Alive
	app.get('/', function(req, res){
		console.log("request to /");
		res.render("index.html");
		console.log("   successfully rendered index.html");
	});

	// List Available Requests to Server
	app.get('/api', function(req, res){
		console.log("request to /api");
		res.render("list.html");
		console.log("   successfully rendered list.html");
	});

	// 현재 모든 user의 정보를 리턴한다.
	app.get('/user', function(req, res){
		console.log("request to /user");
		Models.User.find(function (err, users){
			if(err) return res.status(500).send({error: 'database failure'});
			res.json(users);
			console.log("   successfully responsed");
		})
	});

	// 해당 id에 맞는 user를 리턴한다.
	app.get('/user/id/:id', function(req, res){
		console.log("request to /user/id/"+req.params.id);
		Models.User.findOne({id: req.params.id}, function(err, user){
			if(err) return res.status(500).json({error: err});
			if(!user) return res.status(400).json({error: 'user not found'});
			res.json(user);
			console.log("   successfully responsed");
		})
	});

	// 현재 모든 room의 정보를 리턴한다.
	app.get('/room', function(req, res){
		console.log("request to /room");
		Models.Room.find(function (err, rooms){
			if(err) return res.status(500).send({error: 'database failure'});
			res.json(rooms);
			console.log("   successfully responsed");
		})
	});

	// 해당 title에 맞는 room들을 리턴한다.
	app.get('/room/title/:title', function(req, res){
		console.log("request to /room/title/"+req.params.title);
		Models.Room.find({title: req.params.title}, function(err, rooms){
			if(err) return res.status(500).json({error: err});
			res.json(rooms);
			console.log("   successfully responsed");
		})
	});

	// 해당 makerId가 만든 room들을 리턴한다.
	app.get('/room/makerId/:makerId', function(req, res){
		console.log("request to /room/makerId/"+req.params.makerId);
		Models.Room.find({makerId: req.params.makerId}, function(err, rooms){
			if(err) return res.status(500).json({error: err});
			res.json(rooms);
			console.log("   successfully responsed");
		})
	});

	// 해당 mealType에 속하는 room들을 리턴한다.
	app.get('/room/mealType/:mealType', function(req, res){
		console.log("request to /room/mealType/"+req.params.mealType);
		Models.Room.find({mealType: req.params.mealType}, function(err, rooms){
			if(err) return res.status(500).json({error: err});
			res.json(rooms);
			console.log("   successfully responsed");
		})
	});

	// 해당 user가 속해있는 room들을 리턴한다.
	app.get('/room/user/:user', function(req, res){
		console.log("request to /room/user/"+req.params.user);
		Models.Room.find(function(err, rooms){
			if(err) return res.status(500).json({error: err});
			var userRooms = [];
			rooms.forEach(function(room){
				var userArray = room.userList;
				userArray.forEach(function(user){
					if(req.params.user.localeCompare(user) == 0) userRooms.push(room);
				})
			})
			res.json(userRooms);
			console.log("   successfully responsed");
		})
	});

	app.get('/room/roomId/:roomId', function(req, res){
		console.log("request to /room/roomId"+req.params.roomId);
		Models.Room.findById(req.params.roomId, function(err, room){
			if(err) return res.status(500).json({error: 'database failure'});
			if(!room) return res.status(404).json({error: 'room not found'});
			res.json(room);
		});
	});

	// return userlist of a specific room
	app.get('/room/:roomId/userlist', function(req, res){
		console.log("request to /room/"+req.params.roomId+"/userlist")
		Models.Room.findOne({_id: req.params.roomId}, function(err, room){
			if (err) return res.status(500).json({error: err});
			if (!room) return res.status(404).json({error: "no such room exists"});
			Models.User.find({id: {$in: room.userList}}, function(err, users){
				if (err) return res.status(500).json({error: err});
				res.json(users);
			})
		})
	})

	// 현재 모든 message의 정보를 리턴한다.
	app.get('/message', function(req, res){
		console.log("request to /message");
		Models.Message.find(function (err, messages){
			if(err) return res.status(500).send({error: 'database failure'});
			res.json(messages);
			console.log("   successfully responsed");
		})
	});

	app.get('/message/bangjang/:bangjang', function(req, res){
		console.log("request to /message/bangjang/"+req.params.bangjang);
		Models.Message.find({bangjang: req.params.bangjang, hideB: false}, function(err, messages){
			if(err) return res.status(500).json({error: err});
			res.json(messages);
			console.log("   successfully responsed");
		})
	});

	app.get('/message/requester/:requester', function(req, res){
		console.log("request to /message/requester/"+req.params.requester);
		Models.Message.find({requester: req.params.requester, hideR: false}, function(err, messages){
			if(err) return res.status(500).send({error: err});
			res.json(messages);
			console.log("   successfully responsed");
		})
	});

	app.get('/message/specific/:roomId/:requester/:bangjangRead', function(req, res){
		console.log("request to /message/specific/"+req.params.roomId+"/"+req.params.requester+"/"+req.params.bangjangRead);
		Models.Message.findOne({roomId: req.params.roomId, requester: req.params.requester, 
			bangjangRead: req.params.bangjangRead}, function(err, message){
			if(err) return res.status(500).send({error: err});
			if(!message) {
				console.log("   message not found");
				return res.status(404).send({error: "message not found"});
			}
			res.json(message);
			console.log("   successfully responsed");
		})
	});

	//새로운 user를 하나 만든다.
	app.post('/user', function(req,res){
		console.log("request to /user");
		var user = new Models.User();
		user.id = req.body.id;
		user.department = req.body.department;
		user.circle = req.body.circle;
		user.hobby = req.body.hobby;
		user.tag = [];
		
		user.save(function(err){
			if(err){
				console.error(err);
				res.json({result : 0});
				return;
			}
			res.json({result : 1});
			console.log("   successfully responsed");
		});
	});

	// 새로운 room을 하나 만든다.
	app.post('/room', function(req, res){
		console.log("request to /room");
		var room = new Models.Room();
		room.title = req.body.title;
		room.makerId = req.body.makerId;
		room.closed = false;
		room.mealType = req.body.mealType;
		room.maxUser = req.body.maxUser;
		room.currentUser = 1;
		room.userList = [req.body.makerId];
	
		room.save(function(err)	{
			if(err){
				console.error(err);
				res.json({result : 0});
				return;
			}
			res.json({result : 1});
			console.log("   successfully responsed");
		});
	});

	//새로운 message를 하나 만든다.
	app.post('/message', function(req, res){
		console.log("request to /message");
		var message = new Models.Message();
		message.roomId = req.body.roomId;
		message.requester = req.body.requester;
		message.bangjang = req.body.bangjang;
		message.bangjangRead = req.body.bangjangRead;
		message.accept = req.body.accept;
		message.hideB = false;
		message.hideR = false;
		message.save(function(err){
			if(err){
				console.error(err);
				res.json({reslut : 0});
				return;
			}
			res.json({result : 1});
			console.log("   successfully responsed");
		});			
	});

	app.post('/user/:userId', function(req, res){
		console.log(req.body.department);
		console.log("request to /user/"+req.params.userId);
		Models.User.findOne({id: req.params.userId}, function(err, user){
			if(err) return res.status(500).json({error: 'database failure'});
			if(!user) return res.status(404).json({error: 'user not found'});
			if(req.body.id) user.id = req.body.id;
			if(req.body.department) user.department = req.body.department;
			if(req.body.circle) user.circle = req.body.circle;
			if(req.body.hobby) user.hobby = req.body.hobby;
			user.save(function(err){
				if(err) res.status(500).json({error: 'failed to update'});
				res.json({message: 'user updated'});
				console.log("   successfully responsed");
			});
		});
	});
	
	app.put('/room/:roomId', function(req, res){
		console.log("request to /room/"+req.params.roomId);
		Models.Room.findById(req.params.roomId, function(err, room){
			if(err) return res.status(500).json({error: 'database failure'});
			if(!room) return res.status(404).json({error: 'room not found'});
			if(req.body.title) room.title = req.body.title;
			if(req.body.mealType) room.mealType = req.body.mealType;
			if(req.body.maxUser) room.maxUser = req.body.maxUser;
			room.save(function(err){
				if(err) res.status(500).json({error: 'failed to update'});
				res.json({message: 'room updated'});
				console.log("   successfully responsed");
			});	
		});
	});

	app.post('/room/addUser/:roomId', function(req, res){
		console.log("request to /room/addUser/"+req.params.roomId);
		Models.Room.findById(req.params.roomId, function(err, room){
			if(err) return res.status(500).json({error: 'database failure'});
			if(!room) return res.status(404).json({error: 'room not found'});
			var userList = room.userList;
			userList.push(req.body.userId);
			room.currentUser += 1;
			if(room.currentUser == room.maxUser) room.closed = true;
			room.save(function(err){
				if(err) res.status(500).json({error: 'failed to update'});
				res.json({message: 'room updated'});
				console.log("   successfully responsed");
			});
		});
	});

	app.post('/message/:roomId/:requester', function(req, res){
		console.log("request to /message/"+req.params.roomId+"/"+req.params.requester);
		Models.Message.findOne({roomId: req.params.roomId, requester:req.params.requester}, function(err, message){
			if(err) return res.status(500).json({error: 'database failure'});
			if(!message) return res.status(404).json({error: 'message not found'});
			if(req.body.requesterRead) message.requesterRead = req.body.requesterRead;
			if(req.body.bangjangRead) message.bangjangRead = req.body.bangjangRead;
			if(req.body.accept) message.accept = req.body.accept;
			if(req.body.hideR) message.hideR = req.body.hideR;
			if(req.body.hideB) message.hideB = req.body.hideB;
			message.save(function(err){
				if(err) res.status(500).json({error : 'failed to update'});
				res.json({message: 'message update'});
				console.log("   successfully responsed");
			});
		});
	});
}

