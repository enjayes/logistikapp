/**
 * lieferantenController
 *
 *
 *
 *
 * @date 19.11.14 - 22:46
 *
 */



lieferantenController = {
    titleUnbenannt: "Unbenannt",
    defaultPin:"0000",
    lieferanten: [],
    loaded:false,

    init: function () {


        $("#lieferantenInformationen input").on("input", function () {
            lieferantenController.aktuellerLieferantGespeichert = false;
            $("#speichereLieferant").css("opacity", 1).removeClass("ui-disabled");
            $("#rueckgaengigLieferant").css("opacity", 1).removeClass("ui-disabled");
        })

    },
    ready: function () {

        //Get Lieferanten From Server
        var getLieferatenFromServer = function (lieferanten) {

            if (lieferanten) {
                lieferantenController.lieferanten = lieferanten;
                lieferantenController.loaded =true;




                if (lieferantenController.aktuellerLieferant) {
                    var lieferant = lieferantenController.getLieferantByID(lieferantenController.aktuellerLieferant.id);
                    if (!lieferant)
                        lieferantenController.keinenLieferantenZeigen();
                    else if (lieferantenController.aktuellerLieferantGespeichert) {
                        lieferantenController.aktuellerLieferant = $.extend(true, {}, lieferant);

                        lieferantenController.zeigeAktuellenLieferanten(true);

                    }
                }
                if (termineController.aktuellerTerminLieferant) {
                    if (tabsController.tab() == termineTab)
                        var lieferantenInput = termineTab.searchWidget.getInput();
                    else
                        lieferantenInput = $("#lieferantenTerminReadyOnly");

                    var lieferant = lieferantenController.getLieferantByID(termineController.aktuellerTerminLieferant.id);

                    if (!lieferant) {

                        lieferantenInput.val("").trigger("input");
                        $("#lieferantAnzeigen button").addClass("ui-disabled")

                        if (tabsController.tab() == lieferantenTab) {
                            $("#popupTermin").popup("close");
                        } else if (tabsController.tab() == termineTab)
                            termineController.aktuellerTerminLieferant = null;

                    }
                    else {

                        termineController.aktuellerTerminLieferant = $.extend(true, {}, lieferant);
                        lieferantenInput.val(lieferantenController.getLieferantFullName(lieferant));

                        if (tabsController.tab() == termineTab)
                            termineController.aktuellerTerminLieferant = lieferant;

                        $("#lieferantAnzeigen button").removeClass("ui-disabled")


                    }
                }

               //Update Lieferanten Widgets
                uebersichtTab.searchWidget.setList(lieferantenController.lieferanten);
                termineTab.searchWidget.setList(lieferantenController.lieferanten);
                nachrichtenTab.searchWidget.setList(lieferantenController.lieferanten);
                lieferantenTab.searchWidget.setList(lieferantenController.lieferanten);
                uebersichtTab.renderSelectedLieferanten();
                nachrichtenTab.renderSelectedLieferanten();

                nachrichtenController.renderEmpfangeneNachrichten();
                nachrichtenController.renderGesendeteNachrichten();

                uebersichtController.updateAuftragsHistorie();

            }
        }
        serverController.lieferant.getAll(getLieferatenFromServer);
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


    zeigeAktuellenLieferanten: function (dontPush) {


        if (lieferantenController.aktuellerLieferant) {

            //Setze werte
            $("#lieferantVorname").val(lieferantenController.aktuellerLieferant.vorname);
            $("#lieferantName").val(lieferantenController.aktuellerLieferant.name);

            $("#lieferantBearbeitenFirma").val(lieferantenController.aktuellerLieferant.firma);

            $("#lieferantBearbeitenEMail").val(lieferantenController.aktuellerLieferant.email);
            $("#lieferantBearbeitenAdresse").val(lieferantenController.aktuellerLieferant.adresse);
            $("#lieferantBearbeitenNotizen").val(lieferantenController.aktuellerLieferant.notizen);
            $("#lieferantBearbeitenTelefon").val(lieferantenController.aktuellerLieferant.telefon);
            $("#lieferantBearbeitenPin").val(lieferantenController.aktuellerLieferant.pin);

            $("#lieferantBearbeitenColor").css("color","#"+misc.getColorFromUniqueID(lieferantenController.aktuellerLieferant.id));

            $("#lieferantenInformationen").show();
            $("#lieferantenInformationen .redborder").removeClass("redborder");

            lieferantenController.aktuellerLieferantGespeichert = true;
            $("#speichereLieferant").css("opacity", 0).addClass("ui-disabled");
            $("#rueckgaengigLieferant").css("opacity", 0).addClass("ui-disabled");


            if (!dontPush)
                Router.pushState();
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

                this.aktuellerLieferant.firma = $("#lieferantBearbeitenFirma").val();;
                this.aktuellerLieferant.telefon = $("#lieferantBearbeitenTelefon").val();

                this.aktuellerLieferant.email = $("#lieferantBearbeitenEMail").val();
                this.aktuellerLieferant.adresse = $("#lieferantBearbeitenAdresse").val();
                this.aktuellerLieferant.notizen= $("#lieferantBearbeitenNotizen").val();

                this.aktuellerLieferantGespeichert = true;
                $("#speichereLieferant").css("opacity", 0).addClass("ui-disabled");
                $("#rueckgaengigLieferant").css("opacity", 0).addClass("ui-disabled");

                lieferantenController.lieferanten.sort(lieferantenController.lieferantenCompare);

                uebersichtTab.searchWidget.setList(lieferantenController.lieferanten);
                termineTab.searchWidget.setList(lieferantenController.lieferanten);
                nachrichtenTab.searchWidget.setList(lieferantenController.lieferanten);
                lieferantenTab.searchWidget.setList(lieferantenController.lieferanten);
                uebersichtTab.renderSelectedLieferanten();
                nachrichtenTab.renderSelectedLieferanten();

                nachrichtenController.renderEmpfangeneNachrichten();
                nachrichtenController.renderGesendeteNachrichten();

                //Update Server DB
                serverController.lieferant.update(lieferantenController.aktuellerLieferant);

            }
        }
    },

    abbrechenBearbeitungAktuellerLieferant: function () {


        this.zeigeAktuellenLieferanten();


    },


    loescheAktuellenLieferant: function () {

        $("#deleteLieferantPopup").popup("open", {
            transition: "pop"
        });


    },
    deletePopupYes: function () {
        Router.popupClosed = true;

        lieferantenController.lieferanten = lieferantenController.lieferanten.filter(function (el) {
            return el.id !== lieferantenController.aktuellerLieferant.id;
        });


        uebersichtTab.searchWidget.setList(lieferantenController.lieferanten);
        termineTab.searchWidget.setList(lieferantenController.lieferanten);
        nachrichtenTab.searchWidget.setList(lieferantenController.lieferanten);
        lieferantenTab.searchWidget.setList(lieferantenController.lieferanten);
        uebersichtTab.renderSelectedLieferanten();
        nachrichtenTab.renderSelectedLieferanten();

        nachrichtenController.renderEmpfangeneNachrichten();
        nachrichtenController.renderGesendeteNachrichten();

        //Update Server DB
        serverController.lieferant.delete(lieferantenController.aktuellerLieferant);

        lieferantenController.keinenLieferantenZeigen(true);


    },
    deletePopupNo: function () {
        Router.popupClosed = true;

    },
    keinenLieferantenZeigen: function (fade) {
        lieferantenController.aktuellerLieferant = null;
        if (fade) {
            $("#lieferantenInformationen").addClass("geloescht").removeClass("geladen");
            setTimeout(function () {
                $("#lieferantenInformationen").removeClass("geloescht");
                if (!lieferantenController.aktuellerLieferant)
                    $("#lieferantenInformationen").hide();
            }, 1000);
            $("#lieferantenInformationen").addClass("geloescht").removeClass("geladen");

        } else
            $("#lieferantenInformationen").hide();


    }, waehleLieferant: function (lieferant) {


        var waehleLieferant = function () {
            lieferantenController.aktuellerLieferant = $.extend(true, {},lieferant);
            $("#lieferantenInformationen").addClass("geladen").removeClass("geloescht");
            setTimeout(function () {
                $("#lieferantenInformationen").removeClass("geladen");
            }, 1000)
            lieferantenController.zeigeAktuellenLieferanten();

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


            lieferantenController.aktuellerLieferant = new lieferant(misc.titleUnbenannt, misc.titleUnbenannt);

            lieferantenController.lieferanten.push(lieferantenController.aktuellerLieferant);
            lieferantenController.lieferanten.sort(lieferantenController.lieferantenCompare);

            uebersichtTab.searchWidget.setList(lieferantenController.lieferanten);
            termineTab.searchWidget.setList(lieferantenController.lieferanten);
            nachrichtenTab.searchWidget.setList(lieferantenController.lieferanten);
            lieferantenTab.searchWidget.setList(lieferantenController.lieferanten);
            uebersichtTab.renderSelectedLieferanten();
            nachrichtenTab.renderSelectedLieferanten();

            nachrichtenController.renderEmpfangeneNachrichten();
            nachrichtenController.renderGesendeteNachrichten();

            //Update Server DB
            serverController.lieferant.create(lieferantenController.aktuellerLieferant);
            //Get New Pin for Lieferant
            serverController.lieferant.getNewPin(lieferantenController.aktuellerLieferant, function(lieferant){

                if(lieferant){
                    var newPin = lieferant.pin;
                    console.log( lieferant.pin)
                    console.dir(lieferant);
                    lieferant =    lieferantenController.getLieferantByID(lieferant.id)
                    if(lieferant){
                        console.log("!")
                        lieferant.pin =  newPin;
                        if(lieferant.id==lieferantenController.aktuellerLieferant.id){

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


                            lieferantenController.zeigeAktuellenLieferanten();
                            $("#lieferantVorname").select();

                        }

                    }
                }

            });


            $("#suchelieferantenliste").hide();




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
        if(!lieferant)
         return "";
        var name = lieferant.name;
        if (lieferant.vorname && lieferant.vorname.trim() != "")
            name = name + " " + lieferant.vorname.trim();
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
        Router.popupClosed = true;

        if (this.savePopupCallback)
            this.savePopupCallback();

        this.savePopupCallback = null;

    },
    savePopupYes: function () {
        Router.popupClosed = true;

        this.speichereAktuellenLieferant();
        if (this.savePopupCallback)
            this.savePopupCallback();

        this.savePopupCallback = null;

    },
    savePopupCancel: function () {


        this.savePopupCallback = null;
    }



}