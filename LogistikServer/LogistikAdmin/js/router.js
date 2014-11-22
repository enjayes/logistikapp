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

    init: function () {


        if(Router.getQueryVariable(location.hash, "jq")=="&ui-state=dialog"){
            Router.popupClosedStartUp = true;
        }

        crossroads.shouldTypecast = true;


         var handleRoute = function (state) {

             if(state==undefined)
                 state="";

             var tab = Router.getQueryVariable(state, "tab")||0;
             var lieferantId = Router.getQueryVariable(state, "l")||null;
             var jqueryvars = Router.getQueryVariable(state, "jq")||"";

             if (Router.popupClosedStartUp||jqueryvars != "&ui-state=dialog") {
                 Router.popupClosedStartUp=false;
                 var changeTab = true;

                 var lieferant = lieferantenController.getLieferantByID(lieferantId);
                 var waehleLieferant = function () {
                     if (lieferant) {
                         lieferantenController.aktuellerLieferant = lieferant;
                         lieferantenController.zeigeAktuellenLieferanten(true);
                     }
                     else
                         lieferantenController.keinenLieferantenZeigen();


                 };
                 if (tabsController.tabs[tab] == lieferantenTab) {
                     setTimeout(function () {
                         var changeActTab = function (destTab) {
                             lieferantenController.checkSaved(function () {
                                 waehleLieferant();
                                 tabsController.openTabWithoutClick(destTab);
                             });
                         };
                         changeActTab(tab);
                     }, 0);
                     changeTab = false;

                 }
                 else
                     waehleLieferant();


                 if(tab==0&&tabsController.aktuellerTab==null)   {
                     tabsController.openTab(0);
                     $("#page").css("opacity","1")

                 }

             else if (changeTab) {
                     tabsController.openTabWithoutClick(tab)
                 }



             }

         };


        crossroads.addRoute('/', handleRoute )
        crossroads.addRoute('#state={state}', handleRoute )


        $(window).on("hashchange", function (test) {
            if (!Router.popupClosed)
                crossroads.parse(location.hash);
            else
                Router.popupClosed = false;

        })


    },
     getQueryVariable :function (query, variable) {
        var vars = query.split('+');
        for (var i = 0; i < vars.length; i++) {
            var pair = vars[i].split('_');
            if (decodeURIComponent(pair[0]) == variable) {
                return decodeURIComponent(pair[1]);
            }
        }
    }
    ,
    pushState: function () {

        if(location.hash==""&& tabsController.aktuellerTab==0&&!lieferantenController.aktuellerLieferant)
          return;

        var hashCode = "state=tab_" + tabsController.aktuellerTab;

        if (lieferantenController.aktuellerLieferant)
            hashCode = hashCode + "+l_" + lieferantenController.aktuellerLieferant.id;

        hashCode = hashCode + "+jq_";

        location.hash = hashCode;

        //Set Tabs as marked
        setTimeout(function () {
            $("#tabs #" + tabsController.tabsAnchorName[tabsController.openTabAttempt]).addClass("ui-btn-active")
            $("#page").css("opacity","1")
        }, 0);


    }


}