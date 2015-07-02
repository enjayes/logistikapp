dataController = {
    load: function (m,d){

        var isLocal = false;


        //TODO check local

        if(isLocal){

        }
        else {
            serverController.socket.emit(m, d);
        }
    }




}