/**
 * uiController.
 *
 * >>Description<<
 *
 * @author Manfred
 * @date 19.11.14 - 22:11
 * @copyright munichDev UG
 */



uiController = {

    ready: function () {


        window.onbeforeunload = function (event) {

            if (typeof event == 'undefined') {
                event = window.event;
            }

            if (event) {
                if (!lieferantenController.aktuellerLieferantGespeichert) {

                    var message = 'Sie haben ihre Änderungen noch nicht gespeichert!';
                    event.returnValue = message;
                }
            }

            return message;
        }



            $("body").show();

            //Init Calender rendering
            $('#calendar .fc-toolbar').hide();


            //Stop Clicks
            $(".ui-tabs-panel .ui-input-btn").click(function (event) {
                event.stopPropagation();
            })

            //Buttons parents
            $("#lieferanthinzufuegen").parent(".ui-input-btn").addClass("lieferanthinzufuegenparent");
            $("#allelieferantenanzeigen").parent(".ui-input-btn").addClass("allelieferantenanzeigenparent");

            $("#eventDate").parent(".ui-input-text").addClass("eventDateparent");

            $("#lieferantAlldayTermin").parent(".ui-checkbox").addClass("lieferantAlldayTerminparent");




            //Window Resize Handling
            $(window).resize(uiController.updateSize);

            //Init Tabs
            tabsController.ready();

            //Rearange Widgets
            $("#popupTermin .eventDateparent").after($("#popupTermin .input-group.clockpicker"))

            $("#popupTermin .eventDateparent").append($("#popupTermin .input-group-addon").clone())




            //Execute resize actions
            uiController.updateSize();



    },
    updateSize: function () {
        $("#suchelieferantenliste").css("width", $("#suchelieferantenwidget").width());
    }
}