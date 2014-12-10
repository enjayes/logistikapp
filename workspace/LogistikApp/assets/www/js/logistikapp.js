/**
 * logistikapp.
 *
 *
 *
 *
 * @date 19.11.14 - 14:43
 *
 */


var logistikapp = {

    start:function(){


        //Use Fastclick
        if(misc.isMobileApp()){
            FastClick.attach(document.body);

        }

        //Establish Socket Connection
        serverController.initialize();



        $('#reader').html5_qrcode(function(data){
                // do something when code is read
            },
            function(error){
                //show read errors
            }, function(videoError){
                //the video stream could be opened
            }
        );

        //Initialize View

        clientView.initialize();


        //TODO: call Client Log-In

        // TODO: load client data

    }
}