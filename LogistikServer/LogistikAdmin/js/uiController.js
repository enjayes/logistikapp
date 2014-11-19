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

    init: function () {

        console.log("Ready!")

        //Init Calender rendering
        $('#calendar .fc-toolbar').hide();


        //Window Resize Handling
        $(window).resize(uiController.updateSize);

        //Init Tabs
        tabsConstroller.ready();

        //Execute resize actions
        uiController.updateSize();

    },
    updateSize: function () {
        $("#suchelieferantenliste").css("width", $("#suchelieferantenwidget").width());
    }
}