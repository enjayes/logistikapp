/**
 * Created by Norbert on 14.12.2014.
 */




/**
 * configView
 *
 *
 * @author
 * @date 11.12.14 - 17:39
 * @copyright
 */


var templateController = {

    set: function(list) {

        console.dir("TEMPLATE LISTE:");
        console.dir(list);
        console.dir(list[0].markt_id);
        var html = '<ul data-role="listview">';


        html = html+ '<li><a href="#">Zuletzt verwendetes Formular</a></li>';


        for (index = 1; index < list.length; ++index) {
            html = html+ '<li><a href="#">'+list[0].template_name+'</a></li>';
        }


        html = html+ '</ul>';

        $("#templateList").html(html);


    },





}



