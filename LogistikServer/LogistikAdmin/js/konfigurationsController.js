/**
 * konfigurationsController
 *
 *
 * @author
 * @date 13.12.14 - 21:11
 * @copyright
 */



konfigurationsController = {
    aktuellerMarkt:null,
    aktuellerMarktGespeichert:true,
    init: function () {

        $("#marktInformationen input").on("input", function () {

            konfigurationsController.aktuellerMarktGespeichert = false;
            $("#speichereMarkt").css("opacity", 1).removeClass("ui-disabled");
            $("#rueckgaengigMarkt").css("opacity", 1).removeClass("ui-disabled");
        })


    },
    ready:function(){



    },
    setMaerkte:function(maerkte){

        konfigurationsTab.konfigurationsMarktSelectionWidget.setData(uebersichtController.maerkte,konfigurationsController.zeigeMarkt,true);

    },
    zeigeMarkt:function(markt){

        if(markt&&markt.id!=undefined){

            konfigurationsController.aktuellerMarkt = markt;

            $("#markttelefon").val(markt.telefon);

            this.aktuellerMarktGespeichert=true;
            $("#speichereMarkt").css("opacity", 0).addClass("ui-disabled");
            $("#rueckgaengigMarkt").css("opacity", 0).addClass("ui-disabled");
        }

    },
    speichereAktuellenMarkt: function () {

        if (this.aktuellerMarkt) {

            var validated = true; // TODO ggf validation
            if (validated) {

                this.aktuellerMarkt.telefon = $("#markttelefon").val();

                this.aktuellerMarktGespeichert = true;

                $("#speichereMarkt").css("opacity", 0).addClass("ui-disabled");
                $("#rueckgaengigMarkt").css("opacity", 0).addClass("ui-disabled");


                //Update Server DB
                serverController.markt.update(this.aktuellerMarkt);

            }
        }
    }, abbrechenBearbeitungAktuellerMarkt: function () {

        konfigurationsController.zeigeMarkt(konfigurationsController.aktuellerMarkt);


    }

}