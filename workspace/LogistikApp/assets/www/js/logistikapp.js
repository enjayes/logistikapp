/**
 * logistikapp.
 * @date 19.11.14 - 14:43
 *
 */


var logistikapp = {

    servername:"",
    server_port:0,
    markt_id:"",
    gruppe_id: "Edeka Baisch",

    retrievePersistentStorage:function(){


        var alert_string = "";
        if(typeof(Storage) !== "undefined")
        {
            if (localStorage.servername && localStorage.servername!="") {
                logistikapp.servername = localStorage.servername;
            } else
            {
                alert_string += "Server undefined "
            }
            if (localStorage.server_port && localStorage.server_port!=0) {
                logistikapp.server_port = localStorage.server_port;
            } else
            {
                alert_string += "Port undefined "
            }
            if (localStorage.markt_id && localStorage.markt_id!="") {
                logistikapp.markt_id = localStorage.markt_id;
            } else
            {
                alert_string += "Marktname undefined "
            }
        }
        else
        {
            alert("Error: HTML5 Storage not supported");
        }

        if(!(alert_string==""))
            console.log(alert_string);


    },


    start:function(){

        console.dir(nfc)


        nfc.addNdefListener(
            function(nfcEvent) {
            // display the tag as JSON
              alert(JSON.stringify(nfcEvent.tag));
                //todo
            },
            function() {
                //alert("Success.");
            },
            function() {
                //alert("Fail.");
            }
        );



        //servername etc
        logistikapp.retrievePersistentStorage();


        $("#gruppenname_title").text(logistikapp.gruppe_id);
        $("#marktname_title").text(logistikapp.markt_id);


        //Use Fastclick
        if(misc.isMobileApp()){
            FastClick.attach(document.body);

        }

        //Establish Socket Connection
        serverController.initialize(function(){




        });

        //Initialize Views
        startView.initialize();
        configView.initialize();
        loginView.initialize();
        clientView.initialize();

        //Show App
        $(".centerapp").show();


    }
}