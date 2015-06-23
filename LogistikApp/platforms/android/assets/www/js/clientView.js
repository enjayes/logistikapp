
var configView = {
    pinPad: null
}

var loginView = {
    pinPad: null
}

var switchView = null;
//Logik hinter den Buttons der Besucherschein-Schaltflächen
var clientView = {
    lieferant: null,
    viewTitle:"start_screen",
    currentViewDiv: 0,
    //Objekt wird initialisiert, Besucherschein-Objekt (job) wird erstellt
    initialize: function () {
        switchView = clientView.switchView;
        switchView(clientView.viewTitle);


        var id = misc.getUniqueID();
        var job = new Job(id);
        var that = this;


        var goodByeTimeout = null;


        $("#popupVorlagen").show();
        $("#popupNFCLogin").show();
        $("#popupWriteNFC").show();



        /*
         $("#b_kalender").click(function()
         {
         //  $('#termine_menu').show();
         $('#contact_daten_menu').hide();
         loginController.waitForLogin();

         $("#startScreen").show();

         });
         */


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


    },



    getLieferantFullName: function () {
        if(this.lieferant) {
            var name = this.lieferant.name;
            if (this.lieferant.vorname && this.lieferant.vorname.trim() != "")
                name = this.lieferant.vorname.trim() + " " + name;
            return name
        }
        else{
            return "";
        }
    },

    switchView: function(fadeInElement){

            clientView.viewTitle = fadeInElement;
        var fadeOutDiv = "#gui"+clientView.currentViewDiv;
        clientView.currentViewDiv = 1-clientView.currentViewDiv
        var fadeInDiv = "#gui"+clientView.currentViewDiv;
        $(fadeOutDiv).fadeOut(); //todo
        $( fadeInDiv).load( "html/"+fadeInElement+".html","",function(){
            $(fadeInDiv).trigger('create');
            clientView.initializeView(fadeInElement);
            $(fadeOutDiv).hide();
            $(fadeInDiv).show();
            $(fadeOutDiv).empty();
                if (misc.isMobileApp()) {
                    FastClick.attach(document.body);

                }
        }
        );
    },
    initializeView: function(fadeInElement){
        if(fadeInElement=="start_screen"){
            $("#start_qr_code_anmelden").click(function () {
                $.mobile.loading('show')
                qrCodeController.scan();
            });

            $("#start_anmelden").click(function () {
                switchView('lieferanten_login');

            });
            $("#start_nfc_code_anmelden").click(function () {

                $("#popupNFCLogin").popup("open");

            });
            //Öffnet die Konfigurations-Ansicht TODO
            $("#b_konfig").click(function () {

                //configView.pinPad.clear();

                clientView.switchView("konfi_login_menue");

            });
        }
        else if (fadeInElement=="contact_daten_menu"){
            if(this.lieferant) {
                contactController.set(this.lieferant.id, this.lieferant);
            }
            $("#b_kalender").click(function () {
                switchView('termine_menu');
            });

            $("#weiter_waitForLogin").click(function () {

                contactController.store();
                //$("#jobSelector").show();
                switchView("wait_goodbye");

                loginController.waitForLogin();

                goodByeTimeout = setTimeout(function () {

                    switchView("start_screen");
                    goodByeTimeout = null;

                }, 20000);


            });

            $("#weiter_jobSelector").click(function () {
                contactController.store();
                switchView("job_selector");
            });

            $("#zurueck_start").click(function () {
                loginController.logout();
                switchView("start_screen");

            });


        }
        else if (fadeInElement=="konfi_login_menue"){
            configView.pinPad = new PinPad("#PINcodeConfig", function (code) {
                if (code == "1234") {
                    clientView.switchView("konfi_menue");
                    configView.pinPad.clear();
                }
            })
            $("#konfi_zurueck_start").click(function () {
                switchView('start_screen');

            });
        }
        else if (fadeInElement=="konfi_menue"){

            if (typeof(localStorage) !== "undefined") {
                $("#t_server").val(localStorage.servername);
                $("#t_port").val(localStorage.server_port);
                $("#markt_id").val(localStorage.markt_id);

            }
            else {
                alert("Error: HTML5 Storage not supported");
            }

            if(serverController.connected==true){
                $("#server_status").css("color", "rgb(84, 191, 84)").text("Mit Server verbunden...");
            }
            else{
                $("#server_status").css("color", "rgb(191,84, 84)").text("Keine Serververbindung...");
            }

            $("#b_write_nfc").click(function () {
                nfcController.startWriteListener();
                $("#popupWriteNFC").popup("open");

            });

            $("#open_admin").click(function () {
                //url = "http://" + localStorage.servername + ":" + localStorage.server_port;
                url = "http://" + localStorage.servername + ":" + localStorage.server_port;
                alert(url);
                misc.openLink(url);

            });
            $("#close_app").click(function () {
                navigator.app.exitApp();
            });
            $("#save_config").click(function () {
                logistikapp.servername = $("#t_server").val();
                logistikapp.server_port = $("#t_port").val();
                logistikapp.markt_id = $("#markt_id").val();
                if (typeof(localStorage) !== "undefined") {
                    localStorage.servername = logistikapp.servername;
                    localStorage.server_port = logistikapp.server_port;
                    localStorage.markt_id = logistikapp.markt_id;

                }
                else {
                    alert("Error: HTML5 Storage not supported");

                }

                //Establish Socket Connection
                serverController.initialize();

                //Set Markt title
                $("#marktname_title").text(logistikapp.markt_id);

                notifications.showWithTimeout("Gespeichert!");


            });

            //Schließt die  Konfigurations-Ansicht
            $("#config_zurueck").click(function () {
                switchView('start_screen');
            });
        }
        else if (fadeInElement=="lieferanten_login"){
            loginView.pinPad = new PinPad("#PINcode", function (code) {

                loginController.clear();
                loginController.login(code);
                loginView.pinPad.clear();

            })
            $("#login_zurueck_start").click(function () {

                switchView("start_screen");

            });
        }
        else if (fadeInElement=="termine_menue") {
            termineListView.initialize();
        }
        else if (fadeInElement=="termine_eintragen"){
            terminView.initialize();
        }
        else if (fadeInElement=="job_selector"){
            $(".greetingLieferant").html(clientView.getLieferantFullName());
            $("#weiter_Aufgaben").click(function () {
                switchView("lieferantenschein_1");


            });

            $("#job_zurueck_kontakt").click(function () {
                switchView("goodbye");


                loginController.logout();

                goodByeTimeout = setTimeout(function () {

                    switchView("start_screen");
                    goodByeTimeout = null;

                }, 10000);


            });

        }
        else if (fadeInElement=="lieferantenschein_1"){
            $(".greetingLieferant").html(clientView.getLieferantFullName());
            $("#weiter_lieferantenschein1").click(function () {
                switchView("lieferantenschein_2");
            });


            $("#zurueck_lieferantenschein1").click(function () {
                switchView("job_selector");
            });
        }
        else if(fadeInElement=="lieferantenschein_2") {
            $(".greetingLieferant").html(clientView.getLieferantFullName());
            $("#zurueck_lieferantenschein2").click(function () {
                switchView("lieferantenschein_1");

            });

            $("#weiter_lieferantenschein2").click(function () {
                switchView("logout")
            });
        }
        else if(fadeInElement=="logout"){
            $(".greetingLieferant").html(clientView.getLieferantFullName());
            $("#zurueck_logout").click(function () {
                switchView("lieferantenschein_2");

            });

            $("#checkout_logout").click(function () {

                job = that.check_input(job);
                job = that.check_out(job);

                if (contactController.lieferant) {
                    job.lieferanten_id = contactController.lieferant.id;
                }
                else {
                    alert("Kein Lieferant eingelogged!");
                }

                //Speichert den Job + sucht den dazugehörigen Termin:
                terminController.endTermin(job);
                console.log("DONE - > GOODBYe!")

                switchView("goodbye");

                if ($("#t_notizen").val() != "") {
                    nachrichtText = "Notiz: " + $("#t_notizen").val();

                    var nachricht = {
                        id: misc.getUniqueID(),
                        lieferantid: job.lieferanten_id,
                        read: 0,
                        datum: new Date(),
                        nachricht: nachrichtText
                    };

                    console.log("SENDE NACHRICHT:");
                    console.dir(nachricht);
                    serverController.antwortNachricht.create(nachricht);
                }
                loginController.logout();

                goodByeTimeout = setTimeout(function () {


                    loginController.logout();

                    switchView("start_screen");
                    goodByeTimeout = null;

                }, 10000);

            });
        }
        else if(fadeInElement== "wait_goodbye"){
            $("#waitgoodbye_button").click(function () {
                if (goodByeTimeout != null) {
                    clearTimeout(goodByeTimeout);
                    goodByeTimeout = null;
                }

                switchView("start_screen");

            });


        }
        else if(fadeInElement== "goodbye"){
            $("#goodbye_button").click(function () {
                if (goodByeTimeout != null) {
                    clearTimeout(goodByeTimeout);
                    goodByeTimeout = null;
                }
                loginController.logout();
                switchView("start_screen");

            });


        }

    }

    ,
    //überträgt die Daten aus dem GUI in ein Besucherschein-Objekt (job)
    check_input: function (job) {

        console.log("Lieferant")
        console.dir(this.lieferant)
        if (this.lieferant) {
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



    //GUI zurücksetzen
    clearJob: function () {
        var id = misc.getUniqueID();
        var clearJob = new Job(id);
        clientView.setJob(clearJob);
    }
}

