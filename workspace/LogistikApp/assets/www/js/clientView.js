/**
 * Created by StandardB on 29.11.2014.
 */


var clientView = {

    check_input: function(job) {
        //check input Aufg
        if ($('#cb_besuch').is(":checked"))
        {
            job.besuch = true;
        }
        else
        {
            job.besuch = false;
        }
        if ($('#cb_cb_bestellung').is(":checked"))
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
        if ($('#cb_cb_austausch').is(":checked"))
        {
            job.austausch = true;
        }
        else
        {
            job.austausch = false;
        }

        //
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
        }
        else{
            job.cb_nr_abgabe=false;
        }

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
        console.dir(job);
        return job;

    },


    initialize: function() {
        var id = 1; //TODO: generate id
        var job = new Job(id);
        var that = this;

        $("#weiter_Aufgaben").click(function()
        {
            //
            notifications.show("Marktleiter","Ich trink nen Sekt vielleicht!");

            //check input
            if ($('#cb_besuch').is(":checked"))
            {
                job.besuch = true;
            }
            else
            {
                job.besuch = false;
            }
            if ($('#cb_cb_bestellung').is(":checked"))
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
            if ($('#cb_cb_austausch').is(":checked"))
            {
                job.austausch = true;
            }
            else
            {
                job.ausstausch = false;
            }


            $("#lieferantenschein1").show();
            $("#jobSelector").hide();

        });

        $("#weiter_lieferantenschein1").click(function()
        {
            notifications.show("Geschäftsführer","Bring mir mal ne Flasche Bier!");

            $("#lieferantenschein2").show();
            $("#lieferantenschein1").hide();

        });


        $("#zurueck_lieferantenschein1").click(function()
        {



            //end get values
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
            job = that.check_input(job);


            serverController.job.create(job);

        });


    }




}

