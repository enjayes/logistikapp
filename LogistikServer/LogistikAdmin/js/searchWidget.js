/**
 * searchWidget.
 *
 * >>Description<<
 *
 * @author Manfred
 * @date 27.11.14 - 23:02
 * @copyright munichDev UG
 */


var SearchWidget = function (domObject, searchPlaceHolder, topMargin,multiSelect, clickedItemCallback, renderItemFunction) {

    searchPlaceHolder = !searchPlaceHolder ? "" : searchPlaceHolder;
    topMargin = !topMargin ? 22 : topMargin;

    var that = this;
    this.domObject = domObject;
    this.filterableDomObject = domObject + " .searchWidget-resultlist";
    this.searchPlaceHolder = searchPlaceHolder;

    this.list = [];
    this.showAll = true;
    this.DontDismissByBlur = false;
    this.DontDismiss = false;
    this.clickedItemCallback = clickedItemCallback;

    this.selectedItems = [];
    this.multiSelect = multiSelect;


    // Init Filterable
    $.mobile.document.one("filterablecreate", that.filterableDomObject, function () {

        $(that.filterableDomObject).filterable("option", "filterCallback", function (index, searchValue) {

            //Alle anzeigen
            if (that.showAll)
                return false;

            //Kein Term eingebenen
            if (searchValue.trim() == "")
                return false;

            //Gefunden
            return !($(this).text().toLowerCase().search(searchValue.toLowerCase()) != -1);

        });

    });


    //Build Filterable
    $(domObject).append(
        '<form>' +
            '<input class="searchWidget-filterable" placeholder="' + searchPlaceHolder + '" data-type="search">' +
            ' </form>' +
            '<ul class="searchWidget-resultlist" style="top:' + topMargin + 'px;" data-role="listview" data-inset="true" data-filter="true" data-input="' + that.domObject + ' .searchWidget-filterable"></ul>'

    );

    //Bind Events and Logic

    setTimeout(function () {

        //Zeige alle Lieferanten bei anklicken des Filters
        $(that.domObject + " input").on("focus",function () {
            $(that.filterableDomObject).show();

            if (!that.showAll)
                that.toggleShowAllItems();

        }).on("keydown", function (e) {

                if (e.which == 13) {

                    var obj = $(that.filterableDomObject + " li").filter(":visible");
                    if (obj.length > 1)
                        $(obj[0]).click();
                    $(that.domObject + " input").blur();
                }
            });


        //Bei Eingabe alle anzeigen deaktivieren
        $(that.domObject + " input").on("input change",function () {
            if (that.showAll)
                that.toggleShowAllItems();
        }).click(function (event) {
                event.stopPropagation()
            })


        //Dismiss Selection bei Click elsewhere
        var dismissFilter = function () {
            if (!that.DontDismiss && !that.DontDismissByBlur) {
                that.DontDismiss = true;
                setTimeout(function () {
                    that.DontDismiss = false;
                }, 400);

                if (that.showAll)
                    that.toggleShowAllItems();
                else
                    $(that.filterableDomObject).filterable("refresh");
                $(that.filterableDomObject).hide();
            }
        }
        $("#page").click(function () {
            dismissFilter();
        })

        $(that.domObject + " .ui-input-clear").click(function () {
            that.DontDismissByBlur = true;
        })

        $(that.domObject + " input").on("blur", function () {
            setTimeout(function () {

                dismissFilter();
            }, 300)
        })

        //Filterable Methods
        $(that.filterableDomObject).filterable({
            filter: function (event, ui) {

                //Show Items if show all and focus
                if ($(that.domObject + " input").is(":focus") && $(that.domObject + " input").val().trim() == "") {
                    $(that.filterableDomObject + " li").removeClass("ui-screen-hidden");

                }


                if (that.list.length == 0 || (!that.showAll && $(that.filterableDomObject + " li.ui-screen-hidden").length == $(that.filterableDomObject + " li").length)) {
                    $(that.filterableDomObject).hide();

                } else if (that.showAll || $(that.domObject + " input").is(":focus")) {
                    $(that.filterableDomObject).show();

                }

            }
        });

        //Render List
        that.renderList();


        //Trigger Filter at start
        $(that.domObject + " input").trigger("change");

    }, 0)


    this.toggleShowAllItems = function () {

        that.showAll = !that.showAll;
        if (that.showAll) {
            that.DontDismissByBlur = true;
            setTimeout(function () {
                that.DontDismissByBlur = false;
            }, 400);

            //$("#allelieferantenanzeigen").val('Verstecken').button('refresh');


        } else {

            //$("#allelieferantenanzeigen").val('Alle anzeigen').button('refresh');

        }
        $(that.filterableDomObject).filterable("refresh");
    };


    this.setList = function(list){

         that.list = list;
         that.renderList();
    }


    this.getSelectedItems = function(){
        return that.selectedItems;
    }

    this.itemSelected = function (item) {

        for(var i=0;i<that.selectedItems.length;i++){   //CHANGE FOR DIFFERENT COMPARISIONS
            if(that.selectedItems[i].id==item.id){
                return true;
            }
        }
        return false;
    }


    this.deselectedItem = function (item) {

        for(var i=0;i<that.selectedItems.length;i++){   //CHANGE FOR DIFFERENT COMPARISIONS
            if(that.selectedItems[i].id==item.id){
                that.selectedItems.splice(i,1)
                return true;
            }
        }
        return false;
    }

    this.renderList = function () {

        var searchListDom = $(domObject + " .searchWidget-resultlist");
        searchListDom.html("");


        for (var i = 0; i < that.list.length; i++) {
            var item = that.list[i];


            var callbackFactory = function (actItem) {
                return function () {
                    if(that.itemSelected(actItem)){
                        if(that.multiSelect){
                            that.deselectedItem(actItem)
                        }
                    }else {
                        if(!that.multiSelect){
                            that.selectedItems = [];
                        }
                        that.selectedItems.push(actItem);
                    }
                    that.renderList();
                    that.clickedItemCallback(actItem);
                }
            }

            if(that.itemSelected(item))
               var selected = "selected"
            else
                selected = ""

            if (renderItemFunction)
                var html = renderItemFunction(item);
            else
                html = "<li class='"+selected+"'><a>" + item.name + "</a></li>"

            searchListDom.append($(html).click(callbackFactory(item)));


        }
        searchListDom.listview("refresh");

    }

}