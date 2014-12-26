/**
 * loginController
 *
 *
 * @author
 * @date 11.12.14 - 02:30
 * @copyright
 */



loginController = {

    loggedIn:false,

    loginError:function(){
        notifications.showError("Die Anmeldung war leider nicht erfolgreich!")
    },

    login:function(pin){

        var loginCallback =function(lieferant) {

            //Login hat funktioniert
            if (lieferant){
                if (lieferant.id) {

                    serverController.nachricht.get(lieferant.id, function (nachrichten) {
                        //Nachrichten
                        console.dir(nachrichten);
                        notifications.showMessages(nachrichten);

                    })


                    $('#lieferantenLogin').hide();
                    $('#contact_daten_menu').show();
                    clientView.lieferant = lieferant;
                    console.dir(lieferant)
                    contactController.set(lieferant.id, lieferant);
                    serverController.job.getTemplates(lieferant.id, templateController.set);
                    $(".greetingLieferant").html(clientView.getLieferantFullName());
                    loggedIn = true;
                    $('#callButton').show();

                }
                else{
                    loginController.loginError();
                }

            }
            else{
                loginController.loginError();
            }
        };


        //Create Pin 4 digits
        var pad = "0000";
        pin = pad.substring(0, pad.length - pin.length) + pin;

        var pinSha = ""+CryptoJS.SHA3("dfjo58443pggd9gudf9"+pin, { outputLength: 512 });
        serverController.lieferant.login(pinSha,loginCallback);
    },



    logout:function(){
        $('#callButton').hide();
        loggedIn = false;
        notifications.hideAll();
        clientView.lieferant = null;
        clientView.clearJob();
        contactController.set(null,null);
    }

}
