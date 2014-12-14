/**
 * Created by StandardB on 29.11.2014.
 */


var clientView = {
    lieferant:null,
    getLieferantFullName: function () {
        var name = this.lieferant.name;
        if (this.lieferant.vorname && this.lieferant.vorname.trim() != "")
            name =  this.lieferant.vorname.trim()+" "+name;
        return name
    },
    check_input: function(job) {
        //check input Aufgabenwahl

        if ($('#fixtermin').is(":checked"))
        {
            job.fixtermin = true;
        }
        else
        {
            job.fixtermin = false;
        }



        if ($('#cb_besuch').is(":checked"))
        {
            job.besuch = true;
        }
        else
        {
            job.besuch = false;
        }
        if ($('#cb_bestellung').is(":checked"))
        {
            job.bestellung = true;
        }
        else
        {
            job.bestellung = false;
        }
        if ($('#cb_verraeumung').is(":checked"))
        {
            job.verraeumung = true;
        }
        else
        {
            job.verraeumung = false;
        }
        if ($('#cb_austausch').is(":checked"))
        {
            job.austausch = true;
        }
        else
        {
            job.austausch = false;
        }

        //input lieferantenschein1

        job.t_ziel = $("#t_ziel").val();
        job.t_grund = $("#t_grund").val();
        job.t_thematik = $("#t_thematik").val();
        job.gespraechspartner = $("#gespraechspartner").val();

        //input lieferantenschein2


        if ($('#cb_auftrag_getaetigt').is(":checked")) {
            job.cb_auftrag_getaetigt = true;//Auftrag getätigt
        }
        else
        {
            job.cb_auftrag_getaetigt = false;
        }
        if ($('#cb_mhd').is(":checked")) {
            job.cb_mhd = true;//MHD-Kontrolle
        }
        else{
            job.cb_mhd = false;
        }
        if ($('#cb_ruecknahme').is(":checked")) {
            job.cb_ruecknahme = true; //Rücknahme
        }
        else
        {
            job.cb_ruecknahme = false; //Rücknahme
        }

        if ($('#cb_warenaufbau').is(":checked")) {
            job.cb_warenaufbau=true;
        }
        else{
            job.cb_warenaufbau=false;
        }

        if ($('#cb_reklamation').is(":checked")) {
            job.cb_reklamation=true;
        }
        else{
            job.cb_reklamation=false; //Reklamationsbearbeitung
        }

        if ($('#cb_umbau').is(":checked")) {
            job.cb_umbau=true;
        }
        else{
            job.cb_umbau=false;
        }

        if ($('#cb_info_gespraech').is(":checked")) {
            job.cb_info_gespraech=true;
        }
        else{
            job.cb_info_gespraech=false;
        }

        if ($('#cb_nr_abgabe').is(":checked")) {
            job.cb_nr_abgabe=true;
            $("#t_vk_euro_abgabe").val(job.t_vk_euro_abgabe);
            $("#t_warengruppe").val(job.t_warengruppe);

        }
        else{
            job.cb_nr_abgabe=false;
        }

        job.t_vk_euro_abgabe = $("#t_vk_euro_abgabe").val();

        if ($('#cb_verkostung').is(":checked")) {
            job.cb_verkostung=true;
        }
        else{
            job.cb_verkostung=false;
        }

        if ($('#cb_sortimentsinfo').is(":checked")) {
            job.cb_sortimentsinfo=true;
        }
        else{
            job.cb_sortimentsinfo=false;
        }

        if ($('#cb_aktionsabsprache').is(":checked")) {
            job.cb_aktionsabsprache=true;
        }
        else{
            job.cb_aktionsabsprache=false;
        }

        if ($('#cb_bemusterung').is(":checked")) {
            job.cb_bemusterung=true;
        }
        else{
            job.cb_bemusterung=false;
        }

        if ($('#cb_verlosung').is(":checked")) {
            job.cb_verlosung=true;
        }
        else{
            job.cb_verlosung=false;
        }


        //logout
        if($("#t_notizen").val()!="")
        {
            //TODO
        }
        else
        {
        }

        //console.dir(job);
        return job;

    },

    check_out: function(job) {
        job.timestamp_end = new Date();
        job.finished = true;
        job.pending=false;
        job.checked_out=true;
        console.dir(job);
        return job;
    },


    setJob: function(job){
        $('#cb_besuch').prop('checked', job.besuch);

        $('#cb_cb_bestellung').prop('checked',job.bestellung);


        $('#cb_verraeumung').prop('checked', job.verraeumung);

        $('#cb_cb_austausch').prop('checked', job.austausch);

        $("#t_ziel").val(job.t_ziel);
        $("#t_grund").val(job.t_grund);
        $("#t_thematik").val(job.t_thematik);


        $('#cb_auftrag_getaetigt').prop('checked',job.cb_auftrag_getaetigt);

        $('#cb_mhd').prop('checked',job.cb_mhd);



        $('#cb_ruecknahme').prop('checked',job.cb_ruecknahme);


        $('#cb_warenaufbau').prop('checked',job.cb_warenaufbau);


        $('#cb_reklamation').prop('checked',job.cb_reklamation);


        $('#cb_umbau').prop('checked',   job.cb_umbau);


        $('#cb_info_gespraech').prop('checked',job.cb_info_gespraech);


        $('#cb_nr_abgabe').prop('checked',job.cb_nr_abgabe);



        $('#cb_verkostung').prop('checked',job.cb_verkostung);


        $('#cb_sortimentsinfo').prop('checked', job.cb_sortimentsinfo);


        $('#cb_aktionsabsprache').prop('checked',job.cb_aktionsabsprache);


        $('#cb_bemusterung').prop('checked',job.cb_bemusterung);


        $('#cb_verlosung').prop('checked', job.cb_verlosung);

        $("#t_notizen").val(job.t_notizen);

        console.dir(job);
    },

    initialize: function() {
        var id = misc.getUniqueID();
        var job = new Job(id);
        var that = this;


        $( "#popupVorlagen" ).popup();

        $("#job_zurueck_kontakt").click(function()
        {
            $('#contact_daten_menu').show();
            $('#jobSelector').hide();


        });


        $("#zurueck_start").click(function()
        {
            $('#startScreen').show();
            $('#contact_daten_menu').hide();


        });

        $("#zurueck_kontakt").click(function()
        {
            $('#contact_daten_menu').show();
            $('#termine_menu').hide();


        });

        $("#b_kalender").click(function()
        {
            $('#termine_menu').show();
            $('#contact_daten_menu').hide();

        });

        $("#b_kalender").click(function()
        {
            $('#termine_menu').show();
            $('#contact_daten_menu').hide();

        });


        $("#weiter_jobSelector").click(function()
        {
            //notifications.show("Marktleiter","Ich trink nen Sekt vielleicht!");

            contactController.store();
            $("#jobSelector").show();
            $("#contact_daten_menu").hide();

        });

        $("#weiter_Aufgaben").click(function()
        {
            //notifications.show("Marktleiter","Ich trink nen Sekt vielleicht!");


            $("#lieferantenschein1").show();
            $("#jobSelector").hide();

        });

        $("#weiter_lieferantenschein1").click(function()
        {
            //notifications.show("Geschäftsführer","Bring mir mal ne Flasche Bier!");

            $("#lieferantenschein2").show();
            $("#lieferantenschein1").hide();

        });


        $("#zurueck_lieferantenschein1").click(function()
        {


            $("#jobSelector").show();
            $("#lieferantenschein1").hide();

        });

        $("#zurueck_lieferantenschein2").click(function()
        {
            $("#lieferantenschein1").show();
            $("#lieferantenschein2").hide();

        });


        $("#weiter_lieferantenschein2").click(function()
        {
            $("#logout").show();
            $("#lieferantenschein2").hide();

        });




        $("#checkout_logout").click(function()
        {
            job = that.check_input(job);
            job = that.check_out(job);

            if(contactController.lieferant) {
                job.lieferanten_id = contactController.lieferant.id;
            }
            else{
                alert("Kein Lieferant eingelogged!");
            }
            serverController.job.create(job);

        });

        $("#zurueck_logout").click(function()
        {
            $("#logout").hide();
            $("#lieferantenschein2").show();

        });




    }




}

