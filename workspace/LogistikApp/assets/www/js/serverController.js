
//Nachricht an den Server
var ServerMessage = function (data, type) {
    //It is an app message  0 = App
    this.c = 0;
    //Define type of message
    if (type)
        this.t = type;
    else
        this.t = serverController.messageType.normal;

    //Set data of message
    this.data = data;
    this.clientid = serverController.id;

}

//für die CLient-Server-Kommunikation zuständig
serverController = {

    socket: null,
    connected: false,
    id: misc.getUniqueID(),

    messageType: {
        connection: "c",
        normal: "n",
        callback: "cb",
        pushLieferant: "pl",
        deleteLieferant: "dl"
    },
    callbackHandler: {
        register: function (callback) {
            var callBackName = "cb" + Date.now() + "x" + ((Math.random() * 1000000.0) + "").replace(".", "");
            //console.log("reg: "+callBackName);
            this[callBackName] = callback;
            return callBackName;
        }
    },
    initialize: function (callback) {

        //Load Socket io and connect

        //dataController = io.connect(preferences.server);

        var address = logistikapp.servername + ":" + logistikapp.server_port;

        if(address.search("http://")==-1){
            address = "http://"+address
        }
        console.log("io.connect("+address+")");

        serverController.socket = io.connect(address, {"force new connection": true});


        serverController.socket.on('error', function (err) {
            console.log("SOCKET ERROR!!!!")
            console.log(err);
        });


        serverController.socket.on('disconnect', function () {
            if(clientView.viewTitle=="konfi_menue") {
                $("#server_status").css("color", "rgb(191,84, 84)").text("Keine Serververbindung...");
            }
            serverController.connected = false;
            console.log("DISCONNECT")
        });

        serverController.socket.on('connect', function() {
            console.log("CONNECT!");

        });

        //On Message
        serverController.socket.on('message', function (msg) {
            console.dir("on(message)");
            console.dir(msg);
            if(clientView.viewTitle=="konfi_menue") {
                $("#server_status").css("color", "rgb(84, 191, 84)").text("Mit Server verbunden...");
            }
            setTimeout(function(){serverController.connected = true},1000);
            //Server Connected, send Connection message back
            if (msg.t == serverController.messageType.connection) {
                serverController.socket.emit('message', new ServerMessage({callback: serverController.callbackHandler.register(callback)}, serverController.messageType.connection));
                serverController.loadConfig();
                dataController.load();
                console.log("serverController.messageType.connection");
            }
            else if (msg.t == serverController.messageType.pushLieferant){
                console.log("UPDATE LIEFERANT")
                console.log(msg.l);
                lieferantenController.update(serverController.lieferant.parseDTO(msg.l));
            }
            else if (msg.t == serverController.messageType.deleteLieferant){
                lieferantenController.deleteLieferant(serverController.lieferant.parseDTO(msg.l));
            }
            else if (msg.t == serverController.messageType.callback) {
                //Execute Callback
                if (msg.callback && serverController.callbackHandler[msg.callback]) {

                    /*
                    console.dir("zurückgeschickte callback id" + msg.callback);
                    console.dir(msg.cbdata);
                    console.dir(serverController.callbackHandler);*/

                    serverController.callbackHandler[msg.callback](msg.cbdata);
                    delete serverController.callbackHandler[msg.callback];
                }

                /*} else if (msg.t == serverController.lieferant.messageType.updateOthers) {
                 if (msg.l)
                 serverController.lieferant.getAllCallback(msg.l);
                 */
            }

        });

    },
    loadConfig: function () {
        serverController.markt.getAll(function (maerkte) {
            if (maerkte) {
                var found = false;
                configData.maerkte = maerkte;
                var arrayLength = maerkte.length;
                for (var i = 0; i < arrayLength; i++) {
                    if (logistikapp.markt_id == maerkte[i].name) {
                        configData.markt = maerkte[i];
                        found = true;
                        break;
                    }
                }
                if (found == false) {
                    serverController.socket.disconnect();
                    if(clientView.viewTitle=="konfi_menue") {
                        $("#server_status").css("color", "rgb(191,84, 84)").text("Keine Serververbindung...");
                    }
                    serverController.connected = false;
                    notifications.showError("Der Marktname wurde nicht gefunden!")
                }

            }
            console.log("loadConfig-Märkte:");
            console.log(configData.maerkte);


            terminController.load();


        })
    }
    ,
    lieferant: {
        messageType: {
            login: "ll",
            getNewPin: "lgnp",
            getAll: "lga",
            create: "lc",
            update: "lu",
            delete: "ld",
            get: "lg",
            updateOthers: "luo"

        },
        parseDTO: function (lieferant) {
            console.log("parseDTO ####################################### LIEFERANT");
            console.log(lieferant);
            return {
                id: lieferant.id,
                pin: lieferant.Pin,
                vorname: lieferant.Vorname,
                name: lieferant.Name,
                telefon: lieferant.Telefon,
                email: lieferant.Email,
                adresse: lieferant.Adresse,
                notizen: lieferant.Notizen
            }
        },
        buildDTO: function (lieferant) {
            return {
                id: lieferant.id,
                Pin: lieferant.pin,
                Vorname: lieferant.vorname,
                Name: lieferant.name,
                Telefon: lieferant.telefon,
                Email: lieferant.email,
                Adresse: lieferant.adresse,
                Notizen: lieferant.notizen
            }
        },
        login: function (pinSha, callback) {

            var lieferant = lieferantenController.login(pinSha);
            if( lieferant==null) {

                var newCallback = function () {
                    if (arguments[0]) {
                        var lieferant = serverController.lieferant.parseDTO(arguments[0]);
                        callback(lieferant);
                    } else
                        callback();
                };
                dataController.emit('message', new ServerMessage({
                    t: this.messageType.login,
                    p: pinSha,
                    callback: serverController.callbackHandler.register(newCallback)
                }));
            }
            else
            {
                callback(lieferant);
            }
        },
        update: function (lieferant) {
            dataController.emit('message', new ServerMessage({
                t: this.messageType.update,
                l: this.buildDTO(lieferant)
            }));
        }
    }

    ,
    nachricht: {
        messageType: {
            getAll: "nga",
            create: "nc",
            delete: "nd",
            updateOthers: "nuo",
            markRead: "nm",
            get: "ng"
        },
        buildDTO: function (nachricht) {
            return {
                id: nachricht.id,
                datum: nachricht.datum.getTime(),
                nachricht: nachricht.nachricht,
                lieferanten: nachricht.lieferanten
            }
        },
        parseDTO: function (nachricht) {
            return {
                id: nachricht.id,
                datum: parseInt(nachricht.datum),
                nachricht: nachricht.nachricht,
                lieferanten: nachricht.lieferanten
            }
        },
        getAll: function (callback) {
            serverController.nachricht.getAllCallback = function (list) {
                for (var i = 0; i < list.length; i++) {
                    list[i] = serverController.nachricht.parseDTO(list[i]);
                }
                return callback(list);
            };
            var newCallback = function () {

                serverController.nachricht.getAllCallback(arguments[0], arguments[1], arguments[2], arguments[3]);
                serverController.getAllOnStartupCounter++;
                serverController.onLoadedGetAllOnStartup();
            };

            dataController.emit('message', new ServerMessage({
                t: this.messageType.getAll,
                callback: serverController.callbackHandler.register(newCallback)
            }));
        },
        get: function (lieferanten_id, callback) {
            serverController.nachricht.getCallback = function (list) {


                for (var i = 0; i < list.length; i++) {
                   // console.log(list[i]);
                    list[i] = serverController.nachricht.parseDTO(list[i]);

                }
                return callback(list);
            };
            var newCallback = function () {
                serverController.nachricht.getCallback(arguments[0], arguments[1], arguments[2], arguments[3]);
            };

            dataController.emit('message', new ServerMessage({
                t: this.messageType.get,
                lid: lieferanten_id,
                callback: serverController.callbackHandler.register(newCallback)
            }));
        },
        markRead: function (nachricht) {

            var markReadMessage = {t: this.messageType.markRead, lid: clientView.lieferant.id, nid: nachricht.id}
            console.dir("markRead:");
            console.dir(markReadMessage);
            dataController.emit('message', new ServerMessage(markReadMessage));
        },
        create: function (nachricht) {

            dataController.emit('message', new ServerMessage({
                t: this.messageType.create,
                n: this.buildDTO(nachricht)
            }));
        }

    },
    antwortNachricht: {
        messageType: {
            getAll: "aga",
            create: "ac",
            update: "au",
            delete: "ad",
            updateOthers: "auo"
        },
        maxNachrichten: 1000,
        getAllAdminNachrichten: function (callback, start, stop) {
            if (!callback)
                return;
            dataController.mysqlConnection.query('SELECT * FROM Lieferantnachrichtgesendet  ORDER BY datum DESC', function (err, rows, fields) {
                if (err)
                    console.log("MYSQL ERROR: " + err);
                else {
                    if (rows) {
                        var length = rows.length;

                        rows = rows.slice(start, stop);


                        callback(rows, length);
                    }
                }
            });

        },
        buildDTO: function (nachricht) {
            return {
                id: nachricht.id,
                datum: nachricht.datum.getTime(),
                nachricht: nachricht.nachricht,
                lieferantid: nachricht.lieferantid,
                read:nachricht.read
            }
        }
    , create: function (nachricht) {

            dataController.emit('message', new ServerMessage({
                t: this.messageType.create,
                n: this.buildDTO(nachricht)
            }));
        }
    },
    phone: {
        messageType: {
            callNumber: "pcn",
            sendMessage: "psm"
        },
        callNumber: function (number, text) {
            serverController.phone.callCallback = function (list) {

            };
            var newCallback = function () {
                serverController.phone.callCallback(arguments[0], arguments[1], arguments[2], arguments[3]);
            };

            serverController.socket.emit('message', new ServerMessage({
                t: this.messageType.callNumber,
                n: number,
                text: text,
                callback: serverController.callbackHandler.register(newCallback)
            }));
        },
        sendMessage: function (number, text) {
            serverController.phone.sendCallback = function (list) {

            };
            var newCallback = function () {
                serverController.phone.sendCallback(arguments[0], arguments[1], arguments[2], arguments[3]);
            };

            serverController.socket.emit('message', new ServerMessage({
                t: this.messageType.sendMessage,
                n: number,
                text: text,
                callback: serverController.callbackHandler.register(newCallback)
            }));
        }


    },
    job: {
        messageType: {

            getAll: "jga",
            getTemplates: "jt",
            create: "jc",
            update: "ju",
            delete: "jd",
            get: "jg",
            updateOthers: "juo",
            startVisit: "jsv"
        },
        buildDTO: function (job) {

            var newJob = $.extend(true, {}, job);
            newJob.timestamp_start = job.timestamp_start.getTime();
            newJob.timestamp_end = job.timestamp_end.getTime();

            // t_vk_euro_abgabe: job.t_vk_euro_abgabe, //TODO
            //logout
            // TODO t_notizen: job.t_notizen

            return newJob;

        },
        parseDTO: function (job) {
            return job;
        },
        getAllCallback: null,
        getAll: function (callback) {
            serverController.job.getAllCallback = function (list) {
                for (var i = 0; i < list.length; i++) {
                    list[i] = serverController.job.parseDTO(list[i]);
                }
                return callback(list);
            };
            var newCallback = function () {
                serverController.job.getAllCallback(arguments[0], arguments[1], arguments[2], arguments[3]);
                serverController.getAllOnStartupCounter++;
                serverController.onLoadedGetAllOnStartup();
            };
            dataController.emit('message', new ServerMessage({
                t: this.messageType.getAll,
                callback: serverController.callbackHandler.register(newCallback)
            }));
        },
        startVisit: function(startTime,lieferanten_id,jid){
            dataController.emit('message', new ServerMessage({
                t: serverController.job.messageType.startVisit,
                start: startTime,
                mid: logistikapp.markt_id,
                lid: lieferanten_id,
                jid: jid
            }))
        },
        getTemplatesCallback: null,
        getTemplates: function (lieferanten_id, callback) {


            serverController.job.getTemplatesCallback = function (list) {
                for (var i = 0; i < list.length; i++) {
                    list[i] = serverController.job.parseDTO(list[i]);
                }
                return callback(list);
            };
            var newCallback = function () {
                serverController.job.getTemplatesCallback(arguments[0], arguments[1], arguments[2], arguments[3]);
                serverController.getTemplatesOnLoginCounter++;
                serverController.onLoadedGetTemplatesOnLogin();
            };
            dataController.emit('message', new ServerMessage({
                t: this.messageType.getTemplates,
                lid: lieferanten_id,
                callback: serverController.callbackHandler.register(newCallback)
            }));
        },
        create: function (job) {
            console.log("SEND JOB:")
            console.log(this.buildDTO(job));

            dataController.emit('message', new ServerMessage({
                t: this.messageType.create,
                j: this.buildDTO(job)
            }));
        },
        update: function (job) {
            dataController.emit('message', new ServerMessage({
                t: this.messageType.update,
                j: this.buildDTO(job)
            }));
        },
        delete: function (job) {
            dataController.emit('message', new ServerMessage({
                t: this.messageType.delete,
                j: this.buildDTO(job)
            }));
        }

    }, markt: {
        messageType: {
            getAll: "mga",
            update: "mu"
        },
        getAll: function (callback) {
            var newCallback = function () {
                callback(arguments[0], arguments[1], arguments[2], arguments[3]);
                serverController.getAllOnStartupCounter++;
                serverController.onLoadedGetAllOnStartup();
            };
            dataController.emit('message', new ServerMessage({
                t: this.messageType.getAll,
                callback: serverController.callbackHandler.register(newCallback)
            }));

        },
        update: function (markt) {
            dataController.emit('message', new ServerMessage({t: this.messageType.update, m: markt}));
        }
    },

    termin: {
        messageType: {
            create: "tc",
            get: "tg",
            getRange: "tgr",
            setTerminJob: "tsj"

        },

        parseDTO: function (sent_termin) {

            var date = new Date(sent_termin.Start);

            var newTermin = {
                id: sent_termin.id,
                title: sent_termin.Title,
                marktid: sent_termin.marktId,
                start: date,
                end: parseInt(sent_termin.End),
                allDay: sent_termin.AllDay,
                notizen: sent_termin.Notizen,
                lieferant: sent_termin.Lieferant,
                repeatDays: sent_termin.RepeatDays
            }

            return newTermin;

          },
          buildDTO: function (termin) {

            var termin = $.extend(true, {}, termin);
            //newTermin.timestamp_start = termin.timestamp_start.getTime();
            //newTermin.timestamp_end = termin.timestamp_end.getTime();


            var newTermin = {
                id: termin.id,
                Title: termin.title,
                Start: termin.start, //.format(),
                StartMilli: termin.start.getTime(), //.toDate().getTime(),
                AllDay: termin.allDay,
                Notizen: termin.notizen,
                Lieferant: termin.lieferant,
                RepeatDays: termin.repeatDays,
                jobId: termin.jobId,
                marktId: termin.marktId,
                alarm: termin.alarm
            };


            return newTermin;

        },

        //TODO: zum funktionieren bringen aka gegenstück im server schreiben
        get: function (lieferanten_id, callback) {


             var newCallback = function (list) {

                for (var i = 0; i < list.length; i++) {

                    list[i] = serverController.termin.parseDTO(list[i]);

                }
                return callback(list);
            };


            dataController.emit('message', new ServerMessage({
                t: this.messageType.get,
                lid: lieferanten_id,
                callback: serverController.callbackHandler.register(newCallback)
            }));
        },


        create: function (termin) {
            var smessage = new ServerMessage({
                t: this.messageType.create,
                e: this.buildDTO(termin)
            });
            console.log(smessage);
            dataController.emit('message',smessage );
        }, getRangeLieferant: function (lid, start, end, callback) {

            var newCallback = function (list) {
                for (var i = 0; i < list.length; i++) {
                    list[i] = serverController.termin.parseDTO(list[i]);
                }

                callback(list);

            };
            dataController.emit('message', new ServerMessage({t: this.messageType.getRange,today: (new Date()).getTime(), start: start.getTime(), end: end.getTime(), callback: serverController.callbackHandler.register(newCallback)}));
        }


    },


    getTemplatesOnLoginCounter: 0,
    getAllOnStartupCounter: 0,
    getAllOnStartupMax: 1,
    onLoadedGetAllOnStartup: function () {
        if (serverController.getAllOnStartupCounter == serverController.getAllOnStartupMax) {
            console.log("Get All complete")
        }

    },
    onLoadedGetTemplatesOnLogin: function () {
        if (serverController.getTemplatesOnLoginCounter == serverController.getAllOnStartupMax) {
            console.log("Get Templates complete")
        }

    }
}

