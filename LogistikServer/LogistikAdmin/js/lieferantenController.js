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
    tabPosition: 2,
    titleUnbenannt: "Unbenannt",
    lieferanten: [],

    init: function () {

        //Get Lieferanten From Server
        var getLieferatenFromServer = function (lieferanten) {
            if (lieferanten) {
                lieferantenController.lieferanten = lieferanten;
                lieferantenController.renderLieferanten();
            }
        }
        serverController.lieferant.getAll(getLieferatenFromServer);

        this.renderLieferanten();

        $("#lieferantenInformationen input").on("input", function () {
            lieferantenController.aktuellerLieferantGespeichert = false;
            $("#speichereLieferant").css("opacity", 1).removeClass("ui-disabled");
            $("#rueckgaengigLieferant").css("opacity", 1).removeClass("ui-disabled");
        })


        //Zeige alle Lieferanten bei anklicken des Filters
        $("#suchelieferantenwidget #filterBasic-input").on("focus",function () {
            $("#suchelieferantenliste").show();

            if (!lieferantenController.zeigeAlleLieferanten)
                lieferantenController.zeigeLieferanten();

        }).on("keydown", function (e) {

                if (e.which == 13) {

                    console.log("!!!!!!")

                    if (tabsController.tab() == lieferantenTab) {

                        console.log("!!!!!sdfdsfsdf!")

                        if (!lieferantenController.zeigeAlleLieferanten && ($("#suchelieferantenliste li").length - $("#suchelieferantenliste li.ui-screen-hidden").length == 1)) {
                            $("#suchelieferantenliste li").filter(":visible").click();
                        }
                    }

                }
            });


        var dismissFilter = function () {
            if (!lieferantenController.lieferantenFilterDontDismiss) {
                lieferantenController.lieferantenFilterDontDismiss = true;
                setTimeout(function () {
                    lieferantenController.lieferantenFilterDontDismiss = false;
                }, 400)

                if (lieferantenController.zeigeAlleLieferanten)
                    lieferantenController.zeigeLieferanten();
                else
                    $("#suchelieferantenliste").filterable("refresh");

                $("#suchelieferantenliste").hide();
            }
        }

        //Breche Auswahl von Lieferant ab
        $("#page").click(function () {
            dismissFilter();
        })

        $("#suchelieferantenwidget #filterBasic-input").on("blur", function () {
            setTimeout(function () {
                if (!lieferantenController.lieferantenFilterDontDismissByBlur)
                    dismissFilter();
            }, 300)
        })


    },
    renderLieferanten: function () {


        $("#suchelieferantenliste").html("");


        for (var i = 0; i < lieferantenController.lieferanten.length; i++) {
            var lieferant = lieferantenController.lieferanten[i];

            var selectedClass = lieferantenController.lieferanten[i] == lieferantenController.aktuellerLieferant ? "class='selected'" : "";

            var name = lieferantenController.getLieferantFullName(lieferant);

            $("#suchelieferantenliste").append(
                '<li onclick= "tabsController.tab().waehleLieferant(' + i + ')" ' + selectedClass + '><a>' +
                    '<h2>' + name + '</h2>' +
                    '<p><strong>You\'ve been invited to a meeting at Filament Group in Boston, MA</strong></p>' +
                    '<p>Hey Stephen, if you\'re available at 10am tomorrow, we\'ve got a meeting with the jQuery team.</p>' +
                    '<p class="ui-li-aside"><strong>6:24</strong>PM</p>' +
                    '</a></li>'
            )

        }

    },
    aktuellerLieferantGespeichert: true,

    aktuellerLieferant: null,
    zeigeAlleLieferanten: false,

    zeigeLieferanten: function () {

        lieferantenController.zeigeAlleLieferanten = !lieferantenController.zeigeAlleLieferanten;
        if (lieferantenController.zeigeAlleLieferanten) {
            lieferantenController.lieferantenFilterDontDismissByBlur = true;
            setTimeout(function () {
                lieferantenController.lieferantenFilterDontDismissByBlur = false;
            }, 400)
            $("#allelieferantenanzeigen").val('Verstecken').button('refresh');
        } else {
            $("#allelieferantenanzeigen").val('Alle anzeigen').button('refresh');
        }
        $("#suchelieferantenliste").filterable("refresh");
    },


    zeigeAktuellenLieferanten: function () {


        if (lieferantenController.aktuellerLieferant) {

            //Setze werte
            $("#lieferantVorname").val(lieferantenController.aktuellerLieferant.vorname);
            $("#lieferantName").val(lieferantenController.aktuellerLieferant.name);


            $("#lieferantenInformationen").show();
            $("#lieferantenInformationen .redborder").removeClass("redborder");

            lieferantenController.aktuellerLieferantGespeichert = true;
            $("#speichereLieferant").css("opacity", 0).addClass("ui-disabled");
            $("#rueckgaengigLieferant").css("opacity", 0).addClass("ui-disabled");


            lieferantenController.renderLieferanten();
        }


    }, speichereAktuellenLieferant: function () {

        if (this.aktuellerLieferant) {
            var validated = true;

            $("#lieferantenInformationen .redborder").removeClass("redborder");

            var name = $("#lieferantName").val();
            if (name.trim() == "") {
                validated = false;
                $("#lieferantName").parent(".ui-input-text").addClass("redborder");
            }


            if (validated) {


                this.aktuellerLieferant.vorname = $("#lieferantVorname").val();
                this.aktuellerLieferant.name = name;

                this.aktuellerLieferantGespeichert = true;
                $("#speichereLieferant").css("opacity", 0).addClass("ui-disabled");
                $("#rueckgaengigLieferant").css("opacity", 0).addClass("ui-disabled");

                lieferantenController.lieferanten.sort(lieferantenController.lieferantenCompare);

                this.renderLieferanten();

                //Update Server DB
                serverController.lieferant.update(lieferantenController.aktuellerLieferant);

            }
        }
    },

    abbrechenBearbeitungAktuellerLieferant: function () {


        this.zeigeAktuellenLieferanten();


    },


    loescheAktuellerLieferant: function () {

        $("#deletePopup").popup("open", {
            transition: "pop"
        });


    },
    deletePopupYes: function () {

        lieferantenController.lieferanten = lieferantenController.lieferanten.filter(function (el) {
            return el.id !== lieferantenController.aktuellerLieferant.id;
        });


        //Update Server DB
        serverController.lieferant.delete(lieferantenController.aktuellerLieferant);


        lieferantenController.aktuellerLieferant = null;


        $("#lieferantenInformationen").addClass("geloescht");
        setTimeout(function () {
            $("#lieferantenInformationen").removeClass("geloescht");
            if (!lieferantenController.aktuellerLieferant)
                $("#lieferantenInformationen").hide();
        }, 1000)


        lieferantenController.renderLieferanten();

    },
    deletePopupNo: function () {

    },
    waehleLieferant: function (index) {


        var waehleLieferant = function () {
            lieferantenController.aktuellerLieferant = lieferantenController.lieferanten[index];
            lieferantenController.zeigeAktuellenLieferanten();
            $("#lieferantenInformationen").addClass("geladen");
            setTimeout(function () {
                $("#lieferantenInformationen").removeClass("geladen");
            }, 1000)
        }

        this.checkSaved(waehleLieferant);


    },

    lieferantenCompare: function (a, b) {
        if (a.name < b.name)
            return -1;
        if (a.name > b.name)
            return 1
        if (a.vorname < b.vorname)
            return -1;
        if (a.vorname > b.vorname)
            return 1
        return 0;
    },
    lieferantenHinzufuegen: function () {

        var lieferantenHinzufuegen = function () {
            if (lieferantenController.zeigeAlleLieferanten)
                lieferantenController.zeigeLieferanten();

            if (lieferantenController.aktuellerLieferant) {
                $("#lieferantenInformationen").addClass("neumitalt");
                setTimeout(function () {
                    $("#lieferantenInformationen").removeClass("neumitalt");
                }, 1300)

            } else {
                $("#lieferantenInformationen").addClass("neu");
                setTimeout(function () {
                    $("#lieferantenInformationen").removeClass("neu");
                }, 1300)
            }


            lieferantenController.aktuellerLieferant = new lieferant(misc.titleUnbenannt, misc.titleUnbenannt);

            lieferantenController.lieferanten.push(lieferantenController.aktuellerLieferant);


            lieferantenController.lieferanten.sort(lieferantenController.lieferantenCompare);


            //Update Server DB
            serverController.lieferant.create(lieferantenController.aktuellerLieferant);


            $("#suchelieferantenliste").hide();

            lieferantenController.zeigeAktuellenLieferanten();
            $("#lieferantVorname").select();


        }

        this.checkSaved(lieferantenHinzufuegen);


    }, getLieferantByID: function (lieferantenID) {

        for (var i = 0; i < lieferantenController.lieferanten.length; i++) {
            if (lieferantenController.lieferanten[i].id == lieferantenID) {
                return lieferantenController.lieferanten[i];
            }

        }
        return null;

    }, getLieferantByName: function (lieferantenName) {
        for (var i = 0; i < lieferantenController.lieferanten.length; i++) {
            if (this.getLieferantFullName(lieferantenController.lieferanten[i]).toLowerCase() == lieferantenName.toLowerCase()) {
                return lieferantenController.lieferanten[i];
            }

        }
        return null;

    },
    getLieferantFullName: function (lieferant) {
        var name = lieferant.name;
        if (lieferant.vorname && lieferant.vorname.trim() != "")
            name =  name+"  "+lieferant.vorname.trim();
        return name
    },
    savePopupCallback: null,
    savePopup: function (callback) {
        this.savePopupCallback = callback;

        $("#savePopup").popup("open", {
            transition: "pop"
        });
    },
    checkSaved: function (callback) {

        if (lieferantenController.aktuellerLieferantGespeichert)
            callback();
        else
            lieferantenController.savePopup(callback);
    },
    savePopupNo: function () {

        if (this.savePopupCallback)
            this.savePopupCallback()

        this.savePopupCallback = null;

    },
    savePopupYes: function () {

        this.speichereAktuellenLieferant();
        if (this.savePopupCallback)
            this.savePopupCallback();

        this.savePopupCallback = null;

    },
    savePopupCancel: function () {


        this.savePopupCallback = null;
    }



}