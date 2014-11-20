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


//Init Controller
tabsController.init();
lieferantenController.init();
termineController.init();

//Init UI
$(document).ready(function () {
    uiController.ready();
});

$(document).on("mobileinit", function () {
    $.extend($.mobile, {
        linkBindingEnabled: false,
        ajaxEnabled: false
    });
});









