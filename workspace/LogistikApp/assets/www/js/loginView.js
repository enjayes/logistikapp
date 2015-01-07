
//Logik hinter den Schaltflächen der Login-Screens
var loginView = {
    pinPad: null,
    initialize: function () {


        loginView.pinPad = new PinPad("#PINcode", function (code) {
            loginController.clear();
            loginController.login(code);

        })


        $("#start_nfc_code_anmelden").click(function () {

            $("#popupNFCLogin").popup("open");

        });

        $("#b_write_nfc").click(function () {
            nfcController.startWriteListener();
            $("#popupWriteNFC").popup("open");

        });


        $("#start_qr_code_anmelden").click(function () {
            $.mobile.loading('show')
            qrCodeController.scan();
        });


        $("#start_anmelden").click(function () {
            loginView.pinPad.clear();

            $('#startScreen').hide();
            $('#lieferantenLogin').show();


        });

        $("#login_zurueck_start").click(function () {

            $('#lieferantenLogin').hide();
            $('#startScreen').show();


        });


    }

}