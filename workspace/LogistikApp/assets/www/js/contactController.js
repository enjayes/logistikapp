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
        if(this.lieferant!=undefined) {
            if(this.lieferant!=null) {
                this.lieferant.vorname = $("#vorname").val();
                this.lieferant.name = $("#familienname").val();
                this.lieferant.firma = $("#firma").val();
                this.lieferant.telefon = $("#telefon").val();
                this.lieferant.email = $("#email").val();


                localStorage.vorname = this.lieferant.vorname;
                localStorage.name = this.lieferant.name;
                localStorage.firma = this.lieferant.firma;
                localStorage.telefon = this.lieferant.telefon;
                localStorage.email = this.lieferant.email;

                serverController.lieferant.update(this.lieferant);
            }
        }

    }

}