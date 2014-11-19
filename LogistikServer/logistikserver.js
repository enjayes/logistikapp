/**
 * Main Server
 */


// General Settings

var port = 3142;

var mysqlprefs = {
    host: 'localhost',
    port: 3306,
    user: 'logistikuser',
    password: 'logistikpasswort',
    database: 'logistikdb'
}


//Express Server
var express = require("express"),
    app = express();

//MySql
var mysql =  require('mysql');


//Connect to MySql database
mysqlConnection = mysql.createConnection(mysqlprefs);
mysqlConnection.connect(function (err) {
    if (err) throw err;
    console.log('Connected to MySql database');
});


//Https Handler
var http = require('http').Server(app);


//Start Admin Site, serve Resources under /admin/ statically
app.use('/', express.static(__dirname + '/LogistikAdmin'));


//Start socket.io for communication with website/apps
var io = require('socket.io')(http);


//Listen
http.listen(port, function(){
    console.log("Server listening on port "+port);
});


//Website connected
io.on('connection', function(socket){

    console.log('Connected');
    socket.emit('message', 'Server Push Message');

    socket.on('disconnect', function(){
        console.log('Disconnected');
    });
});



//App  connected
io.on('connection', function(socket){

    console.log('App connected');

    socket.emit('message', 'Server Push Message');

    socket.on('disconnect', function(){
        console.log('App Disconnected');
    });
});

