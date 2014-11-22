/**
 * serverController.
 *
 * >>Description<<
 *
 * @author Manfred
 * @date 21.11.14 - 00:19
 * @copyright munichDev UG
 */




var ServerMessage = function (data, type) {
    //It is a website message
    this.c = 1;
    //Define type of message
    if (type)
        this.t = type;
    else
        this.t = serverController.messageType.normal;

    //Set data of message
    this.data = data;
    this.clientid = serverController.id;

}


serverController = {

    socket: null,
    id: misc.getUniqueID(),
    messageType: {
        connection: "c",
        normal: "n",
        callback: "cb"
    },
    callbackHandler: {
        register: function (callback) {
            var callBackName = "cb" + Date.now() + ((Math.random() * 1000000.0) + "").replace(".", "");
            this[callBackName] = callback;
            return callBackName;
        }
    },
    init: function (callback) {
        //Load Socket io and connect

        serverController.socket = io();

        //On Message
        serverController.socket.on('message', function (msg) {
            //Server Connected, send Connection message back
            if (msg.t == "c")
                serverController.socket.emit('message', new ServerMessage({callback: serverController.callbackHandler.register(callback)}, serverController.messageType.connection));
            else if (msg.t == "cb") {
                //Execute Callback
                if (msg.callback && serverController.callbackHandler[msg.callback]) {
                    serverController.callbackHandler[msg.callback](msg.cbdata);
                    delete serverController.callbackHandler[msg.callback];
                }
            }

        });

    },

    lieferant: {
        messageType: {
            getAll: "lga",
            create: "lc",
            update: "lu",
            delete: "ld",
            get: "lg"
        },
        getAll: function (callback) {
            serverController.socket.emit('message', new ServerMessage({t: this.messageType.getAll, callback: serverController.callbackHandler.register(callback)}));
        },
        create: function (lieferant) {
            serverController.socket.emit('message', new ServerMessage({t: this.messageType.create, l: lieferant}));
        },
        update: function (lieferant) {
            serverController.socket.emit('message', new ServerMessage({t: this.messageType.update, l: lieferant}));
        },
        delete: function (lieferant) {
            serverController.socket.emit('message', new ServerMessage({t: this.messageType.delete, l: lieferant}));
        }

    },

    termin: {
        messageType: {
            getAll: "tga",
            create: "tc",
            update: "tu",
            delete: "td",
            get: "tg"
        },
        buildDTO: function (termin) {
            var newTermin = {
                id: termin.id,
                title: termin.title,
                start: termin.start.format(),
                allDay: termin.allDay,
                notizen: termin.notizen,
                lieferant: termin.lieferant
            }

            if (termin.end)
                newTermin.end = termin.end.format()
            else
                newTermin.end = "";



            return newTermin;
        },
        getAll: function (callback) {
            serverController.socket.emit('message', new ServerMessage({t: this.messageType.getAll, callback: serverController.callbackHandler.register(callback)}));
        },
        create: function (termin) {
            serverController.socket.emit('message', new ServerMessage({t: this.messageType.create, l: this.buildDTO(termin)}));
        },
        update: function (termin) {
            serverController.socket.emit('message', new ServerMessage({t: this.messageType.update, l: this.buildDTO(termin)}));
        },
        delete: function (termin) {
            serverController.socket.emit('message', new ServerMessage({t: this.messageType.delete, l: this.buildDTO(termin)}));
        }

    }

}

