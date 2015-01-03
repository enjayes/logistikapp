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
                    else{
                        alert("empty Tag")
                        $('#writeNFCTag').show();
                        $('#startScreen').hide();

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
    }
}