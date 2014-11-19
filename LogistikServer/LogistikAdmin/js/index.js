/**
 * index.js.
 *
 * >>Description<<
 *
 * @author Manfred
 * @date 19.11.14 - 17:10
 * @copyright munichDev UG
 */


//Load Socket io and connect
var socket = io();


//On Message
socket.on('message', function (msg) {
    console.log('message: ' + msg);
});


//On Resize

updateSize = function () {

    $("#suchelieferantenliste").css("width", $("#suchelieferantenwidget").width());

}




zeigeAlleLieferanten = false;



/**
 * Init Tabs
 * @param index
 */

prepareTab = function (index) {

    if (index == 0) {
    }
    else if (index == 1) {

        /**
         * Init Filterable
         */

        $.mobile.document.one( "filterablecreate", "#suchelieferantenliste", function() {

            $( "#suchelieferantenliste" ).filterable( "option", "filterCallback", function( index, searchValue ) {

                if(zeigeAlleLieferanten){
                    return false;
                }

                if(searchValue.trim()=="")
                 return true;

                console.log(searchValue)

                if( $(this).text().toLowerCase().search(searchValue.toLowerCase())!=-1)
                    return false;
                else
                    return true;

                // The previous example explains the signature of the callback function.
                //
                // your custom filtering logic goes here.





            });

        });


    }
    else if (index == 2) {
    }

}

/**
 * Init Tabs
 * @param index
 */

initTab = function (index) {

    if (index == 0) {
    }

    else if (index == 1) {

        $("#suchelieferantenwidget #filterBasic-input").on("input change",function(){
             if(zeigeAlleLieferanten)
                 zeigeLieferanten();
        })

        //Init Filterable
        $( "#suchelieferantenliste" ).filterable({
            filterPlaceholder: "Suche nach Lieferanten...",
            filter: function( event, ui ) {
                if($("#suchelieferantenliste li.ui-screen-hidden").length==$("#suchelieferantenliste li").length)
                    $("#suchelieferantenliste").hide();
                 else
                    $("#suchelieferantenliste").show();

            }
        });
        $("#suchelieferantenliste").hide();

    }
    else if (index == 2) {
    }

}


// Open Tab
openTab = function (index) {
    //Inhalt verstecken vor Rendern
    $(".tabinhalt").css("opacity","0")
    setTimeout(function () {
        if (index == 0) {
        }
        else if (index == 1) {

            $('#calendar').fullCalendar('render');
            $('#calendar .fc-toolbar').show();

        }
        else if (index == 2) {

        }
        updateSize();
        //Inhalt anzeigen nach Rendern
        $(".tabinhalt").css("opacity","1")
    }, 0)

}

//Init Tabs
for(var i=0;i<3;i++){
    prepareTab(i);
}

//Init Ui

initUI = function () {


    console.log("Ready!")

    //Window Resize Handling
    $(window).resize(updateSize);

    //Init Calender rendering
    $('#calendar .fc-toolbar').hide();


    //Init Tabs
    for(var i=0;i<3;i++){
        initTab(i);
    }

    //Execute resize actions
    updateSize();
}


//init calendar

$(document).ready(function () {


    $("body").show();

    $('#calendar').fullCalendar({
        header: {
            left: 'prev,next today',
            center: 'title',
            right: 'month,agendaWeek,agendaDay'
        },
        defaultDate: '2014-11-12',
        lang: "de",
        editable: true,
        eventLimit: true, // allow "more" link when too many events
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
        ]
    });

    initUI();


});




zeigeLieferanten = function(){
    zeigeAlleLieferanten = !zeigeAlleLieferanten;
    if(zeigeAlleLieferanten){
            $("#allelieferantenanzeigen").val('Verstecken').button('refresh');
    }else{
            $("#allelieferantenanzeigen").val('Alle anzeigen').button('refresh');
    }
    $( "#suchelieferantenliste" ).filterable( "refresh" );
}





