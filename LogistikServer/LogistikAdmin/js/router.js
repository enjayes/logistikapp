/**
 * router.
 *
 * >>Description<<
 *
 * @author Manfred
 * @date 22.11.14 - 00:27
 * @copyright munichDev UG
 */




Router = {
    routeHandler: function(eventType, matchObj, ui, page, evt){

         alert(page)


        // your code here
    },
    init:function(){

        crossroads.addRoute('#tab={tab}', function(tab){
            console.log(tab);
            tabsController.openTabWithoutClick(tab)

        });

        $(window).on("hashchange",function(test){
             console.log(location.hash)
            crossroads.parse(location.hash);

        })

        setTimeout(function(){


        }, 5000);

        crossroads.parse(location.hash);
    },


    pushState:function(){
        location.hash = "tab="+tabsController.aktuellerTab;

        setTimeout(function () {
            $("#tabs #"+tabsController.tabsAnchorName[tabsController.openTabAttempt]).addClass("ui-btn-active")

        },0)

        //history.pushState(stateObj, "page 2", "bar.html");



    }









}