/**
 * tabsController.
 *
 * >>Description<<
 *
 * @author Manfred
 * @date 19.11.14 - 22:03
 * @copyright munichDev UG
 */


tabsConstroller = {
    tabs: [],

    //Init Controller
    init: function () {
        this.tabs[0] = uebersichtTab;
        this.tabs[1] = termineTab;
        this.tabs[2] = lieferantenTab;

        //Init Tabs
        for (var i = 0; i < 3; i++) {
            this.tabs[i].init();
        }
    },


    //Init after UI Loaded
    ready: function () {
        //Init Tabs
        for (var i = 0; i < 3; i++) {
            this.tabs[i].ready();
        }
    },


    // Open Tab
    openTab: function (index) {

        $(".tabinhalt").css("opacity", "0")

        setTimeout(function () {

            tabsConstroller.tabs[index].open();

            uiController.updateSize();
            //Inhalt anzeigen nach Rendern
            $(".tabinhalt").css("opacity", "1")
        }, 0)
    }

}


