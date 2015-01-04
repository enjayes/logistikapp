/**
 * Created by Norbert on 03.01.2015.
 */



var nfcController = {

    init:function(){
        nfc.addNdefListener(
            function(nfcEvent) {
                // display the tag as JSON
                var tag = nfcEvent.tag, ndefMessage = tag.ndefMessage;
                if (ndefMessage) {
                    var nfcPayload = nfc.bytesToString(ndefMessage[0].payload).substring(3);
                  //  alert(nfcPayload);
                    if(nfcPayload!="" && nfcPayload != "empty") {
                        loginController.loginNFC(nfcPayload);
                    }

                }
                //todo
            },
            function() {
                //alert("Success.");
            },
            function() {
                //alert("Fail.");
            }
        );
    },
    startWriteListener: function(){
        nfc.addTagDiscoveredListener(
            function(nfcEvent) {
                var payload = "Das hat die Edeka-App geschrieben!",
                    record = ndef.mimeMediaRecord("text/html", nfc.stringToBytes(payload));
                nfc.write(
                    [record],
                    function () {
                       notifications.show();
                        navigator.notification.vibrate(100);
                    },
                    function (reason) {
                        navigator.notification.alert(reason, function() {}, "There was a problem");
                    }
                );
            },
            function() {
                //alert("Success.");
            },
            function() {
                //alert("Fail.");
            }
        );
    }
}