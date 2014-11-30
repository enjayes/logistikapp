/**
 * Created by StandardB on 29.11.2014.
 */


var clientView = {


    initialize: function() {



        $("#weiterAufgaben").click(function()
        {
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


    }




}

