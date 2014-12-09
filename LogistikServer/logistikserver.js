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


//Load Controllers
var connectionController = require('connectionController');
var adminController = require('admincontroller/adminController.js');
var appController = require('appcontroller/appController.js');
var dataController = require('dataController.js');


//Init Controllers
dataController.init(preferences);
connectionController.init(preferences,io,http,adminController);
adminController.init(preferences,dataController,io,app,express);
appController.init(preferences,dataController,io,app,express);







