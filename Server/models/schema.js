var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var userSchema = new Schema({
	id: String, // primary key
	department: String,
	circle: String,
	hobby: String,
	tag: Array
}, {versionKey: false});

var roomSchema = new Schema({
	title: String,
	makerId: String, 
	closed: Boolean, 
	mealType: String,
	maxUser: Number,
	currentUser: Number,
	userList: Array
}, {versionKey: false})

var messageSchema = new Schema({
	//roomId, requester, bangjangRead are primary key
	roomId: String,
	requester: String,
	bangjang: String,
	bangjangRead: Boolean,
	accept: Boolean,
	hideB: Boolean,
	hideR: Boolean
}, {versionKey: false})

module.exports = {
	User: mongoose.model('user', userSchema),
	Room: mongoose.model('room', roomSchema),
	Message: mongoose.model('message', messageSchema),
}

