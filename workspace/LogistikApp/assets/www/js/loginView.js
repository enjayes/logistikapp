/**
 * loginView
 *
 *
 * @author
 * @date 11.12.14 - 16:55
 * @copyright
 */



var loginView = {
    pinPad:null,
    initialize: function() {


        loginView.pinPad= new PinPad("#PINcode",function(code){
            loginController.login(code);

        })

        $("#start_anmelden").click(function()
        {

            loginView.pinPad.clear();

            $('#startScreen').hide();
            $('#lieferantenLogin').show();


        });

        $("#login_zurueck_start").click(function()
        {

            $('#lieferantenLogin').hide();
            $('#startScreen').show();

        });




    }

}