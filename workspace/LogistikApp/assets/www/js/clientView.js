/**
 * Created by StandardB on 29.11.2014.
 */


var clientView = {


    initialize: function() {
        var id = 1; //TODO: generate id
        var job = new Job(id);

        $("#weiter_Aufgaben").click(function()
        {
            //check input
            if ($('#cb_besuch').is(":checked"))
            {
                job.besuch = true;
            }
            if ($('#cb_cb_bestellung').is(":checked"))
            {
                job.bestellung = true;
            }
            if ($('#cb_verraeumung').is(":checked"))
            {
                job.verraeumung = true;
            }
            if ($('#cb_cb_austausch').is(":checked"))
            {
                job.austausch = true;
            }

            $("#lieferantenschein1").show();
            $("#jobSelector").hide();

        });

        $("#weiter_lieferantenschein1").click(function()
        {
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


    }




}
