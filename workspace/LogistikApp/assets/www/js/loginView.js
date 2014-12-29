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
            cordova.plugins.barcodeScanner.scan(
                function (result) {
                    loginController.loginQR(result.text);
                    $.mobile.loading('hide')
                },
                function (error) {
                    alert("Scanning failed: " + error);
                    $.mobile.loading('hide')
                }
            );
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