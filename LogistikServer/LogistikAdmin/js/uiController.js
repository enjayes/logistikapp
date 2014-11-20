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
        setTimeout(function () {
            $("body").show();

            //Init Calender rendering
            $('#calendar .fc-toolbar').hide();


            //Stop Clicks
            $(".ui-tabs-panel .ui-input-btn").click(function (event) {
                console.log("!!!!")
                event.stopPropagation();
            })

            //Buttons parents
            $("#lieferanthinzufuegen").parent(".ui-input-btn").addClass("lieferanthinzufuegenparent");
            $("#allelieferantenanzeigen").parent(".ui-input-btn").addClass("allelieferantenanzeigenparent");

            $("#eventDate").parent(".ui-input-text").addClass("eventDateparent");




            //Window Resize Handling
            $(window).resize(uiController.updateSize);

            //Init Tabs
            tabsController.ready();

            //Rearange Widgets
            $("#popupTermin .eventDateparent").after($("#popupTermin .input-group.clockpicker"))

            //Execute resize actions
            uiController.updateSize();



        }, 0)
    },
    updateSize: function () {
        $("#suchelieferantenliste").css("width", $("#suchelieferantenwidget").width());
    }
}