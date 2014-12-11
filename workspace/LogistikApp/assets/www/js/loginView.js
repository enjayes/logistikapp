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



    }

}