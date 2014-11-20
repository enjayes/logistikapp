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

    calender:null,

    init: function () {

    },

    ready: function () {
        setTimeout(function () {

            termineTab.calender =  $('#calendar');
            termineTab.calender.fullCalendar(termineController.calendar);

        }, 0);

    },
    open: function () {

        termineTab.calender.fullCalendar('render');
        termineTab.calender.find('.fc-toolbar').show();


    }


}