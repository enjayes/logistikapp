/**
 * jobsController.
 *
 * >>Description<<
 *
 * @author Manfred
 * @date 09.12.14 - 17:52
 * @copyright munichDev UG
 */






lieferantenController = {
    titleUnbenannt: "Unbenannt",
    lieferanten: [],

    init: function () {


        $("#lieferantenInformationen input").on("input", function () {
            lieferantenController.aktuellerLieferantGespeichert = false;
            $("#speichereLieferant").css("opacity", 1).removeClass("ui-disabled");
            $("#rueckgaengigLieferant").css("opacity", 1).removeClass("ui-disabled");
        })

    },
    ready: function () {

        //Get Lieferanten From Server
        var getLieferatenFromServer = function (lieferanten) {

            if (lieferanten) {
                lieferantenController.lieferanten = lieferanten;

                if (lieferantenController.aktuellerLieferant) {
                    var lieferant = lieferantenController.getLieferantByID(lieferantenController.aktuellerLieferant.id);
                    if (!lieferant)
                        lieferantenController.keinenLieferantenZeigen();
                    else if (lieferantenController.aktuellerLieferantGespeichert) {
                        lieferantenController.aktuellerLieferant = $.extend(true, {}, lieferant);

                        lieferantenController.zeigeAktuellenLieferanten(true);

                    }
                }
                if (termineController.aktuellerTerminLieferant) {
                    if (tabsController.tab() == termineTab)
                        var lieferantenInput = termineTab.searchWidget.getInput();
                    else
                        lieferantenInput = $("#lieferantenTerminReadyOnly");

                    var lieferant = lieferantenController.getLieferantByID(termineController.aktuellerTerminLieferant.id);

                    if (!lieferant) {

                        lieferantenInput.val("").trigger("input");
                        $("#lieferantAnzeigen button").addClass("ui-disabled")

                        if (tabsController.tab() == lieferantenTab) {
                            $("#popupTermin").popup("close");
                        } else if (tabsController.tab() == termineTab)
                            termineController.aktuellerTerminLieferant = null;

                    }
                    else {

                        termineController.aktuellerTerminLieferant = $.extend(true, {}, lieferant);
                        lieferantenInput.val(lieferantenController.getLieferantFullName(lieferant));

                        if (tabsController.tab() == termineTab)
                            termineController.aktuellerTerminLieferant = lieferant;

                        $("#lieferantAnzeigen button").removeClass("ui-disabled")


                    }
                }


                //Update Lieferanten Widgets
                nachrichtenTab.searchWidget.setList(lieferantenController.lieferanten);
                lieferantenTab.searchWidget.setList(lieferantenController.lieferanten);



                termineTab.searchWidget.setList(lieferantenController.lieferanten);
                nachrichtenTab.renderSelectedLieferanten();

                nachrichtenController.renderEmpfangeneNachrichten();
                nachrichtenController.renderGesendeteNachrichten();
                nachrichtenTab.renderSelectedLieferanten();
            }
        }
        serverController.job.getAll(getLieferatenFromServer);
    }
}