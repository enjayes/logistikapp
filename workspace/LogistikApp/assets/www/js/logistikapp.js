

//hält Konfigurationsdaten der Lieferanten-App, und ist für die Initialisierung der Komponenten zuständig
var logistikapp = {

    servername: "",
    server_port: 0,
    markt_id: "",
    gruppe_id: "Edeka Baisch",

    //lädt persistente Konfigurationsdaten aus dem localStorage
    retrievePersistentStorage: function () {

        var alert_string = "";
        if (typeof(Storage) !== "undefined") {
            if (localStorage.servername && localStorage.servername != "") {
                logistikapp.servername = localStorage.servername;
            } else {
                alert_string += "Server undefined "
            }
            if (localStorage.server_port && localStorage.server_port != 0) {
                logistikapp.server_port = localStorage.server_port;
            } else {
                alert_string += "Port undefined "
            }
            if (localStorage.markt_id && localStorage.markt_id != "") {
                logistikapp.markt_id = localStorage.markt_id;
            } else {
                alert_string += "Marktname undefined "
            }
        }
        else {
            alert("Error: HTML5 Storage not supported");
        }

        if (!(alert_string == ""))
            console.log(alert_string);


    },

    //wird beim Start der App aufgerufen, für die Initialisierung zuständig
    start: function () {
        $(".centerapp").hide();
        $(".centerapp").animate({ 'zoom': 1.5 }, 0);
        console.dir(nfc)

        localStorage.loggedIn = "false";



        if(!localStorage.servername){
            localStorage.servername = "nodejs-edeka.rhcloud.com"
        }
        else if(localStorage.servername==""){
            localStorage.servername = "nodejs-edeka.rhcloud.com"
        }
        if(!localStorage.server_port){
            localStorage.server_port = "80"
        }
        else if(localStorage.server_port==""){
            localStorage.server_port = "80"
        }
        if(!localStorage.markt_id){
            localStorage.markt_id = "Sindelfingen"
        }
        else if(localStorage.markt_id==""){
            localStorage.markt_id = "Sindelfingen"
        }



        nfcController.init();


        logistikapp.retrievePersistentStorage();

        $("#gruppenname_title").text(logistikapp.gruppe_id);
        $("#marktname_title").text(logistikapp.markt_id);

        //Fastclick nur auf mobilen Geräten
        if (misc.isMobileApp()) {
            FastClick.attach(document.body);

        }

        //Establish Socket Connection
        serverController.initialize(function () {

        });

        //Initialize Views



        //Show App

        $(".centerapp").show();
        window.BOOTSTRAP_OK = true;
        dataController.loadMessages();
        lieferantenController.loadLieferanten();
        clientView.initialize();

    }
}