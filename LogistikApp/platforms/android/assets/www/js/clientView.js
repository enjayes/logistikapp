

//Logik hinter den Buttons der Besucherschein-Schaltflächen
var clientView = {
    lieferant: null,
    getLieferantFullName: function () {
        var name = this.lieferant.name;
        if (this.lieferant.vorname && this.lieferant.vorname.trim() != "")
            name = this.lieferant.vorname.trim() + " " + name;
        return name
    },

    //überträgt die Daten aus dem GUI in ein Besucherschein-Objekt (job)
    check_input: function (job) {

        console.log("Lieferant")
        console.dir(this.lieferant)
        if(this.lieferant) {
            job.lieferanten_id = this.lieferant.id;
        }

        job.markt_id = logistikapp.markt_id;

        if ($('#fixtermin').is(":checked")) {
            job.fixtermin = true;
        }
        else {
            job.fixtermin = false;
        }

        if ($('#cb_besuch').is(":checked")) {
            job.besuch = true;
        }
        else {
            job.besuch = false;
        }
        if ($('#cb_bestellung').is(":checked")) {
            job.bestellung = true;
        }
        else {
            job.bestellung = false;
        }
        if ($('#cb_verraeumung').is(":checked")) {
            job.verraeumung = true;
        }
        else {
            job.verraeumung = false;
        }
        if ($('#cb_austausch').is(":checked")) {
            job.austausch = true;
        }
        else {
            job.austausch = false;
        }

        job.t_ziel = $("#t_ziel").val();
        job.t_grund = $("#t_grund").val();
        job.t_thematik = $("#t_thematik").val();
        job.gespraechspartner = $("#gespraechspartner").val();

        if ($('#cb_auftrag_getaetigt').is(":checked")) {
            job.cb_auftrag_getaetigt = true;//Auftrag getätigt
        }
        else {
            job.cb_auftrag_getaetigt = false;
        }
        if ($('#cb_mhd').is(":checked")) {
            job.cb_mhd = true;//MHD-Kontrolle
        }
        else {
            job.cb_mhd = false;
        }
        if ($('#cb_ruecknahme').is(":checked")) {
            job.cb_ruecknahme = true; //Rücknahme
        }
        else {
            job.cb_ruecknahme = false; //Rücknahme
        }

        if ($('#cb_warenaufbau').is(":checked")) {
            job.cb_warenaufbau = true;
        }
        else {
            job.cb_warenaufbau = false;
        }

        if ($('#cb_reklamation').is(":checked")) {
            job.cb_reklamation = true;
        }
        else {
            job.cb_reklamation = false; //Reklamationsbearbeitung
        }

        if ($('#cb_umbau').is(":checked")) {
            job.cb_umbau = true;
        }
        else {
            job.cb_umbau = false;
        }

        if ($('#cb_info_gespraech').is(":checked")) {
            job.cb_info_gespraech = true;
        }
        else {
            job.cb_info_gespraech = false;
        }

        if ($('#cb_nr_abgabe').is(":checked")) {
            job.cb_nr_abgabe = true;
            $("#t_vk_euro_abgabe").val(job.t_vk_euro_abgabe);
            $("#t_warengruppe").val(job.t_warengruppe);

        }
        else {
            job.cb_nr_abgabe = false;
        }

        job.t_vk_euro_abgabe = $("#t_vk_euro_abgabe").val();

        if ($('#cb_verkostung').is(":checked")) {
            job.cb_verkostung = true;
        }
        else {
            job.cb_verkostung = false;
        }

        if ($('#cb_sortimentsinfo').is(":checked")) {
            job.cb_sortimentsinfo = true;
        }
        else {
            job.cb_sortimentsinfo = false;
        }

        if ($('#cb_aktionsabsprache').is(":checked")) {
            job.cb_aktionsabsprache = true;
        }
        else {
            job.cb_aktionsabsprache = false;
        }

        if ($('#cb_bemusterung').is(":checked")) {
            job.cb_bemusterung = true;
        }
        else {
            job.cb_bemusterung = false;
        }

        if ($('#cb_verlosung').is(":checked")) {
            job.cb_verlosung = true;
        }
        else {
            job.cb_verlosung = false;
        }

        if ($("#t_notizen").val() != "") {
            //TODO
        }
        else {
        }

        return job;

    },

    //erstellt Zeitstempel und markiert Ende der Bearbeitung
    check_out: function (job) {
        job.timestamp_end = new Date();
        job.finished = true;
        job.pending = false;
        job.checked_out = true;
        console.dir(job);
        return job;
    },

    //ein Besucherschein-Objekt wird in das GUI geladen
    setJob: function (job) {

        $('#fixtermin').prop('checked', job.fixtermin);


        $('#cb_besuch').prop('checked', job.besuch);

        $('#cb_bestellung').prop('checked', job.bestellung);


        $('#cb_verraeumung').prop('checked', job.verraeumung);

        $('#cb_austausch').prop('checked', job.austausch);

        $("#t_ziel").val(job.t_ziel);

        $("#t_grund").val(job.t_grund);

        $("#t_thematik").val(job.t_thematik);

        $("#gespraechspartner").val(job.gespraechspartner);

        $('#cb_auftrag_getaetigt').prop('checked', job.cb_auftrag_getaetigt);

        $('#cb_mhd').prop('checked', job.cb_mhd);


        $('#cb_ruecknahme').prop('checked', job.cb_ruecknahme);


        $('#cb_warenaufbau').prop('checked', job.cb_warenaufbau);


        $('#cb_reklamation').prop('checked', job.cb_reklamation);


        $('#cb_umbau').prop('checked', job.cb_umbau);


        $('#cb_info_gespraech').prop('checked', job.cb_info_gespraech);


        $('#cb_nr_abgabe').prop('checked', job.cb_nr_abgabe);

        $("#t_vk_euro_abgabe").val(job.t_vk_euro_abgabe);
        $("#t_warengruppe").val(job.t_warengruppe);


        $('#cb_verkostung').prop('checked', job.cb_verkostung);


        $('#cb_sortimentsinfo').prop('checked', job.cb_sortimentsinfo);


        $('#cb_aktionsabsprache').prop('checked', job.cb_aktionsabsprache);


        $('#cb_bemusterung').prop('checked', job.cb_bemusterung);


        $('#cb_verlosung').prop('checked', job.cb_verlosung);

        $("#t_notizen").val(job.t_notizen);
        $('[type=checkbox]').checkboxradio("refresh");


    },

    //Objekt wird initialisiert, Besucherschein-Objekt (job) wird erstellt
    initialize: function () {
        var id = misc.getUniqueID();
        var job = new Job(id);
        var that = this;

        terminView.initialize();
        var goodByeTimeout = null;


        $("#popupVorlagen").show();
        $("#popupNFCLogin").show();
        $("#popupWriteNFC").show();


        $("#waitgoodbye_button").click(function () {
            if (goodByeTimeout != null) {
                clearTimeout(goodByeTimeout);
                goodByeTimeout = null;
            }
            $("#waitgoodbye").hide();
            $("#startScreen").show();

        });


        $("#job_zurueck_kontakt").click(function () {
            $("#goodbye").show();
            $("#jobSelector").hide();

            loginController.logout();

            goodByeTimeout = setTimeout(function () {

                $("#goodbye").hide();
                $("#startScreen").show();
                goodByeTimeout = null;

            }, 10000);


        });


        $("#zurueck_start").click(function () {
            loginController.logout();
            $('#startScreen').show();
            $('#contact_daten_menu').hide();


        });

        $("#zurueck_kontakt").click(function () {
            $('#contact_daten_menu').show();
            $('#termine_menu').hide();


        });

        $("#b_kalender").click(function () {
            $('#termine_menu').show();
            $('#contact_daten_menu').hide();

        });

        /*
         $("#b_kalender").click(function()
         {
         //  $('#termine_menu').show();
         $('#contact_daten_menu').hide();
         loginController.waitForLogin();

         $("#startScreen").show();

         });
         */


        $("#weiter_jobSelector").click(function () {
            contactController.store();
            $("#contact_daten_menu").hide();
            $("#jobSelector").show();
        });


        $("#weiter_waitForLogin").click(function () {

            contactController.store();
            //$("#jobSelector").show();
            $("#waitgoodbye").show();
            $("#contact_daten_menu").hide();

            loginController.waitForLogin();

            goodByeTimeout = setTimeout(function () {

                $("#waitgoodbye").hide();
                $("#startScreen").show();
                goodByeTimeout = null;

            }, 20000);


        });


        $("#weiter_Aufgaben").click(function () {

            $("#lieferantenschein1").show();
            $("#jobSelector").hide();

        });

        $("#weiter_lieferantenschein1").click(function () {

            $("#lieferantenschein2").show();
            $("#lieferantenschein1").hide();

        });


        $("#zurueck_lieferantenschein1").click(function () {


            $("#jobSelector").show();
            $("#lieferantenschein1").hide();

        });

        $("#zurueck_lieferantenschein2").click(function () {
            $("#lieferantenschein1").show();
            $("#lieferantenschein2").hide();

        });


        $("#weiter_lieferantenschein2").click(function () {
            $("#logout").show();
            $("#lieferantenschein2").hide();

        });

        $("#goodbye_button").click(function () {
            if (goodByeTimeout != null) {
                clearTimeout(goodByeTimeout);
                goodByeTimeout = null;
            }
            loginController.logout();
            $("#goodbye").hide();
            $("#startScreen").show();

        });


        $("#checkout_logout").click(function () {
            job.id =  misc.getUniqueID();
            job = that.check_input(job);
            job = that.check_out(job);

            if (contactController.lieferant) {
                job.lieferanten_id = contactController.lieferant.id;
            }
            else {
                alert("Kein Lieferant eingelogged!");
            }


            //TODO add selected termin

            job.terminId = "e40d6e34-b43b-4160-885d-e7672b67d7ca"; //Id of Termin
            job.terminStartMilli = 1420588800000; //Start of Termin
            job.terminEndMilli = 1420588800000; //Stop of Termin

            serverController.job.create(job);

            $("#goodbye").show();
            $("#logout").hide();
            nachrichtText = $("#t_notizen").val();

            var nachricht = {id: misc.getUniqueID(), lieferanten:[job.lieferanten_id] , read: false, datum: new Date(), nachricht: nachrichtText, maerkte: [job.markt_id]};

            console.log("SENDE NACHRICHT:");
            console.dir(nachricht);
            serverController.nachricht.create(nachricht);

            loginController.logout();

            goodByeTimeout = setTimeout(function () {


                loginController.logout();
                $("#goodbye").hide();
                $("#startScreen").show();
                goodByeTimeout = null;

            }, 10000);

        });


        $("#vorlage_logout").click(function () {
            $("#vorlage_benennen").show();

        });

        $("#speichern_vorlage").click(function () {
            if ($("#vorlagen_name").val() == "") {
                $("#vorlage_leer").show();
            }
            else {
                job.template_name = $("#vorlagen_name").val();
                $("#vorlage_benennen").hide();
                $("#vorlage_logout").hide();
                $("#vorlage_leer").hide();
            }


        });

        $("#zurueck_logout").click(function () {
            $("#logout").hide();
            $("#lieferantenschein2").show();

        });


    },

    //GUI zurücksetzen
    clearJob: function () {
        var id = misc.getUniqueID();
        var clearJob = new Job(id);
        clientView.setJob(clearJob);
    }


}

