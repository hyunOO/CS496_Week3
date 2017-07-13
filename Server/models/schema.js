var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var userSchema = new Schema({
	id: String, // primary key
	department: String,
	circle: String
	hobby: String
}, {versionKey: false});

var roomSchema = new Schema({
	title: String,
	makerId: String, 
	mealType: String,
	maxUser: Number,
	currentUser: Number,
	userList: Array
}, {versionKey: false})

var messageSchema = new Schema({
	from: String,
	to: String,
	name: String,
	roomId: String,
	fromRead: Boolean,
	toRead: Boolean,
	result: Boolean
}, {versionKey: false})

module.exports = {
	User: mongoose.model('user', userSchema),
	Room: mongoose.model('room', roomSchema),
	Message: mongoose.model('message', messageSchema),
}

