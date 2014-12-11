/**
 * serverController.
 *
 *
 *
 *
 * @date 21.11.14 - 00:19
 * @copyright munichDev UG
 */




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
            var callBackName = "cb" + Date.now() + "x"+((Math.random() * 1000000.0) + "").replace(".", "");
            this[callBackName] = callback;
            return callBackName;
        }
    },
    initialize: function (callback) {
        //Load Socket io and connect

        serverController.socket = io.connect(preferences.server);


        serverController.socket.on('disconnect', function () {
            console.log("DISCONNECT")
        });

        //On Message
        serverController.socket.on('message', function (msg) {



            //Server Connected, send Connection message back
            if (msg.t == serverController.messageType.connection) {
                serverController.socket.emit('message', new ServerMessage({callback: serverController.callbackHandler.register(callback)}, serverController.messageType.connection));

            }
            else if (msg.t == serverController.messageType.callback) {
                //Execute Callback
                if (msg.callback && serverController.callbackHandler[msg.callback]) {
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
        login: function (pinSha, callback) {
            var newCallback = function () {
                callback(arguments[0], arguments[1], arguments[2], arguments[3]);
            };
            serverController.socket.emit('message', new ServerMessage({t: this.messageType.login,  p: pinSha,callback: serverController.callbackHandler.register(newCallback)}));
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
        }

    },
    getAllOnStartupCounter: 0,
    getAllOnStartupMax: 1,
    onLoadedGetAllOnStartup: function () {
        if (serverController.getAllOnStartupCounter == serverController.getAllOnStartupMax) {
            console.log("Get All complete")
        }

    }
}

