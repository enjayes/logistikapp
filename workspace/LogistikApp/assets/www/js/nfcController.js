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
                    loginController.loginNFC(nfcPayload);
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
    }
}