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
	state: Boolean, 
	mealType: String,
	maxUser: Number,
	currentUser: Number,
	userList: Array,
}, {versionKey: false})

var messageSchema = new Schema({
	roomId: String,
	requester: String, // to from 보다 
	bangjang: String, // request bangjang 이 직관적이여서 바꿨어!
	requesterRead: Boolean,
	bangjangRead: Boolean,
	state: Boolean,
	hideR: Boolean,
	hideB: Boolean
}, {versionKey: false})

module.exports = {
	User: mongoose.model('user', userSchema),
	Room: mongoose.model('room', roomSchema),
	Message: mongoose.model('message', messageSchema),
}

