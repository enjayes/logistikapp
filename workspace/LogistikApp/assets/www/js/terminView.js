
//Logik hinter dem Termin-GUI
var terminView = {

    //Auswahl der Uhrzeit
    clockPickerHelper: function () {
        $('.clockpicker').clockpicker();
        $('.clockpicker').clockpicker()
            .find('input').change(function () {
                console.log(this.value);
            });
        var input = $('#single-input').clockpicker({
            placement: 'bottom',
            align: 'left',
            autoclose: true,
            'default': 'now'
        });


        // Manually toggle to the minutes view
        $('#check-minutes').click(function (e) {
            // Have to stop propagation here
            e.stopPropagation();
            input.clockpicker('show')
                .clockpicker('toggleView', 'minutes');
        });
        if (/mobile/i.test(navigator.userAgent)) {
            $('input').prop('readOnly', true);
        }
    },

    //Daten der GUI werden in einem termin-Objekt gespeichert
    readInput: function () {
        var termin = {};

        /*
         `Start` text NOT NULL,
         `StartMilli` bigint(20) NOT NULL,
         `End` text NOT NULL,
         `EndMilli` bigint(20) NOT NULL,
         */
        termin.id = misc.getUniqueID();
        termin.lieferant = clientView.lieferant.id;
        termin.marktId = configData.markt.id;

        //TODO:
        //termin.start = moment($("#eventDate").datepicker('getDate'));
        //termin.start = $("#eventDate").datepicker('getDate');
        //termin.start = new Date();
        termin.start = new Date($("#eventDate").val());





        termin.alarm = 0;

        if ($('#lieferantRepeatTermin').is(":checked")) {
            termin.repeatDays = $("#lieferantRepeatTerminInput").val();
        }
        else {
            termin.repeatDays = 0;
            //TODO: nothing??
        }

        termin.title = $("#termintitel").val();
        termin.notizen = $("#terminnotizen").val();


        if ($('#lieferantAlldayTermin').is(":checked")) {
            termin.allDay = true;
        }
        else {
            termin.allDay = false;
            //set time
            time = $("#eventTime").val();
            var array = time.split(':');
            hours = array[0];
            minutes = array[1];
            // Set hours
            termin.start.setHours(hours);
            // Then set minutes
            termin.start.setMinutes(minutes);
        }


        console.dir(termin);
        return termin;
    },

    initialize: function () {


        $('#termine_menu .clockpicker').clockpicker();

        terminView.clockPickerHelper();

        $('.clockpicker').clockpicker()
            .find('input').change(function () {
                console.log(this.value);
            });
        var input = $('#eventTime').clockpicker({
            placement: 'bottom',
            align: 'left',
            autoclose: true,
            'default': 'now'
        });


        // Manually toggle to the minutes view
        $('#check-minutes').click(function (e) {
            // Have to stop propagation here
            e.stopPropagation();
            input.clockpicker('show')
                .clockpicker('toggleView', 'minutes');
        });


        if (/mobile/i.test(navigator.userAgent)) {
            $('input').prop('readOnly', true);
        }





        $("#b_terminabsenden").click(function () {

            var termin = terminView.readInput();
            console.log("termin:")
            console.log(termin)
            console.log("serverController.termin.buildDTO(termin):")

            console.log( serverController.termin.buildDTO(termin))
            $("#terminEintragen").hide();

            $("#termine_menu").show();

            console.log("serverController.termin.create(termin);")
             try {
              serverController.termin.create(termin);
             }
             catch (e) {
                 try {
                     serverController.termin.create(termin);
                 }
                 finally{

                 }
            }
            console.log("notifications -> termin ")
            notifications.showWithTimeout("Hinweis", "Der Termin wurde erfolgreich an den Marktleiter übermittelt");

            console.log(termin)



        });


        $("#b_termin_abbrechen").click(function () {

            notifications.showWithTimeout("Hinweis", "Der Termin wurde <p style='color:#ff2723'>nicht</p> gespeichert!");

            $("#terminEintragen").hide();
            $("#termine_menu").show();


        });

        $("#lieferantRepeatTermin").click(function () {
            if ($('#lieferantRepeatTermin').is(":checked")) {
                $("#lieferantRepeatTerminChild").show();
            }
            else {
                $("#lieferantRepeatTerminChild").hide();
            }
        });


        $("#lieferantAlldayTermin").click(function () {

            if ($("#lieferantAlldayTermin").prop("checked")) {
                $("#lieferantClock").hide();
                $("#termine_menu .clockpicker").addClass("ui-disabled");
            }
            else {
                $("#termine_menu .clockpicker").removeClass("ui-disabled");
                $("#lieferantClock").show();
            }

        });


    }

}


