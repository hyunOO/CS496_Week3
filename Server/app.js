// app.js

// [LOAD PACKAGES]
let express = require('express');
let app = express();
var server = require('http').createServer(app);
var io = require('socket.io')(server);
// let io = require('socket.io')(app);
let bodyParser = require('body-parser');
let mongoose = require('mongoose');

// [CONFIGURE APP TO USE bodyParser]
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());
	
// [CONFIGURE SERVER PORT]
let port = process.env.PORT || 10001;


app.set('views', __dirname + '/views');
app.set('view engine', 'ejs');
app.engine('html', require('ejs').renderFile);

// [ CONFIGURE mongoose ]
// CONNECT TO MONGODB SERVER
let db = mongoose.connection;
db.on('error', console.error);
db.once('open', function(){
  // CONNECTED TO MONGODB SERVER
  console.log("Connected to mongod server");
});

mongoose.connect('mongodb://localhost/CS496_Week3');

// DEFINE MODEL
let Models = require('./models/schema');

// [CONFIGURE ROUTER]
let router = require('./routes')(app, Models)

// [RUN SERVER]
server.listen(port, function(){
 console.log("Express server has started on port " + port)
});


var numUsers = 0;

io.on('connection', function (socket) {

	console.log("-----socket connected");

	var addedUser = false;

  // when the client emits 'new message', this listens and executes
  socket.on('new message', function (data) {
    // we tell the client to execute 'new message'
    console.log("-----socket got a new message");
    socket.broadcast.emit('new message', {
    	username: socket.username,
    	message: data
    });
  });

  // when the client emits 'add user', this listens and executes
  socket.on('add user', function (username) {
  	console.log("-----socket add user");

  	if (addedUser) return;

    // we store the username in the socket session for this client
    socket.username = username;
    ++numUsers;
    addedUser = true;
    socket.emit('login', {
    	numUsers: numUsers
    });
    // echo globally (all clients) that a person has connected
    socket.broadcast.emit('user joined', {
    	username: socket.username,
    	numUsers: numUsers
    });
  });

  // when the client emits 'typing', we broadcast it to others
  socket.on('typing', function () {
  	console.log("-----socket typing");
  	socket.broadcast.emit('typing', {
  		username: socket.username
  	});
  });

  // when the client emits 'stop typing', we broadcast it to others
  socket.on('stop typing', function () {
  	console.log("-----socket stop typing");
  	socket.broadcast.emit('stop typing', {
  		username: socket.username
  	});
  });

  // when the user disconnects.. perform this
  socket.on('disconnect', function () {
  	console.log("-----socket disconnected");
  	if (addedUser) {
  		--numUsers;

      // echo globally that this client has left
      socket.broadcast.emit('user left', {
      	username: socket.username,
      	numUsers: numUsers
      });
    }
  });
});
