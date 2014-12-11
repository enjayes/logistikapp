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
            var callBackName = "cb" + Date.now() + ((Math.random() * 1000000.0) + "").replace(".", "");
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

    job: {
        messageType: {
            getAll: "jga",
            create: "jc",
            update: "ju",
            delete: "jd",
            get: "jg",
            updateOthers: "juo"

        },

        /*
         buildDTO: function (nachricht) {
         return {
         id: nachricht.id,
         datum: nachricht.datum.getTime(),
         nachricht: nachricht.nachricht,
         lieferanten: nachricht.lieferanten
         }
         },
         */

        buildDTO: function (job) {

            return{
               //general
                id: job.id,
                client_id: job.client_id,
                markt_id: job.markt_id,
                timestamp_start: job.timestamp_start.getTime(),
                timestamp_end: job.timestamp_end.getTime(),
                fixtermin: job.fixtermin,

    
                //status
                pending: job.pending,
                finished: job.finished,
                checked_out: job.checked_out, //lieferant hat den job explizit abgeschlossen, d.h. sich ausgeloggt
    
                //job_selector
                besuch: job.besuch,
                bestellung : job.bestellung,
                verraeumung : job.verraeumung,
                austausch : job.austausch,

                //lieferantenschein1
                t_ziel: job.t_ziel,
                t_grund: job.t_grund,
                t_thematik: job.t_thematik,
    
                //lieferantenschein2

                cb_auftrag_getaetigt: job.cb_auftrag_getaetigt,
                cb_mhd: job.cb_mhd,
                cb_ruecknahme : job.cb_ruecknahme, //Rücknahme
                cb_reklamation: job.cb_reklamation, //Reklamationsbearbeitung
                cb_warenaufbau: job.cb_warenaufbau, //Warenaufbau
                cb_umbau: job.cb_umbau, //Umbau
                cb_info_gespraech: job.cb_info_gespraech, //Info-Gespräch
                cb_nr_abgabe: job.cb_nr_abgabe, //Nummer-Abgabe
                t_vk_euro_abgabe: job.t_vk_euro_abgabe,
                cb_verkostung : job.cb_verkostung, //Verkostung
                cb_sortimentsinfo : job.cb_sortimentsinfo, //Sortimentsinfo
                cb_aktionsabsprache: job.cb_aktionsabsprache,
                cb_bemusterung: job.cb_bemusterung, //Bemusterung
                cb_verlosung: job.cb_verlosung //Verlosung
            }
        },
        parseDTO: function (job) {
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

