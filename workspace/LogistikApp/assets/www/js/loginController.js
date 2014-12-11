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

            if(!lieferant||!lieferant.id){
                alert("Error")

            }else{
                $('#lieferantenLogin').hide();
                $('#jobSelector').show();
                clientView.lieferant = lieferant;
                console.dir(lieferant)
                $("#greetingLieferant").html(clientView.getLieferantFullName())

            }

        };


        //Create Pin 4 digits
        var pad = "0000";
        pin = pad.substring(0, pad.length - pin.length) + pin;

        var pinSha = ""+CryptoJS.SHA3("dfjo58443pggd9gudf9"+pin, { outputLength: 512 });
        serverController.lieferant.login(pinSha,loginCallback);

    }

}
