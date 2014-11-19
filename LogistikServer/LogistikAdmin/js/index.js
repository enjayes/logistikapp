/**
 * index.js.
 *
 * >>Description<<
 *
 * @author Manfred
 * @date 19.11.14 - 17:10
 * @copyright munichDev UG
 */


//Load Socket io and connect
var socket = io();


//On Message
socket.on('message', function (msg) {
    console.log('message: ' + msg);
});


//Init Controlers
tabsConstroller.init();



//init calendar

$(document).ready(function () {


    $("body").show();


    uiController.init();

});










