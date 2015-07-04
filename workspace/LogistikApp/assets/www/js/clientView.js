
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
    loadedTitle: "",
    currentViewDiv: 0,
    job:null,
    debugMode:0,
    //Objekt wird initialisiert, Besucherschein-Objekt (job) wird erstellt
    initialize: function () {




        switchView = clientView.switchView;
        switchView(clientView.viewTitle);


        var id = misc.getUniqueID();
        clientView.job = new Job(id);

        var goodByeTimeout = null;

        $("#popupVorlagen").show();
        $("#popupNFCLogin").show();
        $("#popupWriteNFC").show();


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

        console.log(clientView.loadedTitle+" ->  clientView.setJob");
        var fadeOutDiv = "#gui"+clientView.currentViewDiv;

        if(clientView.viewTitle=="admin"){
            fadeOutDiv = "#gui2"
            $("#divbackground").show();
            $("#centerapp").show();
        }
        clientView.loadedTitle = clientView.viewTitle;
        clientView.job = clientView.setJob(clientView.job);
        clientView.viewTitle = fadeInElement;


        clientView.currentViewDiv = 1-clientView.currentViewDiv
        var fadeInDiv = "#gui"+clientView.currentViewDiv;
        if(clientView.viewTitle=="admin"){
            fadeInDiv = "#gui2"
            $("#divbackground").hide();
            $("#centerapp").hide();
            var height = window.innerHeight+"px";
            $("#gui2").attr('style', "display:none;width:100%;height:"+height);
        }
        if(fadeOutDiv!=fadeInDiv) {
            $(fadeOutDiv).fadeOut(); //todo
            $(fadeInDiv).load("html/" + fadeInElement + ".html", "", function () {
                    $(fadeInDiv).trigger('create');
                    clientView.setGUI(clientView.job);
                    $(fadeOutDiv).hide();
                    $(fadeInDiv).show();

                    $(fadeOutDiv).empty();
                    clientView.initializeView(fadeInElement);

                    if (misc.isMobileApp()) {
                        FastClick.attach(document.body);

                    }

                }
            );
        }
    },

    checkNumbers: function(eingabe) {
        var bad     = false;
        var erlaubt = '0123456789,';
        for(i=0;i <= eingabe.length; i++) { if(erlaubt.indexOf(eingabe.charAt(i)) < 0  ) {bad = true;break;}; }
        if(bad == true){
            notifications.showError("Bitte überprüfen sie ihre Eingaben (Zahlenwerte)");
            return false;
        }
        return true;
    }
    ,
    initializeView: function(fadeInElement){
        if(fadeInElement=="start_screen"){
            if(clientView.debugMode==1){
                $('#jobButton').show();
            }
            else {
                $('#jobButton').hide();
            }
            $("#progresssteps").hide();
            $('weiter_jobSelectorPopup').hide();
            // $('#callButton').hide();
            var id = misc.getUniqueID();
            clientView.job = new Job(id);
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

                switchView("konfi_login_menue");

            });
        }
        else if (fadeInElement=="contact_daten_menu"){
            if(clientView.debugMode==1){
                $('#weiter_jobSelectorPopup').show();
            }
            else {
                $('#weiter_jobSelectorPopup').hide();
            }
            $("#popupJobselector").show();
            $("#progresssteps").attr("src","img/progress1.png");
            $("#progresssteps").show();
            //$('#callButton').fadeIn();
            if(this.lieferant) {
                console.log("################################################################ LIEFERANT");
                console.log(this.lieferant);
                contactController.set(this.lieferant.id, this.lieferant);
            }
            $("#b_kalender").click(function () {
                switchView('termine_menue');
            });

            $("#weiter_waitForLogin").click(function () {

                contactController.store();
                //$("#jobSelector").show();

                loginController.waitForLogin();
                switchView("wait_goodbye");
                goodByeTimeout = setTimeout(function () {
                    goodByeTimeout = null;
                    switchView("start_screen");
                }, 20000);


            });


            $("#close_popupJobselector").click(function () {
                $("#popupJobselector").popup("close");
            });

            $("#weiter_jobSelectorPopup").click(function () {
                switchView("job_selector")
                // $("#popupJobselector").popup("open");
            });

            $("#weiter_jobSelector").click(function () {
                $("#popupJobselector").popup("close");

                contactController.store();
                setTimeout(function(){ switchView("job_selector")},1200);
            });

            $("#zurueck_start").click(function () {
                loginController.logout();
                switchView("start_screen");

            });


        }
        else if (fadeInElement=="konfi_login_menue"){
            configView.pinPad = new PinPad("#PINcodeConfig", function (code) {
                if (code == "3792") {
                    configView.pinPad.clear();
                    clientView.switchView("konfi_menue");
                }
            })
            $("#konfi_zurueck_start").click(function () {
                switchView('start_screen');

            });
        }
        else if (fadeInElement=="admin"){
            $("#admin_zurueck").click(function () {
                switchView('konfi_menue');
            })

            var url =  localStorage.servername + ":" + localStorage.server_port;
            if(url.search("http://")==-1){
                url = "http://" +url;
            }
            $("#adminframe").attr('src', url);
            $.mobile.loading( "show");
            $("#adminframe").load(function(){
                $.mobile.loading( "hide");
            })


        }
        else if (fadeInElement=="konfi_menue"){

            if (typeof(localStorage) !== "undefined") {

                $("#t_server").val(localStorage.servername);//nodejs-edeka.rhcloud.com");//);
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
                switchView('lieferanten_login_nfc');
            });

            $("#open_admin").click(function () {
                switchView('admin');
                //url = "http://" + localStorage.servername + ":" + localStorage.server_port;
                /*
                 url =  localStorage.servername + ":" + localStorage.server_port;
                 if(url.search("http://")==-1){
                 url = "http://" +url;
                 }
                 misc.openLink(url);
                 */

            });
            $("#close_app").click(function () {
                lieferantenController.saveLieferanten();
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
        else if (fadeInElement == "lieferanten_login_nfc"){
            loginView.pinPadNFC = new PinPad("#PINcodeNFC", function (code) {
                loginController.clear();
                loginController.writeNFClogin(code);
                loginView.pinPadNFC.clear();

            })
            $("#login_zurueck_start_nfc").click(function () {
                switchView("start_screen");

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
        else if (fadeInElement=="termin_eintragen"){
            terminView.initialize();
        }
        else if (fadeInElement=="job_selector"){
            $("#progresssteps").attr("src","img/progress3.png");
            $("#progresssteps").show();
            //$('#callButton').fadeIn();
            $(".greetingLieferant").html(clientView.getLieferantFullName());
            $("#weiter_Aufgaben").click(function () {
                switchView("lieferantenschein_2");


            });

            $("#job_zurueck_kontakt").click(function () {
                loginController.logout();
                switchView("goodbye");

                goodByeTimeout = setTimeout(function () {

                    goodByeTimeout = null;
                    switchView("start_screen");

                }, 10000);


            });

        }
        else if (fadeInElement=="lieferantenschein_1"){
            $("#progresssteps").attr("src","img/progress4.png");
            $("#progresssteps").show();
            $(".greetingLieferant").html(clientView.getLieferantFullName());
            $("#weiter_lieferantenschein1").click(function (){

                switchView("lieferantenschein_2");

            });


        }
        else if(fadeInElement=="lieferantenschein_2") {
            $("#progresssteps").attr("src", "img/progress4.png");
            $("#progresssteps").show();
            $(".greetingLieferant").html(clientView.getLieferantFullName());
            $("#zurueck_lieferantenschein2").click(function () {
                switchView("job_selector");

            });

            $("#weiter_lieferantenschein2").click(function () {
                if(!$('#cb_nr_abgabe').is(":checked") || ($("#t_vk_euro_abgabe").val() != "" && $("#t_warengruppe").val() != ""))
                {
                    if(clientView.checkNumbers( $("#t_vk_euro_abgabe").val())){



                        switchView("logout");
                    }
                }
                else{
                    notifications.showError("Bitte überprüfen sie ihre Eingaben (Natural-Rabatt)");
                }
            });



            $("#t_warengruppe").bind('input propertychange',function () {
                if($("#t_vk_euro_abgabe").val() != "" || $("#t_warengruppe").val()!=""){
                    $('#cb_nr_abgabe').prop('checked', true);
                }
                else
                {
                    $('#cb_nr_abgabe').prop('checked', false);
                }
                $('[type=checkbox]').checkboxradio("refresh");
            });

            $("#t_vk_euro_abgabe").bind('input propertychange',function () {
                if($("#t_vk_euro_abgabe").val() != "" || $("#t_warengruppe").val()!=""){
                    $('#cb_nr_abgabe').prop('checked', true);
                }
                else
                {
                    $('#cb_nr_abgabe').prop('checked', false);
                }
                $('[type=checkbox]').checkboxradio("refresh");
            });
        }
        else if(fadeInElement=="logout"){
            $("#progresssteps").attr("src", "img/progress5.png");
            $("#progresssteps").show();

            $(".greetingLieferant").html(clientView.getLieferantFullName());


            $("#zurueck_lieferantenschein1").click(function () {
                switchView("job_selector");
            });


            $("#vorlage_logout").click(function () {
                $("#vorlage_benennen").show();

            });

            $("#speichern_vorlage").click(function () {
                if ($("#vorlagen_name").val() == "") {
                    $("#vorlage_leer").show();
                }
                else {
                    clientView.template_name = $("#vorlagen_name").val();
                    $("#vorlage_benennen").hide();
                    $("#vorlage_logout").hide();
                    $("#vorlage_leer").hide();
                }


            });

            $("#zurueck_logout").click(function () {

                switchView("lieferantenschein_2");

            });
            $("#weiter_lieferantenschein3").click(function () {



                clientView.job = clientView.check_input(clientView.job);
                clientView.job = clientView.check_out(clientView.job);

                if (contactController.lieferant) {
                    clientView.job.lieferanten_id = contactController.lieferant.id;
                }
                else {
                    alert("Kein Lieferant eingelogged!");
                }
                //Speichert den Job + sucht den dazugehörigen Termin:

                if ($("#t_notizen").val() != "") {
                    clientView.job.t_notizen = window.btoa($("#t_notizen").val());

                }
                switchView("logout_check");


            })
            clientView.template_name = "";

        }
        else if(fadeInElement=="logout_check"){

            $("#zurueck_logout_check").click(function () {
                switchView("logout");
            });
            $("#checkout_logout").click(function () {


                if (clientView.job.t_notizen ) {

                    nachrichtText = "Notiz: " + clientView.job.t_notizen;

                    var nachricht = {
                        id: misc.getUniqueID(),
                        lieferantid: clientView.job.lieferanten_id,
                        read: 0,
                        datum: new Date(),
                        nachricht: nachrichtText
                    };

                    console.log("SENDE NACHRICHT:");
                    console.dir(nachricht);
                    serverController.antwortNachricht.create(nachricht);
                }




                terminController.endTermin(clientView.job);
                console.log("DONE - > GOODBYe!")


                loginController.logout();

                switchView("goodbye");
                goodByeTimeout = setTimeout(function () {


                    loginController.logout();


                    goodByeTimeout = null;
                    switchView("start_screen");

                }, 10000);
            });
            $("#progresssteps").attr("src","img/progress6.png");
            $("#progresssteps").show();
            clientView.showJobPreview(clientView.job,contactController.lieferant);

        }
        else if(fadeInElement== "wait_goodbye"){
            $("#progresssteps").attr("src","img/progress2.png");
            $("#progresssteps").show();
            $("#waitgoodbye_button").click(function () {
                if (goodByeTimeout != null) {
                    clearTimeout(goodByeTimeout);
                    goodByeTimeout = null;
                }

                switchView("start_screen");

            });


        }
        else if(fadeInElement== "goodbye"){
            $("#progresssteps").attr("src","img/progress7.png");
            $("#progresssteps").show();
            $("#goodbye_button").click(function () {
                if (goodByeTimeout != null) {
                    clearTimeout(goodByeTimeout);
                    goodByeTimeout = null;
                }
                loginController.logout();
                switchView("start_screen");
            });
            clientView.job = new Job(id);

        }

    },

    showJobPreview: function (job,lieferant) {
        //Lieferanten not loadedyet


        // $("#besucherscheinContent").html("TODO: format job<br> termineController.showJob()<br><br><br><br><br><br>" + JSON.stringify(job))
        // $("#besucherscheinMarkt").html(job.markt_id);
        // $("#besucherscheinFirma").html(lieferant.firma);

        $("#besucherscheinLieferant").html(clientView.getLieferantFullName(lieferant)+" ");



        //$("#besucherscheinThematik").html(window.atob(job.t_thematik+" "));
       // $("#besucherscheinZiel").html(window.atob(job.t_ziel)+" ");
        $("#besucherscheinGrund").html(window.atob(job.t_grund)+" ");
        $("#besucherscheinGespraechspartner").html(window.atob(job.gespraechspartner)+" ");

        console.dir(job)
        job.cb_auftrag_getaetigt ? $("#besucherscheinAuftraggetaetigt").html("X") : {};
        job.cb_aktionsabsprache ? $("#besucherscheinAktionsabsprache").html("X") : {};
        job.cb_bemusterung ? $("#besucherscheinBemusterung").html("X") : {};
        job.cb_info_gespraech ? $("#besucherscheinInfoGespraech").html("X") : {};
        job.cb_mhd ? $("#besucherscheinMHD").html("X") : {};
        job.cb_nr_abgabe ? $("#besucherscheinNrAbgabe").html("X") : {};
        job.cb_reklamation ? $("#besucherscheinRelamationsbearbeitung").html("X") : {};
        job.cb_ruecknahme ? $("#besucherscheinRuecknahme").html("X") : {};
        job.cb_sortimentsinfo ? $("#besucherscheinSortimentsinfo").html("X") : {};
        job.cb_umbau ? $("#besucherscheinUmbau").html("X") : {};
        job.cb_verkostung ? $("#besucherscheinVerkostung").html("X") : {};
        job.cb_verlosung ? $("#besucherscheinVerlosung").html("X") : {};
        job.cb_warenaufbau ? $("#besucherscheinWarenaufbau").html("X") : {};

        $("#besucherscheinVKBetrag").html(job.t_vk_euro_abgabe + " €");

        $("#besucherscheinWarengruppe").html(window.atob(job.t_warengruppe)+" ");

        var notizenText = window.atob(job.t_notizen)
        var notizenTextS = notizenText.substr(0,200);
        if(notizenText!=notizenTextS){
            notizenTextS = notizenTextS+"..."
        }

        $("#besucherscheinNotizen").html(notizenTextS);

       // $("#besucherscheinThematik").click(function () {switchView("logout");})
        //$("#besucherscheinZiel").click(function () {switchView("logout");})
        $("#besucherscheinGrund").click(function () {switchView("logout");})
        $("#besucherscheinGespraechspartner").click(function () {switchView("logout");})

        $("#besucherscheinAuftraggetaetigt").click(function () {switchView("lieferantenschein_2");})
        $("#besucherscheinAktionsabsprache").click(function () {switchView("lieferantenschein_2");})
        $("#besucherscheinBemusterung").click(function () {switchView("lieferantenschein_2");})
        $("#besucherscheinInfoGespraech").click(function () {switchView("lieferantenschein_2");})
        $("#besucherscheinMHD").click(function () {switchView("lieferantenschein_2");})
        $("#besucherscheinNrAbgabe").click(function () {switchView("lieferantenschein_2");})
        $("#besucherscheinRelamationsbearbeitung").click(function () {switchView("lieferantenschein_2");})
        $("#besucherscheinRuecknahme").click(function () {switchView("lieferantenschein_2");})
        $("#besucherscheinSortimentsinfo").click(function () {switchView("lieferantenschein_2");})
        $("#besucherscheinUmbau").click(function () {switchView("lieferantenschein_2");})

        $("#besucherscheinVerkostung").click(function () {switchView("lieferantenschein_2");})
        $("#besucherscheinVerlosung").click(function () {switchView("lieferantenschein_2");})
        $("#besucherscheinWarenaufbau").click(function () {switchView("lieferantenschein_2");})
        $("#besucherscheinVKBetrag").click(function () {switchView("lieferantenschein_2");})
        $("#besucherscheinWarengruppe").click(function () {switchView("lieferantenschein_2");})

        $("#besucherscheinNotizen").click(function () {switchView("logout");})


        $("#besucherscheinContent").show();





    }
//überträgt die Daten aus dem GUI in ein Besucherschein-Objekt (job)
    ,
    setJob: function (job) {
        if (clientView.loadedTitle == "job_selector"){
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
        }
        else if (clientView.loadedTitle == "lieferantenschein_1"){


        }
        else if (clientView.loadedTitle == "lieferantenschein_2") {

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

            if ($('#cb_nr_abgabe').is(":checked")) {
                job.cb_nr_abgabe = true;


            }
            else {
                job.cb_nr_abgabe = false;
            }

            job.t_vk_euro_abgabe = $("#t_vk_euro_abgabe").val();
            job.t_warengruppe = window.btoa($("#t_warengruppe").val());

        }
        else if (clientView.loadedTitle == "logout") {
            job.t_ziel = window.btoa($("#t_ziel").val());
            job.t_grund = window.btoa($("#t_grund").val());
            job.t_thematik = window.btoa($("#t_thematik").val());
            job.gespraechspartner = window.btoa($("#gespraechspartner").val());


            if ($("#t_notizen").val() != "") {
                job.t_notizen = window.btoa($("#t_notizen").val()); //TODO
            }
            else {
                job.t_notizen = "";
            }
        }

        return job;
    }

    ,

    check_input: function (job) {

        console.log("Lieferant")
        console.dir(this.lieferant)
        if (this.lieferant) {
            job.lieferanten_id = this.lieferant.id;
        }

        job.markt_id = logistikapp.markt_id;

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
    setGUI: function (job) {

        if (clientView.viewTitle == "job_selector"){
            $('#fixtermin').prop('checked', job.fixtermin);


            $('#cb_besuch').prop('checked', job.besuch);

            $('#cb_bestellung').prop('checked', job.bestellung);


            $('#cb_verraeumung').prop('checked', job.verraeumung);

            $('#cb_austausch').prop('checked', job.austausch);
        }
        else if (clientView.viewTitle == "lieferantenschein_1"){

        }
        else if (clientView.viewTitle == "lieferantenschein_2") {
            $('#cb_auftrag_getaetigt').prop('checked', job.cb_auftrag_getaetigt);

            $('#cb_mhd').prop('checked', job.cb_mhd);


            $('#cb_ruecknahme').prop('checked', job.cb_ruecknahme);


            $('#cb_warenaufbau').prop('checked', job.cb_warenaufbau);


            $('#cb_reklamation').prop('checked', job.cb_reklamation);


            $('#cb_umbau').prop('checked', job.cb_umbau);


            $('#cb_info_gespraech').prop('checked', job.cb_info_gespraech);





            $('#cb_verkostung').prop('checked', job.cb_verkostung);


            $('#cb_sortimentsinfo').prop('checked', job.cb_sortimentsinfo);


            $('#cb_aktionsabsprache').prop('checked', job.cb_aktionsabsprache);


            $('#cb_bemusterung').prop('checked', job.cb_bemusterung);


            $('#cb_verlosung').prop('checked', job.cb_verlosung);

            $('#cb_nr_abgabe').prop('checked', job.cb_nr_abgabe);

            $("#t_vk_euro_abgabe").val(job.t_vk_euro_abgabe);
            $("#t_warengruppe").val(window.atob(job.t_warengruppe));
        }
        else if (clientView.viewTitle == "logout") {
            if(job.t_notizen!="") {
                $("#t_notizen").val(window.atob(job.t_notizen));
            }
            else
            {
                $("#t_notizen").val("");
            }
            $("#t_ziel").val(window.atob(job.t_ziel));

            $("#t_grund").val(window.atob(job.t_grund));

            $("#t_thematik").val(window.atob(job.t_thematik));



            $("#gespraechspartner").val(window.atob(job.gespraechspartner));

        }
        $('[type=checkbox]').checkboxradio("refresh");

    },



    //GUI zurücksetzen
    clearJob: function () {
        var id = misc.getUniqueID();
        var clearJob = new Job(id);

        clientView.setJob(clearJob);
    }
}

