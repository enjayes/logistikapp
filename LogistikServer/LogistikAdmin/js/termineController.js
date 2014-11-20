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
    events: [
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
            url: 'http://google.com/',
            start: '2014-11-28'
        }
    ],
    calendar: {
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


            var event = new CalenderEvent(date);
            termineController.zeigeEvent(event);
            // alert('Clicked on: ' + date.format());
            //  alert('Coordinates: ' + jsEvent.pageX + ',' + jsEvent.pageY);
            //  alert('Current view: ' + view.name);
            // change the day's background color just for fun
            //$(this).css('background-color', 'red');

        }, eventClick: function (calEvent, jsEvent, view) {


            termineController.zeigeEvent();

            //alert('Event: ' + calEvent.title);
            //alert('Coordinates: ' + jsEvent.pageX + ',' + jsEvent.pageY);
            //alert('View: ' + view.name);

            // change the border color just for fun
            //$(this).css('border-color', 'red');

        },
        events: []

    } ,
    init:function(){

        this.calendar.events = termineController.events
    }  ,
    aktuellesEvent: null,

    zeigeEvent:function(calenderEvent){

        $("#popupTermin").popup("open", {
            transition: "pop"
        });

        termineController.aktuellesEvent = calenderEvent;



    },
    abbrechenBearbeitungAktuellesEvent:function(){
        $("#popupTermin").popup("close");

    },

    speichereAktuellesEvent:function(){

        termineTab.calender.fullCalendar('addEventSource', [
            {
                title: 'All Day Event',
                start: termineController.aktuellesEvent.start.format()
            }
        ])

        $("#popupTermin").popup("close");

    }


}