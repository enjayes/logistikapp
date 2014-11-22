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
                if (lieferantenController.zeigeAlleLieferanten)
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
            if (lieferantenController.zeigeAlleLieferanten)
                lieferantenController.zeigeLieferanten();
        }).click(function(event){
                 event.stopPropagation()
            }).attr("placeholder","Suche nach Lieferanten...");




        //Filterable initialisieren
        $("#suchelieferantenliste").filterable({
            filter: function (event, ui) {

                if (lieferantenController.lieferanten.length==0||!lieferantenController.zeigeAlleLieferanten&&$("#suchelieferantenliste li.ui-screen-hidden").length == $("#suchelieferantenliste li").length)
                {
                    $("#suchelieferantenliste").hide();

                }
                else if( lieferantenController.zeigeAlleLieferanten||  $("#suchelieferantenwidget #filterBasic-input").is(":focus"))
                    $("#suchelieferantenliste").show();

            }
        });
        $("#suchelieferantenliste").hide();

    },
    open: function () {



    },
    waehleLieferant: function (index) {

        lieferantenController.waehleLieferant(index);
    }

}