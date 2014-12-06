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
        if(misc.isMobileApp()){
            FastClick.attach(document.body);

        }
        //Open Socket Connection

        serverController.init();



        //Initialize View
        clientView.initialize();

        //TODO: call Client Log-In

        // TODO: load client data

    }
}