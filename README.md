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

### Server Structure
- models/
    - schema.js : mongoDB Schema
- routes/
    - index.js : routing Server code
- views/ 
- app.js: main JavaScript Server code
- package.json : include code dependencies