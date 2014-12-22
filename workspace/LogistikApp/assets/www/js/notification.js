/** * notification.js.
 *
 *
 *
 * @author Norbert
 * @date 06.12.14 - 20:28
 * @copyright  */






var notifications = {


    show: function (title, message, callback) {

        $.toast({
            heading: title,
            text: message + "<button>Prost!</button>",
            stack: 50,
            hideAfter: 6000000,
            showHideTransition: 'fade',
            afterHidden: callback

        })

    },
    showMessages: function (nachrichten) {
        for (var i = 0; i < nachrichten.length; i++) {
            var showMessage = function (index) {

                notifications.show("Nachricht", nachrichten[index].nachricht, function () {

                    mark_read(nachrichten[index]);
                    //alert("Callback");


                });
            }
            showMessage(i);
        }
    },
    mark_read: function (nachricht) {
        // nachrichten[index].id
        serverController.nachricht.markRead(nachricht);

    }


}