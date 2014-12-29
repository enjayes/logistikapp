/**
 * Created by Norbert on 29.12.2014.
 */



var phoneController = {

    callNumber:function(phoneNumber,textToSpeech){
        serverController.phone.callNumber(phoneNumber,textToSpeech);
    },

    sendMessage:function(phoneNumber,text){
        serverController.phone.sendMessage(phoneNumber,text);
    },

    informAboutLogin:function(markt,lieferant){
        var name = "Unbekannt";
        var firma = "Unbekannt"
        var telefon = "Unbekannt"
        if(lieferant) {
            if (lieferant.vorname) {
                name = lieferant.vorname + " "
            }
            if (lieferant.name) {
                name = name + lieferant.name + " "
            }
            if (lieferant.firma) {
                firma = lieferant.firma
            }
            if (lieferant.telefon) {
                telefon = lieferant.telefon
            }
            if (markt.sms) {
                phoneController.sendMessage(markt.telefon, "Ein Lieferant hat sich soeben am Terminal angemeldet.\n\nName: " + name + "\nFirma: " + firma + "\nTel: " + telefon);
            }
            if (markt.call) {
                phoneController.callNumber(markt.telefon, "Hallo, ein Lieferant hat sich soeben am Terminal angemeldet! Name: " + name + ".  Firma: " + firma + ".  Auf Wiederhören!");
            }
        }
    }

}