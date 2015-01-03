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
            loginController.clear();
            loginController.login(code);

        })





        $("#start_qr_code_anmelden").click(function()
        {
            $.mobile.loading('show')
           qrCodeController.scan();
        });


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