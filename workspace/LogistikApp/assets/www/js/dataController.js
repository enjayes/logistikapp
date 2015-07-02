dataController = {
    messages: [],
    sendTime: 5000,
    emitTimer:function(){
        if(dataController.messages){
            if(serverController.socket.connected!=false && serverController.connected) {
                var len = dataController.messages.length;
                var sendmessages = dataController.messages.slice();
                console.log("sendmessages:");
                console.dir(sendmessages);
                dataController.messages = [];

                for (var i = 0; i < len; i++) {
                    var message = sendmessages[i];
                    dataController.emit(message.m, message.d,true);
                }
                dataController.saveMessages();
                if(dataController.messages) {
                    if (dataController.messages.length > 0) {
                        setTimeout(dataController.emitTimer, dataController.sendTime);
                    }
                }
            }
            else{
                setTimeout(dataController.emitTimer,dataController.sendTime);
            }
        }
    },
    emit: function (m,d,noretry) {
        console.log("EMIT!")
            console.log("d.t: "+d.data.t);
        console.dir(d.data);
        console.log("noretry: "+noretry);

        if(serverController.socket.connected && serverController.connected==true) {

            if (noretry != true) {
                if (dataController.messages.length > 0) {
                    dataController.messages.push({m: m, d: d});
                    dataController.saveMessages();
                    dataController.emitTimer();
                }
                else{
                    console.log("serverController.socket.emit(" + m + "," + d.data.t + ");");
                    serverController.socket.emit(m, d);
                }
            }
            else {
                console.log("serverController.socket.emit(" + m + "," + d.data.t + ");");
                serverController.socket.emit(m, d);
            }

        }
        else {
            if (d.data.t == serverController.lieferant.messageType.login) { /*todo*/
                if (d.callback && serverController.callbackHandler[d.callback]) {
                    serverController.callbackHandler[d.callback](null);
                    delete serverController.callbackHandler[d.callback];
                }
            }
            else {
                dataController.messages.push({m: m, d: d});
                dataController.saveMessages();
                if (noretry != true) {
                    setTimeout(dataController.emitTimer, dataController.sendTime);
                }
            }
        }

    },
    saveMessages: function(){
        localStorage.messages = JSON.stringify(dataController.messages);
    },
    loadMessages: function() {
        if (localStorage.messages){
            dataController.messages = JSON.parse(localStorage.messages);
        }
        if(!dataController.messages){
            dataController.messages = [];
        }
    }
    ,
    load: function(){
        $.mobile.loading( "show");

        lieferantenController.load();

        $.mobile.loading( "hide");
        dataController.checkIfLoaded();
        if(dataController.messages) {
            if (dataController.messages.length > 0) {
                setTimeout(dataController.emitTimer, 5000);
            }
        }
        $.mobile.loading( "hide");
    },
    checkIfLoaded:function(){
        if(lieferantenController.ready==1){

        }
        else{
            setTimeout(dataController.checkIfLoaded,300);
        }
    }



}