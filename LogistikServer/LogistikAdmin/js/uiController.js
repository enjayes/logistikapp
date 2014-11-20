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
        $("body").show();

        //Init Calender rendering
        $('#calendar .fc-toolbar').hide();

        //Stop Clicks
        $(".ui-tabs-panel .ui-input-btn").click(function(event){
            event.stopPropagation();
        })

        //Buttons parents
        $("#lieferanthinzufuegen").parent().addClass("lieferanthinzufuegenparent");
        $("#allelieferantenanzeigen").parent().addClass("allelieferantenanzeigenparent");


        //Window Resize Handling
        $(window).resize(uiController.updateSize);

        //Init Tabs
        tabsController.ready();

        //Execute resize actions
        uiController.updateSize();

    },
    updateSize: function () {
        $("#suchelieferantenliste").css("width", $("#suchelieferantenwidget").width());
    }
}