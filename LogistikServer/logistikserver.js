/**
 * Main Server
 *
 */



// General Settings
var port = 4305;


//Express Server
var express = require("express"),
    app = express();

//Https Handler
var httpsHandler = require('./httpsHandler');

//Parse Html Text
var cheerio = require('cheerio');


//Answer Get Request
app.get("/", function (req, res) {


    // Website you wish to allow to connect
    res.setHeader('Access-Control-Allow-Origin', '*');
    // Request methods you wish to allow
    res.setHeader('Access-Control-Allow-Methods', 'GET, POST, OPTIONS, PUT, PATCH, DELETE');
    // Request headers you wish to allow
    res.setHeader('Access-Control-Allow-Headers', 'X-Requested-With,content-type');
    // Set to true if you need the website to include cookies in the requests sent
    // to the API (e.g. in case you use sessions)
    res.setHeader('Access-Control-Allow-Credentials', true);







    var isDidYouMeanRequest=(typeof req.query.dum != "undefined" );
    if (isDidYouMeanRequest) {
       var term = req.query.dum;


        httpsHandler.downloadFile("www.google.com", "/search?q="+encodeURIComponent(term), function (content) {

            $ = cheerio.load(content);

            var correctTerm = $('a.spell').text();

            if(!(correctTerm&& correctTerm.trim()!=""))
                correctTerm = $("#_FQd a").text();


            console.log(correctTerm)

            res.send(correctTerm);


        })


    }
    else
       res.send('');


});


console.log("Service server listening at http://localhost:" + port);
app.listen(port);