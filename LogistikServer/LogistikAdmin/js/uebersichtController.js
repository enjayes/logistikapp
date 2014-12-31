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
    auftragsHistorieDataTable:null,
    init: function () {

        this.auftragsHistorieDataTable = $('#auftragsHistorie').DataTable({
            "data": [],
            "searching": false,
            "columns": [
                { "title": "Id" },
                { "title": "Markt" },
                { "title": "Lieferant" },
                { "title": "Datum" }

            ],
            "columnDefs": [
                {
                    "targets": [ 0 ],
                    "visible": false,
                    "searchable": false
                }
            ],
            "language": {
                "url": "js/German.json"
            },
            "createdRow": function (row, data, dataIndex) {
                $(row).css("cursor", "pointer").click(function () {
                    if (data[0]) {
                        var url = location.protocol + "//" + location.host + "#job=" + data[0];
                        var win = window.open(url, '_blank');
                        win.focus();

                    }
                })
            }
        });

        serverController.markt.getAll(function (maerkte) {

            uebersichtController.maerkte = maerkte;
            uebersichtController.defaultMarktId = uebersichtController.maerkte[0].id;

            //TODO move to Controllers
            termineTab.termineMarktSelectionWidget.setData(uebersichtController.maerkte, null, true, false);
            termineTab.terminMarktSelectionWidget.setData(uebersichtController.maerkte, null, false, true);
            nachrichtenTab.nachrichtenMarktSelectionWidget.setData(uebersichtController.maerkte, null, true, true);
            uebersichtTab.auftragsHistorieMarktSelectionWidget.setData(uebersichtController.maerkte, null, true, true);

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


    },
    updateAuftragsHistorie: function () {
        serverController.job.getAll(function (jobs) {

            console.dir(jobs)

            var maerkteSelected = uebersichtTab.auftragsHistorieMarktSelectionWidget.getSelectedItems();
            var lieferantenSelected = uebersichtTab.searchWidget.getSelectedItems();

            uebersichtController.auftragsHistorieDataTable .clear();

            for (var i = 0; i < jobs.length; i++) {
                var job = jobs[i];

                var lieferant = lieferantenController.getLieferantByID(job.lieferanten_id);

                if (lieferant) {
                    var selected = 0;
                    for (var j = 0; j < maerkteSelected.length; j++) {

                        if (job.markt_id == maerkteSelected[j].name){
                            selected++;
                           break;
                        }
                    }
                    for (j = 0; j < lieferantenSelected.length; j++) {

                        if (lieferant.id == lieferantenSelected[j].id){
                            selected++;
                            break;
                        }
                    }
                    if (selected == 2)
                        uebersichtController.auftragsHistorieDataTable.row.add([job.id, lieferantenController.getLieferantFullName(lieferant), job.markt_id, termineTab.calenderFactory.moment(job.timestamp_start).format('HH:mm DD.MM.YYYY')]);


                }



            }

            console.dir( uebersichtController.auftragsHistorieDataTable.data() )

            uebersichtController.auftragsHistorieDataTable.rows().invalidate().draw();




        });

    }

}