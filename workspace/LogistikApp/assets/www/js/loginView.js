/**
 * loginView
 *
 *
 * @author
 * @date 11.12.14 - 16:55
 * @copyright
 */



var loginView = {

    initialize: function() {


        new PinPad("#PINcode",function(code){
            loginController.login(code);
        })

        $("#login_zurueck_start").click(function()
        {

            $('#lieferantenLogin').hide();
            $('#startScreen').show();

        });




    }

}