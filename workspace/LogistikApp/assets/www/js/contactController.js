/**
 * configView
 *
 *
 * @author
 * @date 11.12.14 - 17:39
 * @copyright
 */


var contactController = {


    lieferant: null,




    set: function(id,l) {
        lieferant = l;
        if (lieferant == null && id == localStorage.lieferanted_id) {
            $("#vorname").val(localStorage.vorname);
            $("#familienname").val(localStorage.name);
            $("#firma").val(localStorage.firma);
            $("#telefon").val(localStorage.telefon);
            $("#email").val(localStorage.email);
        }
        else {
            $("#vorname").val(lieferant.vorname);
            $("#familienname").val(lieferant.name);
            $("#firma").val(lieferant.firma);
            $("#telefon").val(lieferant.telefon);
            $("#email").val(lieferant.email);

        }
    },


    store:  function() {
        if(lieferant!=null) {
            lieferant.vorname = $("#vorname").val();
            lieferant.name = $("#familienname").val();
            lieferant.firma = $("#firma").val();
            lieferant.telefon = $("#telefon").val();
            lieferant.email = $("#email").val();


            localStorage.vorname = lieferant.vorname;
            localStorage.name = lieferant.name;
            localStorage.firma = lieferant.firma;
            localStorage.telefon = lieferant.telefon;
            localStorage.email = lieferant.email;

            serverController.lieferant.update(lieferant);
        }

    }

}