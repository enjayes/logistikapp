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



        new PinPad("#PINcode",function(code){
            loginController.login(code);
        })

        //Initialize View

        clientView.initialize();


        //TODO: call Client Log-In

        // TODO: load client data

    }
}