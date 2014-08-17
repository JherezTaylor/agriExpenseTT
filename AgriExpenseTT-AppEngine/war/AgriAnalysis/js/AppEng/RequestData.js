/*global define */
/*jslint vars: true, plusplus: true, devel: true, nomen: true, indent: 4, maxerr: 50 */
/*global $:false, jQuery:false, _:false, gapi:false*/

$(function ($) {
    'use strict';

    $(".knob").knob({
        change: function (value) {
            //console.log("change : " + value);
        },
        release: function (value) {
            //console.log(this.$.attr('value'));
            console.log("release : " + value);
        },
        cancel: function () {
            console.log("cancel : ", this);
        },

        format: function (value) {
            return value + '%';
        },

        draw: function () {

            // "tron" case
            if (this.$.data('skin') === 'tron') {

                this.cursorExt = 0.3;

                var a = this.arc(this.cv), // Arc
                    pa, // Previous arc
                    r = 1;

                this.g.lineWidth = this.lineWidth;

                if (this.o.displayPrevious) {
                    pa = this.arc(this.v);
                    this.g.beginPath();
                    this.g.strokeStyle = this.pColor;
                    this.g.arc(this.xy, this.xy, this.radius - this.lineWidth, pa.s, pa.e, pa.d);
                    this.g.stroke();
                }

                this.g.beginPath();
                this.g.strokeStyle = r ? this.o.fgColor : this.fgColor;
                this.g.arc(this.xy, this.xy, this.radius - this.lineWidth, a.s, a.e, a.d);
                this.g.stroke();

                this.g.lineWidth = 2;
                this.g.beginPath();
                this.g.strokeStyle = this.o.fgColor;
                this.g.arc(this.xy, this.xy, this.radius - this.lineWidth + 1 + this.lineWidth * 2 / 3, 0, 2 * Math.PI, false);
                this.g.stroke();

                return false;
            }
        }
    });

    // Example of infinite knob, iPod click wheel
    var v, up = 0,
        down = 0,
        i = 0,
        $idir = $("div.idir"),
        $ival = $("div.ival"),
        incr = function () {
            i++;
            $idir.show().html("+").fadeOut();
            $ival.html(i);
        },
        decr = function () {
            i--;
            $idir.show().html("-").fadeOut();
            $ival.html(i);
        };
    $("input.infinite").knob({
        min: 0,
        max: 20,
        stopper: false,
        change: function () {
            if (v > this.cv) {
                if (up) {
                    decr();
                    up = 0;
                } else {
                    up = 1;
                    down = 0;
                }
            } else {
                if (v < this.cv) {
                    if (down) {
                        incr();
                        down = 0;
                    } else {
                        down = 1;
                        up = 0;
                    }
                }
            }
            v = this.cv;
        }
    });
});

