/**
 * Created by Norbert on 03.01.2015.
 */



var nfcController = {
    init:function(){

        $( "#popupWriteNFC" ).bind({
            popupafterclose: function(event, ui) {
                nfcController.stopWriteListener();
            }
        });

        localStorage.writeTag = "false";
        if(misc.isMobileApp()) {
            nfc.addNdefListener(
                function (nfcEvent) {
                    // display the tag as JSON
                    $("#popupNFCLogin").popup("close");
                    if (localStorage.writeTag == "true") { // && localStorage.pinSha)
                        console.log("WRITE TAG!");
                        console.log("PIN: " + localStorage.pinSha);
                        var message = [
                            ndef.textRecord(localStorage.pinSha)
                        ];
                        nfc.write(
                            message,
                            function () {
                                notifications.showWithTimeout("Hinweis", "Die Zugangsdaten wurden erfolgreich auf der Chipkarte gespeichert!");

                            },
                            function (reason) {
                                notifications.showError("Die Chipkarte konnte nicht beschrieben werden!");
                            }
                        );
                        localStorage.writeTag = "false";
                        console.log("nfc.write done!");
                        $('#popupWriteNFC').popup('close');
                    }
                    else {
                        var tag = nfcEvent.tag, ndefMessage = tag.ndefMessage;
                        if (ndefMessage) {
                            var nfcPayload = nfc.bytesToString(ndefMessage[0].payload).substring(3);
                            //  alert(nfcPayload);
                            if (nfcPayload != "" && nfcPayload != "empty") {
                                loginController.loginNFC(nfcPayload);
                            }

                        }
                    }
                    //todo
                },
                function () {
                    //alert("Success.");
                },
                function () {
                    //alert("Fail.");
                }
            );
        }
    },
    startWriteListener: function(){
        localStorage.writeTag = "true";
    },
    stopWriteListener: function(){
        localStorage.writeTag = "false";
    }
}