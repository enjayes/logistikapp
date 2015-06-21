/**
 * Created by StandardB on 19.06.2015.
 */

var termineListView = {

    //TODO: remove
    //templateList: null,
    dummy_termine: [],

    //TODO: remove
    addDummies: function()
    {
        termin1 = new Termin();
        termin1.title="1";
        termin2 = new Termin();
        termin2.title="2";
        termin3= new Termin();
        termin3.title="3";

        this.dummy_termine.push(termin1);
        this.dummy_termine.push(termin2);
        this.dummy_termine.push(termin3);






        for (var t in this.dummy_termine)
        {
            var listItem = "<li>" + termin1.title + "</li>";
            $("#termine_liste").append(listItem);

            var string = t.title;
            console.log("this.dummy_termine");
            console.log(t.title);
            var listItem = "<li>" + string + "</li>";
            $("#termine_liste").append(listItem);
            $("#termine_liste").listview("refresh");
        }

    },


    initialize: function()
    {
        //TODO: remove
        this.addDummies();





        $("#cb_neuer_termin").click(function () {

            $("#terminEintragen").show();

            $("#termine_menu").hide();

        });
    }

}