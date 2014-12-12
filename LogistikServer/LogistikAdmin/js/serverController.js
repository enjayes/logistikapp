/**
 * serverController
 *
 *
 *
 *
 * @date 20.11.14 - 01:07
 *
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
            var callBackName = "cb" + Date.now()  + "x"+((Math.random() * 1000000.0) + "").replace(".", "");
            this[callBackName] = callback;
            return callBackName;
        }
    },
    init: function (callback) {
        //Load Socket io and connect

        serverController.socket = io();


        serverController.socket.on('disconnect', function () {
            $("#page").css("opacity", 0.2).css("pointer-events", "none");
            $("#servererror").show();
        });

        //On Message
        serverController.socket.on('message', function (msg) {
            //Server Connected, send Connection message back
            if (msg.t == serverController.messageType.connection) {
                serverController.socket.emit('message', new ServerMessage({callback: serverController.callbackHandler.register(callback)}, serverController.messageType.connection));
                $("#page").css("opacity", "").css("pointer-events", "auto");
                $("#servererror").hide();
            }
            else if (msg.t == serverController.messageType.callback) {
                //Execute Callback

                if (msg.callback && serverController.callbackHandler[msg.callback]) {
                    serverController.callbackHandler[msg.callback](msg.cbdata);
                    delete serverController.callbackHandler[msg.callback];
                }
                //Lieferanten wurde in anderem Fenster geändert
            } else if (msg.t == serverController.lieferant.messageType.updateOthers) {
                if (msg.l)
                    serverController.lieferant.getAllCallback(msg.l);
            } else if (msg.t == serverController.termin.messageType.updateOthers) {
                if (msg.e)
                    serverController.termin.getAllCallback(msg.e);
            } else if (msg.t == serverController.nachricht.messageType.updateOthers) {
                if (msg.n)
                    serverController.nachricht.getAllCallback(msg.n);
            } else if (msg.t == serverController.antwortNachricht.messageType.updateOthers) {
                if (msg.a)
                    serverController.antwortNachricht.getAllCallback(msg.a);
            }

        });

    },

    lieferant: {
        messageType: {
            getNewPin: "lgnp",
            getAll: "lga",
            create: "lc",
            update: "lu",
            delete: "ld",
            get: "lg",
            updateOthers: "luo"

        }, buildDTO: function (lieferant) {
            return {
                id: lieferant.id,
                Pin: lieferant.pin,
                Vorname: lieferant.vorname,
                Name: lieferant.name,
                Telefon: lieferant.telefon,
                EMail: lieferant.email,
                Adresse: lieferant.adresse,
                Notizen: lieferant.notizen
            }
        }, parseDTO: function (lieferant) {
            return {
                id: lieferant.id,
                pin: lieferant.Pin,
                vorname: lieferant.Vorname,
                name: lieferant.Name,
                telefon: lieferant.Telefon,
                email: lieferant.EMail,
                adresse: lieferant.Adresse,
                notizen: lieferant.Notizen
            }
        },
        getAllCallback: null,
        getNewPin: function(lieferant,callback){
            var newCallback = function () {
                if(arguments[0]){
                    var lieferant = serverController.lieferant.parseDTO(arguments[0]);
                    callback(lieferant);
                }
            }
            serverController.socket.emit('message', new ServerMessage({t: this.messageType.getNewPin,l: this.buildDTO(lieferant), callback: serverController.callbackHandler.register(newCallback)}));

        },
        getAll: function (callback) {

            serverController.lieferant.getAllCallback = function (list) {
                for (var i = 0; i < list.length; i++) {
                    list[i] = serverController.lieferant.parseDTO(list[i]);
                }
                return callback(list);
            }

            var newCallback = function () {
                serverController.lieferant.getAllCallback(arguments[0], arguments[1], arguments[2], arguments[3]);
                serverController.getAllOnStartupCounter++;
                serverController.onLoadedGetAllOnStartup();
            }
            serverController.socket.emit('message', new ServerMessage({t: this.messageType.getAll, callback: serverController.callbackHandler.register(newCallback)}));
        },
        create: function (lieferant) {
            serverController.socket.emit('message', new ServerMessage({t: this.messageType.create, l: this.buildDTO(lieferant)}));
        },
        update: function (lieferant) {
            serverController.socket.emit('message', new ServerMessage({t: this.messageType.update, l: this.buildDTO(lieferant)}));
        },
        delete: function (lieferant) {
            serverController.socket.emit('message', new ServerMessage({t: this.messageType.delete, l: this.buildDTO(lieferant)}));
        }

    },

    termin: {
        messageType: {
            getAll: "tga",
            create: "tc",
            update: "tu",
            delete: "td",
            get: "tg",
            updateOthers: "tuo"

        },
        buildDTO: function (termin) {
            var newTermin = {
                id: termin.id,
                Title: termin.title,
                Start: termin.start.format(),
                AllDay: termin.allDay,
                Notizen: termin.notizen,
                Lieferant: termin.lieferant
            }

            if (termin.end)
                newTermin.End = termin.end.format();
            else
                newTermin.End = "";

            return newTermin;
        },
        parseDTO:function(termin){
             var newTermin = {
                id: termin.id,
                title: termin.Title,
                start: termin.Start,
                allDay: termin.AllDay,
                notizen: termin.Notizen,
                lieferant: termin.Lieferant

            };

            if (termin.end != "")
                newTermin.end = termin.End;
            else
                newTermin.end = undefined;

            return newTermin;
        },
        getAllCallback: null,
        getAll: function (callback) {
            serverController.termin.getAllCallback =  function (list) {

                for (var i = 0; i < list.length; i++) {
                    list[i] = serverController.termin.parseDTO(list[i]);
                }
                return callback(list);
            }

            var newCallback = function () {

                serverController.termin.getAllCallback(arguments[0], arguments[1], arguments[2], arguments[3]);

                serverController.getAllOnStartupCounter++;
                serverController.onLoadedGetAllOnStartup();
            }

            serverController.socket.emit('message', new ServerMessage({t: this.messageType.getAll, callback: serverController.callbackHandler.register(newCallback)}));
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

    },
    nachricht: {
        messageType: {
            getAll: "nga",
            create: "nc",
            delete: "nd",
            updateOthers: "nuo"
        },
        buildDTO: function (nachricht) {
            return {
                id: nachricht.id,
                datum: nachricht.datum.getTime(),
                nachricht: nachricht.nachricht,
                lieferanten: nachricht.lieferanten
            }
        },
        parseDTO:function(nachricht){
            return {
                id: nachricht.id,
                datum: parseInt(nachricht.datum),
                nachricht: nachricht.nachricht,
                lieferanten:nachricht.lieferanten
            }
        },
        getAll: function (callback) {
            serverController.nachricht.getAllCallback =   function (list) {
                for (var i = 0; i < list.length; i++) {
                    list[i] = serverController.nachricht.parseDTO(list[i]);
                }
                return callback(list);
            };
            var newCallback = function () {

                serverController.nachricht.getAllCallback(arguments[0], arguments[1], arguments[2], arguments[3]);
                serverController.getAllOnStartupCounter++;
                serverController.onLoadedGetAllOnStartup();
            }

            serverController.socket.emit('message', new ServerMessage({t: this.messageType.getAll, callback: serverController.callbackHandler.register(newCallback)}));
        },
        create: function (nachricht) {
            serverController.socket.emit('message', new ServerMessage({t: this.messageType.create, n: this.buildDTO(nachricht)}));
        },
        delete: function (nachricht) {
            serverController.socket.emit('message', new ServerMessage({t: this.messageType.delete, n: this.buildDTO(nachricht)}));
        }

    },
    antwortNachricht: {
        messageType: {
            getAll: "aga",
            update: "au",
            delete: "ad",
            updateOthers: "auo"
        },
        buildDTO: function (nachricht) {
            var newNachricht = {
                id: nachricht.id,
                lieferantid: nachricht.lieferantid,
                read: nachricht.read == true ? 1 : 0,
                datum: nachricht.datum,
                nachricht: nachricht.nachricht
            }

            return newNachricht;
        },
        parseDTO:function(nachricht){
            return  {
                id: nachricht.id,
                lieferantid: nachricht.lieferantid,
                read: nachricht.read==1?true:false,
                datum: parseInt(nachricht.datum),
                nachricht: nachricht.nachricht
            };
        },
        getAll: function (callback) {
            serverController.antwortNachricht.getAllCallback = function (list) {
                for (var i = 0; i < list.length; i++) {
                    list[i] = serverController.antwortNachricht.parseDTO(list[i]);
                }
                return callback(list);
            };

            var newCallback = function () {
                serverController.antwortNachricht.getAllCallback (arguments[0], arguments[1], arguments[2], arguments[3]);
                serverController.getAllOnStartupCounter++;
                serverController.onLoadedGetAllOnStartup();
            }

            serverController.socket.emit('message', new ServerMessage({t: this.messageType.getAll, callback: serverController.callbackHandler.register(newCallback)}));
        },
        update: function (nachricht) {
            serverController.socket.emit('message', new ServerMessage({t: this.messageType.update, a: this.buildDTO(nachricht)}));
        },
        delete: function (nachricht) {
            serverController.socket.emit('message', new ServerMessage({t: this.messageType.delete, a: this.buildDTO(nachricht)}));
        }

    },


    job: {
        messageType: {
            getAll: "jga",
            create: "jc",
            update: "ju",
            delete: "jd",
            get: "jg",
            updateOthers: "juo"

        },
        buildDTO: function (job) {
            return job;//TODO
        },
        parseDTO:function(job){
            return job;
        },
        getAllCallback: null,
        getAll: function (callback) {
            serverController.job.getAllCallback =  function (list) {
                for (var i = 0; i < list.length; i++) {
                    list[i] = serverController.job.parseDTO(list[i]);
                }
                return callback(list);
            };
            var newCallback = function () {
                serverController.job.getAllCallback(arguments[0], arguments[1], arguments[2], arguments[3]);
                serverController.getAllOnStartupCounter++;
                serverController.onLoadedGetAllOnStartup();
            }
            serverController.socket.emit('message', new ServerMessage({t: this.messageType.getAll, callback: serverController.callbackHandler.register(newCallback)}));
        },
        create: function (job) {
            serverController.socket.emit('message', new ServerMessage({t: this.messageType.create, j: this.buildDTO(job)}));
        },
        update: function (job) {
            serverController.socket.emit('message', new ServerMessage({t: this.messageType.update, j: this.buildDTO(job)}));
        },
        delete: function (job) {
            serverController.socket.emit('message', new ServerMessage({t: this.messageType.delete, j: this.buildDTO(job)}));
        },
        get: function (jobId,callback) {
            var newCallback = function () {
                if(arguments[0]){
                    var job = serverController.job.parseDTO(arguments[0]);
                    callback(job);
                }
            }
            serverController.socket.emit('message', new ServerMessage({t: this.messageType.get, j: jobId , callback: serverController.callbackHandler.register(newCallback)}));
        }

    }, statistics: {
        messageType: {
            get: "sg"
        },
        get:function(callback){
            var newCallback = function () {
                if(arguments[0])
                    callback( arguments[0]);
                serverController.getAllOnStartupCounter++;
                serverController.onLoadedGetAllOnStartup();
            }
            serverController.socket.emit('message', new ServerMessage({t: this.messageType.get, callback: serverController.callbackHandler.register(newCallback)}));
        }
    },
    getAllOnStartupCounter: 0,
    getAllOnStartupMax: 5,
    onLoadedGetAllOnStartup: function () {
        if (serverController.getAllOnStartupCounter == serverController.getAllOnStartupMax) {
            crossroads.parse(location.hash);
        }

    }
}

