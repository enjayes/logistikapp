

var termin = {

    initialize: function() {


        $('#popupTermin .clockpicker').clockpicker();



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
            alert("termin eintragen");
            $("#terminEintragen").show();

            $("#termine_menu").hide();

        })
    }

}


