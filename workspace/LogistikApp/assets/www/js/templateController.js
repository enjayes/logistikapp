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

    templateList: null,

    set: function(list) {
        this.templateList = list;
        console.dir("TEMPLATE LISTE:");
        console.dir(list);
        var html = '<ul data-role="listview" id = "templateListview">';



        html = html+ '<li data-icon="false" ><a onClick="templateController.load(0)"  href="#">Zuletzt verwendetes Formular</a></li>';


        for (index = 1; index < list.length; ++index) {
            html = html+ '<li data-icon="false"> <a onClick="templateController.load('+index+')" href="#">'+list[index].template_name+'</a></li>';
        }


        html = html+ '</ul>';

        $("#templateList").html(html);

        $("#templateListview").listview();

    },

    load: function(index){
        if(index < templateList.length) {
            clientView.setJob(templateList[index]);
        }

        $( "#popupVorlagen" ).popup("close") ;
    }





}



