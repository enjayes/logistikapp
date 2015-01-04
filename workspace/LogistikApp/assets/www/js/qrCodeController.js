/**
 * Created by Norbert on 03.01.2015.
 */



var qrCodeController = {

    scan:function(){
        if(misc.isMobileApp()) {
            cordova.plugins.barcodeScanner.scan(
                function (result) {
                    loginController.loginQR(result.text);
                    $.mobile.loading('hide')
                },
                function (error) {
                    loginController.loginError();
                    $.mobile.loading('hide')
                }
            );
        }
        else{
            $.mobile.loading('hide')
        }
    }

}
