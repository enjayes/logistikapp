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
    maerkte: null,
    defaultMarktId: null,
    init: function () {
        console.log("'''''''''''öööööäääääüüüü")
        this.searchWidget = new SearchWidget("#searchAuftragsHistorieLieferanten", "Suche nach Lieferanten...", 5, true, function () {
                termineTab.searchWidget.getInput().val("");
                // termineTab.renderSelectedLieferanten();
            },
            function (lieferant, classes) {
                return "<li class='" + classes + "'><a>" + lieferantenController.getLieferantFullName(lieferant) + "</a></li>";
            },
            function (visible, top, height) {
                $("#pagecontent").css("min-height", visible ? top + height : "");
            }
        );

        serverController.job.getAll(function (jobs) {
            console.log("----------------dcdc");
            console.dir(jobs);

            var dataSet = [
                ["Sindelfingen", "Max Huber", "12.12.2014"]
            ];

            $('#auftragsHistorie').DataTable({
                "data": dataSet,
                "searching": false,
                "columns": [
                    { "title": "Markt" },
                    { "title": "Lieferant" },
                    { "title": "Datum" }

                ]
            });



        });

        serverController.markt.getAll(function (maerkte) {
            uebersichtController.maerkte = maerkte;

            uebersichtController.defaultMarktId = uebersichtController.maerkte[0].id;

            console.log(".................................................ydsfsdf")

            //TODO move to Controllers
            termineTab.termineMarktSelectionWidget.setData(uebersichtController.maerkte, null, true, false);
            termineTab.terminMarktSelectionWidget.setData(uebersichtController.maerkte, null, false, true);
            nachrichtenTab.nachrichtenMarktSelectionWidget.setData(uebersichtController.maerkte, null, true, true);

            konfigurationsController.setMaerkte(uebersichtController.maerkte);

        })


        serverController.statistics.get(function (statistics) {
            if (statistics) {
                uebersichtController.statistics = statistics;


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


            }

        });


    },
    ready: function () {


    }

}