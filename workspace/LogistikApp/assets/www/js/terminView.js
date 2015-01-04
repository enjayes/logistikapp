

var terminView = {


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


    readInput: function (){
        var termin = {};

        /*
         `Start` text NOT NULL,
         `StartMilli` bigint(20) NOT NULL,
         `End` text NOT NULL,
         `EndMilli` bigint(20) NOT NULL,
         */

        termin.lieferant = clientView.lieferant.id;
        termin.marktid = logistikapp.markt_id;


        termin.start = $("#eventDate").datepicker('getDate');


        if ($('#lieferantRepeatTermin').is(":checked")) {
            termin.repeatDays = $("#lieferantRepeatTerminInput").val();
        }
        else{
            //TODO: nothing??
        }



        termin.title = $("#termintitel").val();
        termin.notizen = $("#terminnotizen").val();


        if ($('#lieferantAlldayTermin').is(":checked")) {
            termin.allDay=true;
        }
        else{
            termin.allDay=false;
        }


        return termin;
    },

    initialize: function() {


        $('#termine_menu .clockpicker').clockpicker();

        terminView.clockPickerHelper();

        $('.clockpicker').clockpicker()
            .find('input').change(function(){
                console.log(this.value);
            });
        var input = $('#single-input').clockpicker({
            placement: 'bottom',
            align: 'left',
            autoclose: true,
            'default': 'now'
        });



        // Manually toggle to the minutes view
        $('#check-minutes').click(function(e){
            // Have to stop propagation here
            e.stopPropagation();
            input.clockpicker('show')
                .clockpicker('toggleView', 'minutes');
        });


        if (/mobile/i.test(navigator.userAgent)) {
            $('input').prop('readOnly', true);
        }


        $("#cb_neuer_termin").click(function () {

            $("#terminEintragen").show();

            $("#termine_menu").hide();

        });


        $("#b_terminabsenden").click(function () {

            var termin = terminView.readInput();

            serverController.termin.create(termin);

            notifications.showWithTimeout("Hinweis", "Der Termin wurde erfolgreich an den Marktleiter übermittelt");

            console.log(termin)

            $("#terminEintragen").hide();

            $("#termine_menu").show();

        });


        $("#b_termin_abbrechen").click(function () {

            $("#terminEintragen").hide();
            $("#termine_menu").show();


        });

        $("#lieferantRepeatTermin").click(function() {
            if ($('#lieferantRepeatTermin').is(":checked"))
            {
                $("#lieferantRepeatTerminChild").show();
            }
            else
            {
                $("#lieferantRepeatTerminChild").hide();
            }
        });


        $("#lieferantAlldayTermin").click(function ()
        {

            if ($("#lieferantAlldayTermin").prop("checked")) {
                $("#termine_menu .clockpicker").addClass("ui-disabled");
            }
            else {
                $("#termine_menu .clockpicker").removeClass("ui-disabled");
            }

        });


    }

}


