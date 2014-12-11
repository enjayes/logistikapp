/**
 * logistikapp.
 * @date 19.11.14 - 14:43
 *
 */


var logistikapp = {

    servername:"",
    server_port:0,
    markt_id:"",

    retrievePersistentStorage:function(){


        alert_string = "";
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
            alert("Sorry, your browser does not support web storage...");
            document.getElementById("result").innerHTML = "Sorry, your browser does not support web storage...";
        }

        if(!(alert_string==""))
        {
            alert(alert_string);
        }

    },


    start:function(){

        //servername etc
        logistikapp.retrievePersistentStorage();

        //Use Fastclick
        if(misc.isMobileApp()){
            FastClick.attach(document.body);

        }

        //Establish Socket Connection
        serverController.initialize();


        if ($('#cb_besuch').is(":checked"))
        {
            job.besuch = true;
        }

        //config_menue
        $("#save_config").click(function()
        {
            logistikapp.servername = $("#t_server").val();
            logistikapp.server_port = $("#t_port").val();
            logistikapp.markt_id = $("#markt_id").val();

            if(typeof(Storage) !== "undefined")
            {
                localStorage.servername = logistikapp.servername;
                localStorage.server_port = logistikapp.server_port;
                localStorage.markt_id = logistikapp.markt_id;
                alert("Gespeichert!");
            }
            else
            {
                alert("Sorry, your browser does not support web storage...");
                document.getElementById("result").innerHTML = "Sorry, your browser does not support web storage...";
            }

        });

        //open konfig
        $("#b_konfig").click(function()
        {

            if(typeof(Storage) !== "undefined")
            {
                $("#t_server").val(localStorage.servername);
                $("#t_port").val(localStorage.server_port);
                $("#markt_id").val(localStorage.markt_id);
            }
            else
            {
                alert("Sorry, your browser does not support web storage...");
                document.getElementById("result").innerHTML = "Sorry, your browser does not support web storage...";
            }

            $('#startScreen').hide();
            $('#konfi_menue').show();

        });


        //Initialize Views
        startView.initialize();
        loginView.initialize();

        clientView.initialize();

        // TODO: load client data

    }
}