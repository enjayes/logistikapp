/**
 * uebersichtTab
 *
 *
 *
 *
 * @date 19.11.14 - 22:19
 *
 */

uebersichtTab = {
    anchorName: "uebersichtTab",
    controller: uebersichtController,
    pieAufteilung:0,
    init: function () {
        uebersichtTab.pieAufteilung = new PieChart("#aufteilungChart",[]);
    },

    ready: function () {


    },

    open: function () {


    }


}