/**
 * logistikapp.
 *
 *
 *
 *
 * @date 19.11.14 - 14:43
 *
 */


var logistikapp = {

    servername:"",
    server_port:0,
    markt_id:"",

    retrievePersistentStorage:function(){


        alert("retrievePersistentStorage");

        alert_string = "";
        if(typeof(Storage) !== "undefined")
        {
            if (localStorage.servername) {
                logistikapp.servername = localStorage.servername;
            } else
            {
                alert_string += "Server undefined "
            }
            if (localStorage.server_port) {
                logistikapp.servername = localStorage.servername;
            } else
            {
                alert_string += "Port undefined "
            }
            if (localStorage.markt_id) {
                logistikapp.servername = localStorage.servername;
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



        new PinPad("#PINcode",function(code){
            loginController.login(code);
        })


        if ($('#cb_besuch').is(":checked"))
        {
            job.besuch = true;
        }

        //config_menue
        $("#save_config").click(function()
        {
            logistikapp.servername = $("#t_server").val();
            Logistikapp.server_port = $("#t_port").val();
            Logistikapp.markt_id = $("#markt_id").val();

        });


        //Initialize View

        clientView.initialize();


        //TODO: call Client Log-In

        // TODO: load client data

    }
}