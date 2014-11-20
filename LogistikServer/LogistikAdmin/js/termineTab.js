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
        $('.clockpicker').clockpicker();

        setTimeout(function () {




            termineTab.calender =  $('#calendar');
            termineTab.calender.fullCalendar(termineController.calendar);
            delete termineController.calendar.events;

        }, 0);

    },
    open: function () {

        termineTab.calender.fullCalendar('render');
        termineTab.calender.find('.fc-toolbar').show();


    }


}