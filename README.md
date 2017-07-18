# [CS496_Week3]
## Project Name: 
### Team: [HyunWoo Kang](https://github.com/hyunOO), [SeungMin Lee](https://github.com/iamlsm97)

## 1. Client
- Android (java, xml)
#### Log in and sign in
- At first, user has to login with his account. If user don't have an account, then he can make new account with new id, password, department in university, circle, and concerns
- After login, app supports three functionality 
#### TabA [List of Rooms]
- User can see all of rooms which is save in DB now, and search rooms
- The app also support fast search funtionality with department, and meal type
- After searching room, user can send request to maker of the room
- User can join the room only after maker accept the request 
- Moreover, user can make new room with title, type of meal (No matter/Meal/Cafe/Drinking), maximum number of users
#### TabB [My Room]
- User can see all rooms which he or she is belong to
- And if user select the room, then user can see the list of members and chat with people who are in the same room
#### TabC [Messages]
- User can see messages of request which he or she sent, and response of the request
- If user get new request because someone wants to join his or her room, then the user can accept or reject the request, and the response go to opponent immediately

## 2. Server
- Node.JS, mongoDB
- Code dependencies: express, boody-parser, mongoose
- Server host: 13.124.143.15

Install dependencies:
```bash
$ npm install
```
Run server:
```bash
$ node app.js
```

- Our server is running on [`http://13.124.143.15:10001`](http://13.124.143.15:10001) (It may be discontinued without prior notice)

### RESTful API
- GET /
    - Check Whether Server is Alive
- GET /api
    - Show what kinds of API is accessible
- GET /user
    - Show all users in DB
- GET /user/id/:id
    - Find a user whose attribute 'id' is same as request parameter's id, and return it as a response
- POST /user
    - Make a new user with request body which is consist of id, department, circle, hobby
- POST /user/:userId
    - Update user information whose attribute 'id' is same as request parameter's id
- GET /room
    - Show all rooms in DB
- GET /room/title/:title
    - Find a room which attribute 'title' is same as request parameter's title, and return it as a response
- GET /room/makerId/:makerId
    - Find a room which attribute 'makerId' is same as request parameter's makerId, and return it as a response
- GET /room/mealType/:mealType
    - Find a room which attribute 'mealType' is same as request parameter's mealType, and return it as a response
- GET /room/user/:user
    - Find rooms which requsest parameter's user is an element of 'userList', and return them as a response
- GET /room/roomId/:roomId
    - Find a room which its MongDB's id is same as request parameter's id, and return it as a response
- GET /room/:roomId/userList
    - Show userList of a room which attribute 'roomId' is same as request parameter's roomId
- POST /room
    - Make a new room with request body which is consist of title, makerId, mealType, maxUser, and makerId
- POST /room/addUser/:roomId
    - Add a new user in 'userList' of a room which attribute 'roomId' is same as request parameter's roomId
- PUT /room/:roomId
    - Update room information which attribute 'roomId' is same as request parameter's roomId
- GET /message
    - Show all messages in DB
- GET /message/bangjang/:bangjang
    - Find messages which attribute 'bangjang' is same as request parameter's bangjang, and return them as a response
- GET /message/requester/:requester
    - Find messages which attribute 'requester' is same as request parameter's requester, and return them as a response
- GET /message/specific/:roomId/:requester/:bangjangRead
    - Find a specific message which attribute 'roomId', 'requester', 'bangjangRead' are same as request parameter's roomId, requester, bangjangRead respectively, and return it as a response
- POST /message
    - Make a new message with request body which is consist of roomId, requester, bangjang, bangjangRead, accept
- POST /message/:roomId/:requester
    - Update message information which attribute 'roomId' and 'requester' are same as request paramter's roomId and requester respectively

### Server Structure
- models/
    - schema.js : mongoDB Schema
- routes/
    - index.js : routing Server code
- views/ 
- app.js: main JavaScript Server code
- package.json : include code dependencies
