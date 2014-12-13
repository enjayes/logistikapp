/**
 * multipleSelection
 *
 *
 * @author
 * @date 13.12.14 - 16:39
 * @copyright
 */


var MultipleSelectionWidget = function (domObject) {

    var that = this;
    this.internalName = "multipleSelection" + Date.now() + "x" + ((Math.random() * 1000000.0) + "").replace(".", "");
    this.domObject = domObject;
    this.data = null;

    var html = '<fieldset id="' + this.internalName + '" class="multipleselections" data-role="controlgroup" data-mini="true" data-type="horizontal" style="margin:0px"></fieldset>';
    $(that.domObject).append(html);

    $(that.domObject + " #" + this.internalName).controlgroup();

    this.domObjectInner = $(domObject + " #" + this.internalName + " .ui-controlgroup-controls");


    this.setData = function (data, selectAll) {

        that.data = data;

        var html = "";
        for (var i = 0; i < data.length; i++) {
            var name = that.internalName + 'x' + i;
            html += '<input type="checkbox" name="" id="' + name + '">' +
                '<label for="' + name + '">' + data[i].name + '</label>'
        }
        $(that.domObjectInner).append(html);

        var checkboxradios = that.domObjectInner.find('input').checkboxradio();

        if (selectAll) {
            checkboxradios.prop("checked", true).checkboxradio("refresh");
        }

        $(that.domObject + " #" + this.internalName).controlgroup("refresh");

    };

}



