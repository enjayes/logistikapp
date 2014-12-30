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

        serverController.job.getAll(function (jobs) {


            var dataSet = [
                ["27ab9eca-40fd-43b6-94dd-133a36245635","Sindelfingen", "Max Huber", "12.12.2014"]
            ];

            $('#auftragsHistorie').DataTable({
                "data": dataSet,
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
                "createdRow": function( row, data, dataIndex ) {
                    $(row).css("cursor","pointer").click(function(){
                        if(data[0]){
                            var url = location.protocol + "//" + location.host + "#job="+data[0];
                            var win = window.open(url, '_blank');
                            win.focus();

                        }
                     })
                }
            });



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


    }

}