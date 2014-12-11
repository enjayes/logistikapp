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
    init: function () {


    },

    ready: function () {


        var pieData = [
            {
                value: 12,
                color: "#F7464A",
                highlight: "#FF5A5E",
                label: "Besuch"
            },
            {
                value: 5,
                color: "#46BFBD",
                highlight: "#5AD3D1",
                label: "Bestellung"
            },
            {
                value:7,
                color: "#FDB45C",
                highlight: "#FFC870",
                label: "Verräumung"
            },
            {
                value:0,
                color: "#F0045C",
                highlight: "#FFC870",
                label: "Austausch"
            }
        ];
        var ctx = $("#aufteilung-chart-area")[0].getContext("2d");
        aufteilungPie = new Chart(ctx).Pie(pieData, {
            percentageInnerCutout: 0,
            animateScale: true,
            animateRotate: true,
            animationEasing: "easeOutBounce",
            //Number - Amount of animation steps
            animationSteps : 120

        });

        var legendHolder = $("#aufteilung-legend").html(aufteilungPie.generateLegend())

        legendHolder.find("li").each(function(index){
            $(this).on('mouseover', function(){
                var activeSegment = aufteilungPie.segments[index];
                activeSegment.save();
                activeSegment.fillColor = activeSegment.highlightColor;
                aufteilungPie.showTooltip([activeSegment]);
                activeSegment.restore();
            })

            $(this).on('mouseout', function(){
                aufteilungPie.draw();

            })
        })


    },

    open: function () {


    }


}