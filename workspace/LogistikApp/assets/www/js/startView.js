/**
 * startView
 *
 *
 * @author
 * @date 11.12.14 - 17:03
 * @copyright
 */



var startView = {

    initialize: function() {


        $("#start_anmelden").click(function()
        {

            $('#startScreen').hide();
            $('#lieferantenLogin').show();


        });

        $("#start_konfiguration").click(function()
        {
            $(".fadein").removeClass("fadein").addClass("fadein");

            $('#startScreen').hide();
            $('#konfi_menue').show();
        });

    }

}