function generateChart(cycleItems) {
    'use strict';
    var items = cycleItems.items,
        i,
        j,
        item,
        item2,
        chart;

    if (!items || items.length === 0) {
        console.log('No Data Returned');
    } else {

        var prodSeries = [];
        var sortPivot = []; // To sort the entries according to date. Passed into lodash sortBy function
        var cropName;
        var sum;
        var outputVolume = []; // Total volume of crops produced across all counties
        var outputCost = []; // Total cost of production across all counties
        var binaryCounter = []; // To sum the volume/cost of production produced by each county

        //Parse data from object
        for (i = 0; i < items.length; i++) {
            item = items[i];
            sortPivot[0] = item.startDate;
            cropName = item.cropName;
            prodSeries.push([parseInt(item.startDate, 10), item.totalSpent]);
        }

        // Initalize counter to zero in order to track which items have been processed already

        for (i = 0; i < items.length; i++) {
            binaryCounter[i] = 0;
        }
        // Get the volume of crops produced by each county
        for (i = 0; i < items.length; i++) {
            if (binaryCounter[i] === 0) {
                item = items[i];
                sum = item.harvestAmt;
                for (j = i + 1; j < items.length - 1; j++) {
                    item2 = items[j];
                    if (binaryCounter[j] === 0) {
                        if (item.county === item2.county) {
                            sum += item2.harvestAmt;
                            binaryCounter[j] = 1;
                        }
                    }
                }
                outputVolume.push([item.county, sum]);
                sum = 0;
            }
        }


        for (i = 0; i < items.length; i++) {
            binaryCounter[i] = 0;
        }
        // Get the total costs of production for each county
        for (i = 0; i < items.length; i++) {
            if (binaryCounter[i] === 0) {
                item = items[i];
                sum = item.totalSpent;
                for (j = i + 1; j < items.length - 1; j++) {
                    item2 = items[j];
                    if (binaryCounter[j] === 0) {
                        if (item.county === item2.county) {
                            sum += item2.totalSpent;
                            binaryCounter[j] = 1;
                        }
                    }
                }
                outputCost.push([item.county, sum]);
                sum = 0;
            }
        }
        /*    var targetObj = {};
    for (var i = 0; i < items.length; i++) {
        if (!targetObj.hasOwnProperty(items[i].county)) {
            targetObj[items[i].county] = 0;
        }
        targetObj[items[i].county] += parseFloat(items[i].harvestAmt);
    }
	 console.log(targetObj); 
     
     */
        $(function () {
            $('#stockContainer').highcharts('StockChart', {
                loading: {
                    hideDuration: 1000,
                    showDuration: 1000
                },
                rangeSelector: {
                    selected: 1,
                    inputEnabled: $('#stockContainer').width() > 480
                },

                tooltip: {
                    pointFormat: '<span style="color:{series.color}">{series.name}</span>: <b>${point.y}</b><br/>',
                    valueDecimals: 2
                },
                colors: ['#4D90FE', '#1BC123'],
                title: {
                    text: 'Cost of Production'
                },

                chart: {},
                series: [{
                    data: []
                }]
            });

            var sortedData = _.sortBy(prodSeries, function (sortPivot) {
                return sortPivot[0];
            });
            chart = $('#stockContainer').highcharts('StockChart');
            chart.series[0].setData(sortedData);
            chart.series[0].name = cropName;
            //console.log(sortedData);
        });

        // Generate pie chart by Volume
        $(function () {
            $('#pieContainer').highcharts({
                chart: {
                    plotBackgroundColor: null,
                    plotBorderWidth: 1, //null,
                    plotShadow: false
                },
                title: {
                    text: 'Production breakdown by volume'
                },
                tooltip: {
                    pointFormat: '{series.name}: <b> {point.y} Kg</b>'
                },
                plotOptions: {
                    pie: {
                        allowPointSelect: true,
                        cursor: 'pointer',
                        dataLabels: {
                            enabled: true,
                            format: '<b>{point.name} </b>: {point.percentage:.1f} % '
                        }
                    }
                },
                series: [{
                    type: 'pie',
                    name: 'Production Output',
                    data: []
                }]
            });
            chart = $('#pieContainer').highcharts();
            chart.series[0].setData(outputVolume);
        });

        // Generate pie chart by Volume
        $(function () {
            $('#pieContainer2').highcharts({
                chart: {
                    plotBackgroundColor: null,
                    plotBorderWidth: 1, //null,
                    plotShadow: false
                },
                title: {
                    text: 'Production breakdown by total spent'
                },
                tooltip: {
                    pointFormat: '{series.name}: <b> ${point.y}</b>'
                },
                plotOptions: {
                    pie: {
                        allowPointSelect: true,
                        cursor: 'pointer',
                        dataLabels: {
                            enabled: true,
                            format: '<b>{point.name} </b>: {point.percentage:.1f} % '
                        }
                    }
                },
                series: [{
                    type: 'pie',
                    name: 'Production Costs',
                    data: []
                }]
            });
            chart = $('#pieContainer2').highcharts();
            chart.series[0].setData(outputCost);
        });
    }
}

function fetchData() {
    'use strict';
    var cropValue = $("#cropSelection").val(),
        inputRange = $("#rangeSel").data('slider').getValue(),
        start = parseFloat(inputRange[0]),
        end = parseFloat(inputRange[1]),
        areaValue = $("#areaSelection").val(),
        queryData = gapi.client.cycleendpoint.getMatchingCycles({
            "cropName": cropValue,
            "start": start,
            "end": end,
            "area": areaValue
        });
    queryData.execute(function (resp) {
        generateChart(resp);
    });
}