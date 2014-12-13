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
    init: function () {



        serverController.maerkte.getAll(function(maerkte){
            console.dir(maerkte);

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