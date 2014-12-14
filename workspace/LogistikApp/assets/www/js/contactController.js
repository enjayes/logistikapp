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
        this.lieferant = l;
        if (this.lieferant == null){
            if(id == localStorage.lieferanten_id) {
                $("#vorname").val(localStorage.vorname);
                $("#familienname").val(localStorage.name);
                $("#firma").val(localStorage.firma);
                $("#telefon").val(localStorage.telefon);
                $("#email").val(localStorage.email);
            }
        }
        else {
            $("#vorname").val(this.lieferant.vorname);
            $("#familienname").val(this.lieferant.name);
            $("#firma").val(this.lieferant.firma);
            $("#telefon").val(this.lieferant.telefon);
            $("#email").val(this.lieferant.email);

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

                localStorage.lieferanten_id = this.lieferant.id;
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