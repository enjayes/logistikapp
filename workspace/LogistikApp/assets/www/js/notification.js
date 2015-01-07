
//Anzeige von Nachrichten und Meldungen
var notifications = {
    //versteckt alle Nachrichten und Meldungen
    hideAll: function () {
        var _container = $('.jq-toast-wrap');
        if (_container) {
            if (_container.length != 0) {
                _container.empty();
            }
        }
        $.toast._container = _container;
    },

    //zeigt eine Nachricht an, kein Timeout
    show: function (title, message, callback) {

        $.toast({
            heading: title,
            text: message,
            stack: 50,
            hideAfter: 6000000,
            showHideTransition: 'fade',
            afterHidden: callback

        })

    },

    //zeigt eine Nachricht an, mit Timeout (5000ms)
    showWithTimeout: function (title, message, callback) {

        $.toast({
            heading: title,
            text: message,
            stack: 50,
            hideAfter: 5000,
            showHideTransition: 'fade',
            afterHidden: callback

        })
    }
    ,
    //zeigt einen Fehler an
    showError: function (message) {

        $.toast({
            heading: "Fehler",
            text: "<h2>" + message + "</h2>",
            stack: 50,
            hideAfter: 3000,
            showHideTransition: 'fade',
            allowToastClose: false,
            bgColor: '#440000'

        })

    },

    //zeigt Nachrichten der Marktleiter an
    showMessages: function (nachrichten) {
        for (var i = 0; i < nachrichten.length; i++) {
            var showMessage = function (index) {

                notifications.show("Nachricht", nachrichten[index].nachricht, function () {

                    notifications.mark_read(nachrichten[index]);
                    //alert("Callback");


                });
            }
            showMessage(i);
        }
    },

    //markiert eine Nachricht als gelesen
    mark_read: function (nachricht) {
        // nachrichten[index].id

        serverController.nachricht.markRead(nachricht);

    }


}