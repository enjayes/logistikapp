/**
 * Main Server
 */

// General Preferences
var preferences = {
    port: 3142,
    serverdirectory: __dirname,
    mysql: {
        host: 'localhost',
        port: 3306,
        user: 'logistikuser',
        password: 'logistikpasswort',
        database: 'logistikdb'
    }
}




//Express Server
var express = require("express"),
    app = express();

//Http Handler
var http = require('http').Server(app);

//Socket.IO
var io = require('socket.io')(http);

//MySql
var mysql = require('mysql');

//Connect to MySql database
mysqlConnection = mysql.createConnection(preferences.mysql);
mysqlConnection.connect(function (err) {
    if (err) throw err;
    console.log('Connected to MySql database');
});

//Custom Controllers
var connectionController = require('connectionController');
var adminController = require('admincontroller/adminController.js');
var appController = require('appcontroller/appController.js');

//Init Controllers
connectionController.init(preferences,io,http,adminController);
adminController.init(preferences,io,app,express,mysqlConnection);







