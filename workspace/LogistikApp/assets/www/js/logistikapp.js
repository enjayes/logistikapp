/**
 * logistikapp.
 *
 * >>Description<<
 *
 * @author Manfred
 * @date 19.11.14 - 14:43
 * @copyright munichDev UG
 */


var logistikapp = {

    start:function(){


        var socket = io.connect(preferences.server);

        socket.on('message', function(msg){
            alert('message: ' + msg);
        });

    }
}