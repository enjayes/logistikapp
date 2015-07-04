/**
 * Created by Norbert on 22.06.2015.
 */

var terminController = {
    lieferantVisitTimeMap:{},
    terminTolerance:1,
    setTerminCurrenJob: null,
    termine:[],
    load: function(){
        console.log("load termine##########################################################");
        try {
            if (localStorage.termineListe) {
                terminController.termine = JSON.parse(localStorage.termineListe);
            }
        }
        catch(e){}
        console.log("serverController.termin.getAll");
        serverController.termin.getAll(terminController.setTermine);
    },
    refreshload: function(){
        terminController.load();
        setTimeout(terminController.refreshload,3600000);
    }
    ,setTermine: function (termine){
        console.log("set termine######################");
        console.dir(termine);
        if(termine) {

            terminController.termine = termine;
            localStorage.termineListe = JSON.stringify(terminController.termine);;
        }
    },
    addTermin:function(termin) {
        terminController.termine.push(termin);
        localStorage.termineListe = JSON.stringify(terminController.termine);
    },

    initialize: function(){

        /*
        serverController.termin.get(contactController.lieferant.id, function (termine) {
            console.dir("termine.liste");
            console.dir(termine);
            this.termine = termine;
            //this.termine = termine;

        })

        */

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
    endTermin: function (job) {
        console.log("endTermin -> setTerminJob!!!");
        var terminData = terminController.lieferantVisitTimeMap[job.lieferanten_id];
        if (terminData) {


            console.log("terminData exists!");
            terminData.e = new Date();
            job.id = terminData.jid;

            job.timestamp_start = terminData.s;
            job.timestamp_end = terminData.e;
        }
        else
        {
            job.id              = misc.getUniqueID();
            job.timestamp_start = new Date();
            job.timestamp_end   = new Date();
        }

            serverController.job.create(job);
            /*
               var start = new Date();
             var end = new Date();
             start.setHours(start.getHours() - 24);
             end.setHours(end.getHours() + 24);
            terminController.setTerminCurrenJob = job;
            console.log("search for matching termine");
            serverController.termin.getRangeLieferant(job.lieferanten_id, start, end, terminController.setJobTermin);


        }
             */
        delete terminController.lieferantVisitTimeMap[job.lieferanten_id];
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


}