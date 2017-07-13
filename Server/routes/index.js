// routes/index.js

module.exports = function(app, Models)
{
	// Check Whether Server is Alive
	app.get('/', function(req, res){
		console.log("request to /");
		res.render("index.html");
		console.log("   finished rendering index.html");
	})

}