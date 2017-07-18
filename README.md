# [CS496_Week3]
## Project Name: 
### Team: [HyunWoo Kang](https://github.com/hyunOO), [SeungMin Lee](https://github.com/iamlsm97)

## 1. Client
- Android (java, xml)


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
