/**
 * lieferantenTab.
 *
 * >>Description<<
 *
 * @author Manfred
 * @date 19.11.14 - 22:13
 * @copyright munichDev UG
 */


lieferantenTab = {
    anchorName: "lieferantenTab",
    controller: lieferantenController,
    searchWidget: null,
    init: function () {

        this.searchWidget = new SearchWidget("#searchInfoLieferanten", "Suche nach Lieferanten...", 5, false, function (lieferant) {
                lieferantenController.waehleLieferant(lieferant);
            }, function (lieferant, classes) {
                return "<li class='" + classes + "'><a>" + lieferantenController.getLieferantFullName(lieferant) + "</a></li>";
            }, function (visible,top,height) {
                    $("#pagecontent").css("min-height", visible?top+height:"");

            }
        )

    },

    ready: function () {


    },
    open: function () {


    }

}