/*global define */
/*jslint vars: true, plusplus: true, devel: true, nomen: true, indent: 4, maxerr: 50 */
/*global $:false, jQuery:false, _:false, gapi:false*/

function generateChart(cycleItems) {
    'use strict';
    var items = cycleItems.items, i, item, chart;
    //console.log(items);
  
    if (!items || items.length === 0) {
        console.log('No Data Returned');
    } else {
        $(function () {
            $('#interactive').highcharts('StockChart', {
                rangeSelector : {
                    selected : 1,
                    inputEnabled: $('#interactive').width() > 480
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
            
            var mySeries = [];
            var arr = [];
            var cName;
            for (i = 0; i < items.length; i++) {
                item = items[i];
                if (item.costPer) {
                    console.log(item.landQty);
                }
                arr[0] = item.startDate;
                cName = item.cropName;
                mySeries.push([parseInt(item.startDate, 10), item.totalSpent]);
            }
            var sortedData = _.sortBy(mySeries, function (arr) {
                    return arr[0];
                });
            chart = $('#interactive').highcharts('StockChart');
            chart.series[0].setData(sortedData);
            chart.series[0].name = cName;
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
        queryData = gapi.client.cycleendpoint.getMatchingCycles({"cropName": cropValue, "start": start, "end": end, "area": areaValue});
    queryData.execute(function (resp) { generateChart(resp); });
}
