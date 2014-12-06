/**
 * logistikapp.
 *
 * >>Description<<
 *
 * @author Manfred
 * @date 19.11.14 - 14:43
 * @copyright urlyRaptor
 */


var logistikapp = {

    start:function(){

        console.log($(window).width())

        //Use Fastclick
        if(){
        FastClick.attach(document.body);
        }
        //Open Socket Connection
        var socket = io.connect(preferences.server);
        socket.on('message', function(msg){
            console.log('message: ' + msg);
        });

        //Initialize View
        clientView.initialize();

        //TODO: call Client Log-In

        // TODO: load client data

    }
}