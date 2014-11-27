/**
 * searchWidget.
 *
 * >>Description<<
 *
 * @author Manfred
 * @date 27.11.14 - 23:02
 * @copyright munichDev UG
 */



var searchWidget = function (domObject,topMargin) {
    var that = this;
    this.domObject = domObject;

    if(!topMargin)
        topMargin = 55;

    this.gjghjgj = function () {


    }


    //Construct

    $(domObject).append(

        '<form>' +

            '<input class="searchWidget-filterable" data-type="search">' +
            '</form>' +
            '<div' +
            'class="ui-controlgroup ui-controlgroup-vertical ui-corner-all"' +
            'data-role="controlgroup"' +
            'data-filter="true"' +
            'data-input=".searchWidget-filterable"' +
            'data-filter-reveal="true"' +
            'data-enhanced="true">' +
            '<div class="searchWidget-resultlist  ui-controlgroup-controls" style="top:"'+top+'px>' +
            '<a href="index.html" class="ui-screen-hidden" data-role="button">General</a>' +
            '<a href="settings.html" class="ui-screen-hidden" data-role="button">Settings</a>' +
            '<a href="advanced.html" class="ui-screen-hidden" data-role="button">Advanced</a>' +
            '<a href="notifications.html" class="ui-screen-hidden" data-role="button">Notifications</a>' +
            '</div>' +
            '</div>'


    )//.filterable('refresh');




    var updateSize = function () {
       // $(that.domObject+" .searchWidget-resultlist").css("width", $(that.domObject).width());
    }
    //Window Resize Handling
    $(window).resize(updateSize);
    updateSize();



}