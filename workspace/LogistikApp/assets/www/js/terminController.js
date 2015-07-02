/**
 * Created by Norbert on 22.06.2015.
 */

var terminController = {
    lieferantVisitTimeMap:{},
    terminTolerance:2,
    setTerminCurrenJob: null,
    terminListe: null,
    initialize: function(){
        serverController.termin.get(contactController.lieferant.id, function (termine) {
            console.dir("termine.liste");
            console.dir(termine);
            terminListe = termine;
            //this.termine = termine;

        })
    }
    ,
    deleteTermin: function (lieferanten_id) {
        if (terminController.lieferantVisitTimeMap[lieferanten_id]) {
            delete  terminController.lieferantVisitTimeMap[lieferanten_id];
        }
    },
    startTermin: function (lieferanten_id) {
        console.log("startTermin!!!!");
        var terminData = {s:new Date(),e:0,jid:misc.getUniqueID()};
        terminController.lieferantVisitTimeMap[lieferanten_id] = terminData;
        serverController.job.startVisit(terminData.s.getTime(),lieferanten_id,terminData.jid);

    },
    endTermin: function (job){
        console.log("endTermin -> setTerminJob!!!");
        var terminData = terminController.lieferantVisitTimeMap[job.lieferanten_id];
        if(terminData) {
            terminData.e = new Date();
            job.id = terminData.jid;

            job.timestamp_start = terminData.s;
            job.timestamp_end   = terminData.e;


            var start = new Date();
            var end = new Date();
            start.setHours(start.getHours() - 24);
            end.setHours(end.getHours() + 24);
            terminController.setTerminCurrenJob = job;
            console.log("search for matching termine");
            serverController.termin.getRangeLieferant(job.lieferanten_id, start, end, terminController.setJobTermin);
            delete terminController.lieferantVisitTimeMap[job.lieferanten_id];
        }
        console.log("endTermin done!!!")

    },
    getTerminStart: function (lieferanten_id) {
        var terminData = terminController.lieferantVisitTimeMap[lieferanten_id];
        console.log("getTerminStart: "+terminData+ "#######################################################################")
        if (terminData) {
            return terminData.s.getTime();
        }
        else {
            return 0;
        }
    },
    getTerminEnd: function (lieferanten_id) {
        var terminData = terminController.lieferantVisitTimeMap[lieferanten_id];
        if (terminData) {
            return terminData.e.getTime();
        }
        else {
            return 0;
        }
    }
    ,
    setJobTermin: function(list){
        console.log("setTerminJobCallback:");
        var startNow = new Date();
        var endNow = new Date();
        console.log("terminController.terminTolerance: "+terminController.terminTolerance);
        console.log("start.getHours(): "+startNow.getHours());

        startNow.setHours(startNow.getHours()+terminController.terminTolerance);
        endNow.setHours(endNow.getHours()-terminController.terminTolerance);
        terminController.setTerminCurrenJob.termin_id = "";
        for (var i = 0; i < list.length; i++) {
            if(list[i].marktId==configData.markt.id) {
                var terminStart = list[i].start;
                var terminEnd = list[i].end;
                if (isNaN(terminEnd) || !terminEnd) {
                    terminEnd = new Date(terminStart);
                    terminEnd.setHours(terminEnd.getHours() + 1);
                }
                console.log("index_" + i + ": ");
                console.log(terminStart);
                console.log(terminEnd);
                console.log(startNow.getTime() + " > " + terminStart.getTime());
                console.log(endNow.getTime() + " < " + terminEnd.getTime());
                if (startNow.getTime() > terminStart.getTime() && endNow.getTime() < terminEnd.getTime()) {
                    console.log("serverController.socket.emit('message', new ServerMessage({t: this.messageType.setVisit))");
                    terminController.setTerminCurrenJob.termin_id = list[i].id;
                    //serverController.socket.emit('message', new ServerMessage({t: serverController.termin.messageType.setVisit,start:start,end:end,tid:list[i].id ,jid: serverController.termin.setTerminCurrenJob.id}));
                }
            }

        }
        serverController.job.create(terminController.setTerminCurrenJob);
    }

}