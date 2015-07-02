dataController = {
    emit: function (m,d) {

        var isLocal = false;


        //TODO check local

        if (isLocal) {

        }
        else {
            serverController.socket.emit(m, d);
        }
    },
    load: function(){
        $.mobile.loading( "show");
        lieferantenController.load();
        terminController.load();

        $.mobile.loading( "hide");
        clientView.initialize();
    }



}