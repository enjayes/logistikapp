
//Logik hinter dem Kofigurations-Menü
var configView = {
    pinPad: null,

    //Initialisiert das Objekt
    initialize: function () {

        configView.pinPad = new PinPad("#PINcodeConfig", function (code) {

            if (code == "1234") {
                $('#konfi_login_menue').hide();
                $('#konfi_menue').show();
            }
        })




        $("#close_app").click(function () {
            navigator.app.exitApp();
        });


        $("#save_config").click(function () {
            logistikapp.servername = $("#t_server").val();
            logistikapp.server_port = $("#t_port").val();
            logistikapp.markt_id = $("#markt_id").val();
            if (typeof(localStorage) !== "undefined") {
                localStorage.servername = logistikapp.servername;
                localStorage.server_port = logistikapp.server_port;
                localStorage.markt_id = logistikapp.markt_id;

            }
            else {
                alert("Error: HTML5 Storage not supported");

            }


            //Establish Socket Connection
            serverController.initialize();

            //Set Markt title
            $("#marktname_title").text(logistikapp.markt_id);

            notifications.showWithTimeout("Gespeichert!");


        });

        //Öffnet die Konfigurations-Ansicht
        $("#b_konfig").click(function () {

            if (typeof(localStorage) !== "undefined") {
                $("#t_server").val(localStorage.servername);
                $("#t_port").val(localStorage.server_port);
                $("#markt_id").val(localStorage.markt_id);

            }
            else {
                alert("Error: HTML5 Storage not supported");
            }


            configView.pinPad.clear();


            $('#startScreen').hide();

            $('#konfi_login_menue').show();

        });

        //Schließt die  Konfigurations-Ansicht
        $("#config_zurueck").click(function () {
            $('#konfi_menue').hide();
            $('#startScreen').show();
        });

        $("#konfi_zurueck_start").click(function () {
            $('#konfi_login_menue').hide();
            $('#startScreen').show();

        });


        $("#open_admin").click(function () {
            //url = "http://" + localStorage.servername + ":" + localStorage.server_port;
            url = "http://" + localStorage.servername + ":" + localStorage.server_port;
            alert(url);
            misc.openLink(url);

        });

    }

}