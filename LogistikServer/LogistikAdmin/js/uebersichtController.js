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



        serverController.maerkte.getAll(function(maerkte){
            uebersichtController.maerkte = maerkte;
            termineTab.termineMarktSelectionWidget.setData(uebersichtController.maerkte,true);
            nachrichtenTab.nachrichtenMarktSelectionWidget.setData(uebersichtController.maerkte,true);


        })


        serverController.statistics.get(function (statistics) {
            if (statistics) {
                uebersichtController.statistics = statistics;

            }

            console.dir("------------------")
            console.dir(statistics)

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