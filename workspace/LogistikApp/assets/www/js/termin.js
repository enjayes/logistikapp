

var termin = {

    initialize: function() {

        $('#popupTermin .clockpicker').clockpicker();

        $("#cb_neuer_termin").click(function () {
            $("#terminEintragen").show();
            $("#termine_menu").hide();

        })
    }

}


