/**
 * Created by StandardB on 19.06.2015.
 */

var termineListView = {



    //TODO: remove
    //templateList: null,
    dummy_termine: [],

    //TODO: remove
    addDummies: function()
    {
        termin1 = new Termin();
        termin1.title="1";
        termin2 = new Termin();
        termin2.title="2";
        termin3= new Termin();
        termin3.title="3";

        this.dummy_termine.push(termin1);
        this.dummy_termine.push(termin2);
        this.dummy_termine.push(termin3);
        for (var t in this.dummy_termine)
        {
            var listItem = "<li>" + termin1.title + "</li>";
            $("#termine_liste").append(listItem);

            var string = t.title;
            var listItem = "<li>" + string + "</li>";
            $("#termine_liste").append(listItem);
            $("#termine_liste").listview("refresh");
        }


    },

    compare: function () {
        return;
    },


    addTermine: function(terminliste) {


        console.log("addtermine");
        console.dir(terminliste);

        if(!terminliste) {
            terminliste = []

        }
        var l_termine =[];
        //termine des lieferanten rausfiltern
        for (var i=0; i < terminliste.length; i++)
        {

            if (terminliste[i].lieferant = contactController.lieferant.id)
            {
                l_termine.push(terminliste[i]);
                console.log(terminliste[i]);
            }
        }


        //Termine nach Datum sortieren
        terminliste_sorted = l_termine.sort(function (a,b) {

            datea = new Date(a.start);
            dateb = new Date(b.start);

            if (Date.parse(datea) < Date.parse(dateb))
                return -1;
            if (Date.parse(datea) > Date.parse(dateb))
                return 1;
            return 0;
        });


        console.log("addtermine_sorted");
        console.dir(terminliste_sorted);

        var jetzt_d = new Date();
        var akt = jetzt_d.getTime();


        for (var i=0; i < terminliste_sorted.length; i++)
        {




            t = terminliste_sorted[i];

            console.log("");
            console.log("akt vergleichtermin ");
            console.log(t.title);
            console.log(Date.parse(t.start));
            console.log(akt);


            if (Date.parse(t.start) > akt) {
                console.log("add");
                var datum = $.datepicker.formatDate('dd.mm.yy', t.start);
                var time = t.start.getHours() + ":" + t.start.getMinutes();

                var terminElement = '<li';

                if (t.repeatDays != 0) {
                    style = "color : silver"
                }

                terminElement += '><h2>' + t.title + '</h2>' + '<p><strong>' + datum;

                if (t.allDay == 0) {
                    terminElement += " " + time;
                }
                else {
                    terminElement += " [ganztägig]";
                }

                terminElement += '</strong></p>';

                if (t.notizen != "") {
                    terminElement += '<p>Notizen: ' + t.notizen + '</p>';
                }
                terminElement += '</li>';

                /*
                 var clickLieferant = function (lieferantElement, lieferant) {
                 lieferantElement.click(function () {
                 lieferantenController.waehleLieferant(lieferant)

                 })
                 */

                $("#termine_liste").append(terminElement);

                //Wieviele Termine anzeigen
                if (i > 10) {
                    break;
                }
            }

        }
        //clickLieferant(lieferantElement, lieferant);


        $("#termine_liste").listview("refresh");

    },

    updateTermineListe: function()
    {

    },

    initialize: function()
    {

        console.log("init_termine");
        console.dir(terminController.termine);
        termineListView.addTermine(terminController.termine);

        $("#cb_neuer_termin").click(function () {

            switchView("termin_eintragen");

        });

        $("#zurueck_kontakt").click(function () {
            switchView("contact_daten_menu");

        });

    }

}