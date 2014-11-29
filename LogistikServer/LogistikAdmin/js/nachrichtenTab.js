/**
 * nachrichtenTab.
 *
 * >>Description<<
 *
 * @author Manfred
 * @date 27.11.14 - 22:47
 * @copyright munichDev UG
 */



nachrichtenTab = {
    anchorName: "nachrichtenTab",
    controller: nachrichtenController,

    searchWidget: null,
    init: function () {
        var that = this;

        var selectedList = $("#selectedMessageLieferanten");
        var selectedItemsDefaultHtml = selectedList.html();
        selectedList.click(function () {
            $("#searchMessageLieferanten input").focus();
        })


        this.searchWidget = new SearchWidget("#searchMessageLieferanten", "Suche nach Lieferanten...", null, true, function () {

                var renderSelectionList = function () {

                    var selectedItems = that.searchWidget.getSelectedItems();

                    var selectedList = $("#selectedMessageLieferanten");

                    if (selectedItems.length == 0)
                        selectedList.html(selectedItemsDefaultHtml);
                    else {
                        selectedList.html("");
                        for (var i = 0; i < selectedItems.length; i++) {   //CHANGE FOR DIFFERENT COMPARISIONS

                            var append = function (item) {
                                selectedList.append($("<div title='" + lieferantenController.getLieferantFullName(item) + "'class='selectedLieferantButton ui-btn'>" + item.name + "</div>").click(function () {
                                    event.stopPropagation();


                                    lieferantenController.aktuellerLieferant = $.extend(true, {}, item);
                                    tabsController.openTabWithoutClick(3);
                                    lieferantenController.zeigeAktuellenLieferanten();


                                }).append($("<div class='selectedLieferantButtonRemove ui-btn ui-btn-a ui-icon-delete ui-btn-icon-notext ui-btn-inline ui-shadow ui-corner-all ui-mini'></div>").click(function () {
                                        event.stopPropagation();
                                        that.searchWidget.deselectedItem(item);
                                        that.searchWidget.renderList();
                                        renderSelectionList();
                                    })));
                            }
                            append(selectedItems[i]);


                        }
                    }


                }
                renderSelectionList();
            },
            function (lieferant, classes) {
                return "<li class='" + classes + "'><a>" + lieferantenController.getLieferantFullName(lieferant) + "</a></li>";
            },
            function (visible,top,height) {
                $("#pagecontent").css("min-height", visible?top+height:"");
            }
        );


        CKEDITOR.replace('messageLieferantenCKEditor');


    },

    ready: function () {


    },

    open: function () {


    }


}