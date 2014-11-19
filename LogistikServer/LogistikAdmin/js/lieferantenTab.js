/**
 * lieferantenTab.
 *
 * >>Description<<
 *
 * @author Manfred
 * @date 19.11.14 - 22:13
 * @copyright munichDev UG
 */


lieferantenTab = {

    init: function () {

        // Init Filterable
        $.mobile.document.one("filterablecreate", "#suchelieferantenliste", function () {

            $("#suchelieferantenliste").filterable("option", "filterCallback", function (index, searchValue) {

                //Alle einzeigen
                if (lieferantenTab.zeigeAlleLieferanten)
                    return false;

                //Kein Term eingebenen
                if (searchValue.trim() == "")
                    return true;

                //Gefunden
                return !($(this).text().toLowerCase().search(searchValue.toLowerCase()) != -1);

            });

        });
    },

    ready: function () {

        //Bei Eingabe alle anzeigen deaktivieren
        $("#suchelieferantenwidget #filterBasic-input").on("input change", function () {
            if (lieferantenTab.zeigeAlleLieferanten)
                lieferantenTab.zeigeLieferanten();
        }).attr("placeholder","Suche nach Lieferanten...");

        //Filterable initialisieren
        $("#suchelieferantenliste").filterable({
            filter: function (event, ui) {
                if ($("#suchelieferantenliste li.ui-screen-hidden").length == $("#suchelieferantenliste li").length)
                    $("#suchelieferantenliste").hide();
                else
                    $("#suchelieferantenliste").show();

            }
        });
        $("#suchelieferantenliste").hide();

    },
    open: function () {



    },

    zeigeAlleLieferanten:false,
    zeigeLieferanten: function () {
        lieferantenTab.zeigeAlleLieferanten = !lieferantenTab.zeigeAlleLieferanten;
        if (lieferantenTab.zeigeAlleLieferanten) {
            $("#allelieferantenanzeigen").val('Verstecken').button('refresh');
        } else {
            $("#allelieferantenanzeigen").val('Alle anzeigen').button('refresh');
        }
        $("#suchelieferantenliste").filterable("refresh");
    }



}