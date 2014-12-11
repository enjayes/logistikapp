/**
 * configView
 *
 *
 * @author
 * @date 11.12.14 - 17:39
 * @copyright
 */


var configView = {

    initialize: function() {

        //config_menue
        $("#save_config").click(function()
        {
            logistikapp.servername = $("#t_server").val();
            logistikapp.server_port = $("#t_port").val();
            logistikapp.markt_id = $("#markt_id").val();
            if(typeof(localStorage) !== "undefined")
            {
                localStorage.servername = logistikapp.servername;
                localStorage.server_port = logistikapp.server_port;
                localStorage.markt_id = logistikapp.markt_id;

                $("#save_config").css("background","rgb(84, 191, 84)");
                setTimeout(function(){
                    $("#save_config").css("background","");
                },1500);

                //Establish Socket Connection
                serverController.initialize();
            }
            else
            {
                alert("Error: HTML5 Storage not supported");

            }

        });

        //open konfig
        $("#b_konfig").click(function()
        {

            if(typeof(localStorage) !== "undefined")
            {
                $("#t_server").val(localStorage.servername);
                $("#t_port").val(localStorage.server_port);
                $("#markt_id").val(localStorage.markt_id);

            }
            else
            {
                alert("Error: HTML5 Storage not supported");
            }




            $("#save_config").css("background","");

            $('#startScreen').hide();
            $('#konfi_menue').show();

        });

        //close konfig
        $("#config_zurueck").click(function()
        {
            $('#konfi_menue').hide();
            $('#startScreen').show();
        });




    }

}