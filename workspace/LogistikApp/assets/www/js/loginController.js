/**
 * loginController
 *
 *
 * @author
 * @date 11.12.14 - 02:30
 * @copyright
 */



loginController = {



    login:function(pin){

        var loginCallback =function(lieferant){

            //Login hat funktioniert
            if(lieferant&&lieferant.id){

                serverController.nachricht.getAll(function(nachrichten){
                    //Nachrichten
                    console.dir(nachrichten);

                    for(var i= 0;i<nachrichten.length;i++){
                        notifications.show("Nachricht",nachrichten[i].nachricht, function(){})

                    }
                })



                $('#lieferantenLogin').hide();
                $('#contact_daten_menu').show();
                clientView.lieferant = lieferant;
                console.dir(lieferant)
                contactController.set(lieferant.id,lieferant);
                $(".greetingLieferant").html(clientView.getLieferantFullName())

            }

        };


        //Create Pin 4 digits
        var pad = "0000";
        pin = pad.substring(0, pad.length - pin.length) + pin;

        var pinSha = ""+CryptoJS.SHA3("dfjo58443pggd9gudf9"+pin, { outputLength: 512 });
        serverController.lieferant.login(pinSha,loginCallback);

    }

}
