/**
 * termineTab.
 *
 * >>Description<<
 *
 * @author Manfred
 * @date 19.11.14 - 21:58
 * @copyright munichDev UG
 */



termineTab = {

    calender: null,
    calenderFactory: null,
    allowOpening: false,
    init: function () {




    },

    ready: function () {

        $('#popupTermin .clockpicker').clockpicker();

        //Termine Popup Style Widgets
        $("#popupTermin").on("popupafteropen", function (event, ui) {
            uiController.updateSize();
            $("#termintitel").select();

        });
        $("#popupTermin").on("popupbeforeposition", function (event, ui) {


            if (tabsController.tab() == termineTab) {
                $("#lieferantenTerminReadyOnly").parent(".ui-input-text").hide();
                $("#lieferantTermin").append($("#suchelieferantenwidget"));
            } else
                $("#lieferantenTerminReadyOnly").parent(".ui-input-text").show();


        });

        $("#popupTermin").on("popupafterclose", function (event, ui) {

            $("#speichereaktuelleseventbutton, #abbrechenbearbeitungaktuelleseventbutton").removeClass("fade");


            if (tabsController.tab() == termineTab)
                $("#suchelieferanten").prepend($("#suchelieferantenwidget"));


        });


        setTimeout(function () {


            termineTab.calender = $('#calendar');
            termineTab.calender.fullCalendar(termineController.calendarData);
            termineTab.calenderFactory = termineTab.calender.fullCalendar("getCalendar");

            delete termineController.calendarData.events;

        }, 0);

    },
    open: function () {

        termineTab.calender.fullCalendar('render');
        termineTab.calender.find('.fc-toolbar').show();
        termineTab.calender.fullCalendar( 'rerenderEvents' )

    },
    waehleLieferant: function (index) {
        termineController.waehleLieferant(index);
    }


}