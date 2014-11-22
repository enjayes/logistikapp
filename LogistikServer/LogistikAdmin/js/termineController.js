/**
 * termineController.
 *
 * >>Description<<
 *
 * @author Manfred
 * @date 19.11.14 - 22:46
 * @copyright munichDev UG
 */



termineController = {
    tabPosition: 1,
    init: function () {

        this.calendarData.events = termineController.events;

        //Get Lieferanten From Server

        var getTermineFromServer = function (termine) {
            if (termine) {


                 termine.forEach(function(termin){
                     if(termin.start)
                         termin.start = termineTab.calenderFactory.moment( termin.start);
                     if(termin.end)
                         termin.end = termineTab.calenderFactory.moment( termin.end);
                 })


                termineController.events  = termine;
                termineController.calendarData.events = termine;
                if (termineTab.calender) {

                    if (tabsController.tab() == termineTab) {

                        if (termineController.aktuellesEvent) {
                            var termin = termineController.getTerminByID(termineController.aktuellesEvent.id);

                            console.log(termin+"   "+termineController.aktuellerTerminGespeichert)
                            console.dir(termin)
                            if (!termin)
                                $("#popupTermin").popup("close");
                            else if (termineController.aktuellerTerminGespeichert) {
                                termineController.zeigeEvent(termin,termineController.aktuellesEventIstNeu);
                            }

                        }
                    }


                    termineTab.calender.fullCalendar('removeEvents');
                    termineTab.calender.fullCalendar('addEventSource', termine);


                }

            }
        }

        serverController.termin.getAll(getTermineFromServer);
        $("#popupTermin input,#popupTermin textarea").on("input", function () {
            if (termineController.aktuellerTerminGespeichert) {
                termineController.aktuellerTerminGespeichert = false;
                termineController.zeigeSpeicherButton()
            }
        })


        var checkValidLieferant = function () {
            var lieferantenName = $("#suchelieferantenwidget #filterBasic-input").val();

            var lieferant = lieferantenController.getLieferantByName(lieferantenName);
            if (lieferant && lieferant.id) {
                termineController.aktuellerTerminLieferant = lieferant;
                $("#lieferantAnzeigen button").removeClass("ui-disabled");
            }
            else {
                termineController.aktuellerTerminLieferant = null;
                $("#lieferantAnzeigen button").addClass("ui-disabled");
            }
            if (termineController.aktuellerTerminGespeichert) {
                termineController.aktuellerTerminGespeichert = false;
                termineController.zeigeSpeicherButton()
            }

        }

        $("#suchelieferantenliste, #suchelieferantenwidget a").click(function () {
            setTimeout(checkValidLieferant(), 0)
        })

        $("#suchelieferantenwidget #filterBasic-input").on("input", checkValidLieferant)


        //Check if time is right
        $('#popupTermin .clockpicker input').change(function () {
            var value = (($(this).val() || '') + '').split(':');
            var hours = parseInt(value[0]) || 0;
            var minutes = parseInt(value[1]) || 0;
            if (("" + hours).length == 1)
                hours = "0" + hours;
            if (("" + minutes).length == 1)
                minutes = "0" + minutes;
            $(this).val(hours + ":" + minutes)
        });


    },
    events: [],
    calendarData: {
        header: {
            left: 'prev,next today',
            center: 'title',
            right: 'month,agendaWeek,agendaDay'
        },
        defaultDate: '2014-11-12',
        lang: "de",
        editable: true,
        eventLimit: true, // allow "more" link when too many events
        dayClick: function (date, jsEvent, view) {

            console.log(date)


            termineController.erzeugeEvent(date)

            // alert('Clicked on: ' + date.format());
            //  alert('Coordinates: ' + jsEvent.pageX + ',' + jsEvent.pageY);
            //  alert('Current view: ' + view.name);
            // change the day's background color just for fun
            //$(this).css('background-color', 'red');

        }, eventClick: function (calEvent, jsEvent, view) {


            termineController.zeigeEvent(calEvent, false);

            //alert('Event: ' + calEvent.title);
            //alert('Coordinates: ' + jsEvent.pageX + ',' + jsEvent.pageY);
            //alert('View: ' + view.name);

            // change the border color just for fun
            //$(this).css('border-color', 'red');

        },
        eventDrop: function (event, delta, revertFunc) {
            serverController.termin.update(event);
        },

        eventResize: function (event, delta, revertFunc, jsEvent, ui, view) {
            serverController.termin.update(event);
        },
        events: [],
        timeFormat: 'H(:mm)'

    },

    waehleLieferant: function (index) {
        if (lieferantenController.zeigeAlleLieferanten)
            lieferantenController.zeigeLieferanten();

        termineController.aktuellerTerminLieferant = lieferantenController.lieferanten[index];
        $("#suchelieferantenwidget #filterBasic-input").val(lieferantenController.getLieferantFullName(lieferantenController.lieferanten[index]));
        $("#suchelieferantenliste").hide();


    },

    erzeugeEvent: function (date, lieferant) {
        if (lieferant)
            var lieferantid = lieferant.id;
        else
            lieferantid = null;

        var event = CalenderEventFactory.create(misc.titleUnbenannt, date, lieferantid);

        termineController.zeigeEvent(event, true);
    },
    aktuellesEvent: null,
    aktuellesEventIstNeu: false,
    aktuellerTerminGespeichert: true,
    zeigeEvent: function (calenderEvent, neuesEvent) {


        termineTab.alterFilterInput = $("#suchelieferantenwidget #filterBasic-input").val();

        termineController.aktuellesEvent = $.extend({}, calenderEvent);
        termineController.aktuellesEventIstNeu = neuesEvent;

        var date = termineController.aktuellesEvent.start.format('DD.MM.YYYY');
        var time = termineController.aktuellesEvent.start.format('HH:mm');


        //Setze werte in Popup
        $("#eventDate").val(date).trigger("change");
        $(".input-group.clockpicker input").val(time);

        if (neuesEvent)
            $("#terminheader").text("Neuer Termin");
        else
            $("#terminheader").text("Termin");

        $("#termintitel").val(calenderEvent.title);

        $("#terminnotizen").val(calenderEvent.notizen);


        if (tabsController.tab() == termineTab)
            var lieferantenInput = $("#suchelieferantenwidget #filterBasic-input")
        else
            lieferantenInput = $("#lieferantenTerminReadyOnly");

        if (calenderEvent.lieferant && calenderEvent.lieferant.trim() != "") {
            var lieferant = lieferantenController.getLieferantByID(calenderEvent.lieferant);

            if (lieferant && lieferant.id) {
                termineController.aktuellerTerminLieferant = lieferant;
                $("#lieferantAnzeigen button").removeClass("ui-disabled")
                lieferantenInput.val(lieferantenController.getLieferantFullName(lieferant));
            }
            else {
                $("#lieferantAnzeigen button").addClass("ui-disabled")
                lieferantenInput.val("");
            }


        } else
            lieferantenInput.val("");


        if (calenderEvent.allDay) {
            $("#lieferantAlldayTermin").prop("checked", true).checkboxradio("refresh");
            $("#popupTermin .clockpicker").addClass("ui-disabled");

        }
        else {
            $("#lieferantAlldayTermin").removeProp("checked", true).checkboxradio("refresh");
            $("#popupTermin .clockpicker").removeClass("ui-disabled");

        }
        $("#popupTermin .ui-checkbox.lieferantAlldayTerminparent").click(function () {
            setTimeout(function () {
                if ($("#lieferantAlldayTermin").prop("checked"))
                    $("#popupTermin .clockpicker").addClass("ui-disabled");
                else
                    $("#popupTermin .clockpicker").removeClass("ui-disabled");
                if (termineController.aktuellerTerminGespeichert) {
                    termineController.aktuellerTerminGespeichert = false;
                    termineController.zeigeSpeicherButton()
                }

            }, 0)

        })


        $("#popupTermin .redborder").removeClass("redborder");


        termineController.aktuellerTerminGespeichert = !neuesEvent;

        termineController.zeigeSpeicherButton();

        $("#popupTermin").popup("open", {
            transition: "pop"
        });


    },

    abbrechenBearbeitungAktuellesEvent: function () {
        Router.popupClosed = true;

        termineController.aktuellerTerminGespeichert = true;

        $("#popupTermin").popup("close");
        $("#suchelieferantenwidget #filterBasic-input").val(termineTab.alterFilterInput);

    },


    speichereAktuellesEvent: function () {
        Router.popupClosed = true;

        var validated = true;
        $("#popupTermin .redborder").removeClass("redborder");

        //Validieren

        //Titel
        if ($("#termintitel").val().trim() == "") {
            validated = false;
            $("#termintitel").parent(".ui-input-text").addClass("redborder");
        }

        //Lieferanten Name validieren


        if ((!(tabsController.tab() == termineTab && $("#suchelieferantenwidget #filterBasic-input").val().trim() == "")) && (!termineController.aktuellerTerminLieferant || !termineController.aktuellerTerminLieferant.id)) {
            validated = false;
            $("#suchelieferantenwidget #filterBasic-input").parent(".ui-input-search").addClass("redborder");
        }


        var start = termineTab.calenderFactory.moment($("#eventDate").datepicker('getDate'));

        if (start.format('DD.MM.YYYY') != $("#eventDate").val().trim()) {
            validated = false;
            $("#eventDate").parent(".ui-input-text").addClass("redborder");
        }

        //Alle Daten in Ordnung
        if (validated) {
            termineController.aktuellerTerminGespeichert = true;

            //Eintragen
            termineController.aktuellesEvent.title = $("#termintitel").val();
            termineController.aktuellesEvent.notizen = $("#terminnotizen").val();

            termineController.aktuellesEvent.allDay = $("#lieferantAlldayTermin").prop("checked");

            if (termineController.aktuellesEvent.allDay) {
                start.stripTime();
                termineController.aktuellesEvent.end = null;
            } else {
                var starttime = $('#popupTermin .clockpicker input').val();
                start.time(starttime + ":00");

                if (termineController.aktuellesEvent.end) {
                    var oldStart = termineTab.calenderFactory.moment(termineController.aktuellesEvent.start);
                    var oldEnd = termineTab.calenderFactory.moment(termineController.aktuellesEvent.end);
                    var duration = moment.duration(oldEnd.diff(oldStart));

                    if (duration) {
                        termineController.aktuellesEvent.end = termineTab.calenderFactory.moment(start).add(duration);
                    }


                }


            }
            termineController.aktuellesEvent.start = start;

            if (!termineController.aktuellerTerminLieferant || !termineController.aktuellerTerminLieferant.id) {
                if (termineController.aktuellesEvent.lieferant)
                    delete termineController.aktuellesEvent.lieferant
            }
            else
                termineController.aktuellesEvent.lieferant = termineController.aktuellerTerminLieferant.id;


            termineTab.calender.fullCalendar('removeEvents', termineController.aktuellesEvent.id);

            termineTab.calender.fullCalendar('addEventSource', [termineController.aktuellesEvent]);

            termineController.events = termineTab.calender.fullCalendar('clientEvents')

            if (this.aktuellesEventIstNeu)
                serverController.termin.create(termineController.aktuellesEvent);
            else
                serverController.termin.update(termineController.aktuellesEvent);


            $("#popupTermin").popup("close");
        }
        $("#suchelieferantenwidget #filterBasic-input").val(termineTab.alterFilterInput);

    },
    zeigeSpeicherButton: function () {


        if (termineController.aktuellerTerminGespeichert) {
            $("#popupTermin .bottombuttons").addClass("saved");
            $("#speichereaktuelleseventbutton").hide();
            $("#abbrechenbearbeitungaktuelleseventbutton").val("Ok").text("Ok").removeClass("ui-icon-delete").addClass("ui-icon-check");

        } else {

            $("#popupTermin .bottombuttons").removeClass("saved");
            $("#speichereaktuelleseventbutton").css("opacity", 0);
            $("#speichereaktuelleseventbutton").show();
            $("#abbrechenbearbeitungaktuelleseventbutton").hide();

            $("#abbrechenbearbeitungaktuelleseventbutton").val("Abbrechen").text("Abbrechen").removeClass("ui-icon-check").addClass("ui-icon-delete");
            setTimeout(function () {
                $("#speichereaktuelleseventbutton").addClass("fade");
                $("#abbrechenbearbeitungaktuelleseventbutton").addClass("fade");
                $("#abbrechenbearbeitungaktuelleseventbutton").show();
            }, 0)

        }


    },
    oeffneLieferantenTab: function () {

        if (termineController.aktuellerTerminLieferant) {
            var url = location.protocol + "//" + location.host + "#state=tab_2+l_" + termineController.aktuellerTerminLieferant.id + "+jq_";
            var win = window.open(url, '_blank');
            win.focus();
        }

    },
    getTerminByID: function (terminID) {

        for (var i = 0; i < termineController.events.length; i++) {
            if (termineController.events[i].id == terminID) {
                return termineController.events[i];
            }

        }
        return null;

    }


}


/*
 {
 title: 'All Day Event',
 start: '2014-11-01'
 },
 {
 title: 'Long Event',
 start: '2014-11-07',
 end: '2014-11-10'
 },
 {
 id: 999,
 title: 'Repeating Event',
 start: '2014-11-09T16:00:00'
 },
 {
 id: 999,
 title: 'Repeating Event',
 start: '2014-11-16T16:00:00'
 },
 {
 title: 'Conference',
 start: '2014-11-11',
 end: '2014-11-13'
 },
 {
 title: 'Meeting',
 start: '2014-11-12T10:30:00',
 end: '2014-11-12T12:30:00'
 },
 {
 title: 'Lunch',
 start: '2014-11-12T12:00:00'
 },
 {
 title: 'Meeting',
 start: '2014-11-12T14:30:00'
 },
 {
 title: 'Happy Hour',
 start: '2014-11-12T17:30:00'
 },
 {
 title: 'Dinner',
 start: '2014-11-12T20:00:00'
 },
 {
 title: 'Birthday Party',
 start: '2014-11-13T07:00:00'
 },
 {
 title: 'Click for Google',
 start: '2014-11-28'
 }*/