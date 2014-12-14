/**
 * uebersichtController
 *
 *
 *
 *
 * @date 19.11.14 - 22:19
 *
 */

uebersichtController = {
    statistics: null,
    maerkte:null,
    init: function () {



        serverController.markt.getAll(function(maerkte){
            uebersichtController.maerkte = maerkte;

            //TODO move to Controllers
            termineTab.termineMarktSelectionWidget.setData(uebersichtController.maerkte,null,true,true);
            nachrichtenTab.nachrichtenMarktSelectionWidget.setData(uebersichtController.maerkte,null,true,true);

            konfigurationsController.setMaerkte(uebersichtController.maerkte);
        })


        serverController.statistics.get(function (statistics) {
            if (statistics) {
                uebersichtController.statistics = statistics;

            }

            var pieData = [
                {
                    value: statistics.besuche,
                    label: "Besuche"
                } ,
                {
                    value: statistics.bestellungen,
                    label: "Bestellungen"
                },
                {
                    value: statistics.verraeumungen,
                    label: "Verräumungen"
                },
                {
                    value: statistics.austausche,
                    label: "Austausche"
                }
            ];


            uebersichtTab.pieAufteilung.setData(pieData);


        });


    },
    ready: function () {


    }

}