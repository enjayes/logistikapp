/**
 * lieferantenController.
 *
 * >>Description<<
 *
 * @author Manfred
 * @date 19.11.14 - 22:46
 * @copyright munichDev UG
 */



lieferantenController = {
    tabPosition:2,
    lieferanten: [
        {
            name: "Stephen Weber"
        },
        {
            name: "Avery Walker"
        }
    ],

    init: function () {

        this.renderLieferanten();


        $("#page").click(function(){

               if(tabsController.aktuellerTab==lieferantenController.tabPosition){

                   $("#suchelieferantenwidget #filterBasic-input").val("");

                   if (lieferantenController.zeigeAlleLieferanten)
                       lieferantenController.zeigeLieferanten();
                   else
                    $("#suchelieferantenliste").filterable("refresh");
               }
        })

    },
    renderLieferanten: function () {


        $("#suchelieferantenliste").html("");


        for (var i = 0; i < this.lieferanten.length; i++) {
            var lieferant = this.lieferanten[i];

            $("#suchelieferantenliste").append(
                '<li onclick= "lieferantenController.waehleLieferant(' + i + ')"><a>' +
                    '<h2>' + lieferant.name + '</h2>' +
                    '<p><strong>You\'ve been invited to a meeting at Filament Group in Boston, MA</strong></p>' +
                    '<p>Hey Stephen, if you\'re available at 10am tomorrow, we\'ve got a meeting with the jQuery team.</p>' +
                    '<p class="ui-li-aside"><strong>6:24</strong>PM</p>' +
                    '</a></li>'
            )

        }

    },
    neuerLieferant: new Lieferant("Unbenannt"),
    aktuellerLieferant: null,
    zeigeAlleLieferanten: false,

    zeigeLieferanten: function () {
        lieferantenController.zeigeAlleLieferanten = !lieferantenController.zeigeAlleLieferanten;
        if (lieferantenController.zeigeAlleLieferanten) {
            $("#allelieferantenanzeigen").val('Verstecken').button('refresh');
        } else {
            $("#allelieferantenanzeigen").val('Alle anzeigen').button('refresh');
        }
        $("#suchelieferantenliste").filterable("refresh");
    },


    zeigeAktuellenLieferanten: function () {
        if (this.aktuellerLieferant) {

            $("#lieferantName").text(this.aktuellerLieferant.name);


            $("#lieferantterminanlegen").show();

        }
    },

    waehleLieferant: function (index) {
        this.aktuellerLieferant = this.lieferanten[index];
        this.zeigeAktuellenLieferanten();


    },
    lieferantenHinzufuegen: function () {

        if (lieferantenController.zeigeAlleLieferanten)
            lieferantenController.zeigeLieferanten();


        var lieferant = this.neuerLieferant;
        this.aktuellerLieferant = lieferant;

        this.lieferanten.push(lieferant);

        this.zeigeAktuellenLieferanten();
        this.renderLieferanten();
    }


}