var element;
var xaxisNum = [];
window.wellLogsPlotly = function(mainObject, layoutName, units, element) {

	// mainObject.put("seriesData", datasetArray);
	// mainObject.put("yRange", yRange);
	// mainObject.put("xRange", xRange);
	// mainObject.put("yaxisFlag", yaxisFlag);
	// mainObject.put("seriesNameArray", seriesNameArray);

	element = element;
	const numPlots = mainObject.length;
	var axisArray = [];
	xaxisNum = [];
	axisArray.push('xy');
	for (var k = 2; k <= numPlots; k++) {
		axisArray.push('x' + k + 'y');
	}

	var data = [];
	var logTitles = [];
	for (var i = 0; i < numPlots; i++) {
		var xDat = [];
		var yDat = [];

		for (j = 0; j < mainObject[i].seriesData.length; j++) {
			xDat.push(mainObject[i].seriesData[j].x);
			yDat.push(mainObject[i].seriesData[j].y);
		}
		var axisDes = i + 1;
		var logname = mainObject[i].seriesNameArray;
		var title = mainObject[i].nameArray;
		xaxisNum[logname] = axisDes;
		console.log(logname);
		logTitles.push(title);
		var wellData = {
			x : xDat,
			y : yDat,
			xaxis : 'x' + axisDes,
			yaxis : 'y',
			type : 'scatter',
			mode : 'lines',
			line : {
				width : 1,
				cauto : true,
				color : mainObject[i].color,
			},
			name : logname
		};
		data.push(wellData);
	}

	var colorButtons = [ {
		method : 'restyle',
		args : [ {
			'line.color' : 'red'
		} ],
		label : 'red'
	}, {
		method : 'restyle',
		args : [ 'line.color', 'blue' ],
		label : 'blue'
	}, {
		method : 'restyle',
		args : [ 'line.color', 'green' ],
		label : 'green'
	} ];
	// dropMenus.push({
	// x : 0.8,
	// y : 0.9,
	// yanchor : 'top',
	// buttons : colorButtons
	// });

	// dropMenus = [{
	// x: 0.8,
	// y: 1.0,
	// yanchor: 'top',
	// buttons: colorButtons
	// }];

	var xAxisStuff = [];
	var firstX = {

	};

	var layout = {
		// title: "Drilling Report",
		 autoresize : true,
		// font: { size: 14 },
		paper_bgcolor : "rgb(255, 255, 255)",
		autosize : true,
		showlegend : true,
		hovermode: 'y',
		spikedistance: -1,
		legend : {
			orientation : 'h',
			x : 0,
			y : -0.1,
			xanchor : 'left',
			font : {
				family : 'Arial, sans-serif',
				size : 10
			},
			bordercolor : '#A9A9A9',
			borderwidth : 0
		},
		yaxis : {

			title : 'Measured Depth (' + units + ')',
			zeroline : false,
			titlefont : {
				family : 'Arial, sans-serif',
				size : 18,
				color : 'green'
			},
			autorange : 'reversed',
			showticklabels : true,
			scrollZoom : true,
			tickfont : {
				family : 'Old Standard TT, serif',
				size : 14,
				color : 'black'
			},
			showgrid: true,
			showspikes: true,
			spikemode: 'across',
			spikesnap: 'cursor',
			spikecolor: '#48278F',
			spikethickness: 1
		},
		grid : {
			rows : 1,
			columns : numPlots,
			subplots : [ axisArray ],
			xgap : 0
		}
	};

	layout['xaxis'] = {
		title : {
			text : logTitles[0],
			font : {
				family : 'Arial, sans-serif',
				size : 14,
				color : 'green'
			},
			standoff : 0
		},
		showgrid: true,
		zeroline : false,
		type : 'linear',
		side : 'top'
	}
	for (var j = 1; j < logTitles.length; j++) {
		var num = j + 1;
		layout['xaxis' + num.toString()] = {
			title : {
				text : logTitles[j],
				font : {
					family : 'Arial, sans-serif',
					size : 14,
					color : 'green'
				},
				standoff : 0
			},
			showgrid: true,
			zeroline : false,
			type : 'linear',
			side : 'top'
		}
	}

	var config = {
		showEditInChartStudio : true,
		plotlyServerURL : "https://chart-studio.plotly.com",
		modeBarButtonsToRemove : [ 'toImage', 'select2d', 'lasso2d',
				'toggleSpikelines', 'hoverCompareCartesian' ],
		displaylogo : false,
		modeBarButtonsToAdd : [ {
			name : 'Log setting',
			icon : Plotly.Icons.pencil,
			direction : 'up',
			click : function(gd) {
				element.$server.settingWindow(logTitles)
			}
		}, {
			name : 'Show Data',
			icon : Plotly.Icons.pencil,
			direction : 'up',
			click : function(gd) {
				element.$server.showDataWindow(logTitles)
			}
		} ],
		responsive : true
	};
	var container = document.getElementById(layoutName);
	// Plotly.react(container, data, layout,{displayModeBar: false});

	Plotly.react(container, data, layout, config);

	var cnt = 0;
	container.on('plotly_hover', function(data){

	    var infotext = data.points.map(function(d){
	    	var update = {
	    			 value: d.x // assigns value that will update the indicator
	    		};
//	    Plotly.restyle(container, update, 2); // This updates plot with index 2 which in this case is the indicator plot
	      //return (d.data.name+': x= '+d.x+', y= '+d.y.toPrecision(3)); // this isn't really needed 
	    return (d.data.name+': x= '+d.x+', y= '+d.y.toPrecision(3));
	    });
	    console.log(infotext);
	})
	 .on('plotly_unhover', function(data){
//	    hoverInfo.innerHTML = '';
	});

}

window.updateLog = function(layoutName, logname, selectColor) {

	var container = document.getElementById(layoutName);

	var dataRetrievedLater = container.data;

	for (var i = 0; i < dataRetrievedLater.length; i++) {
		var data = dataRetrievedLater[i];
		if (data.name == logname) {
			// data.line.color = selectColor;
			Plotly.restyle(container, {
				'line.color' : [ selectColor ]
			}, [ i ])
		}
	}
	// Plotly.restyle(container, dataRetrievedLater);
}

window.updateLogRT = function(dataObject, logname, layoutName) {
	var container = document.getElementById(layoutName);
	var plotNum = 'x' + xaxisNum[logname];
	if (container !== null) {
		var dataRetrievedLater = container.data;

		var plotlayout = container.layout;
		var addFlag = true;
		for (var i = 0; i < dataRetrievedLater.length; i++) {
			var data = dataRetrievedLater[i];
			if (data.xaxis == plotNum) {
				if (data.name == logname) {
					addFlag = false;
					var xdata = data.x;
					var ydata = data.y;
					var xyData = dataObject.data;
					for (var j = 0; j < xyData.length; j++) {
						xdata.push(xyData[j].x);
						ydata.push(xyData[j].y)
					}
					data.x = xdata;
					data.y = ydata;
					break;
				} else {
					addFlag = true;
				}

			}
		}

		if (addFlag) {
			var xyData = dataObject.data;
			var xdata = [];
			var ydata = [];
			for (var j = 0; j < xyData.length; j++) {
				xdata.push(xyData[j].x);
				ydata.push(xyData[j].y)
			}
			var wellData = {
				x : xdata,
				y : ydata,
				xaxis : plotNum,
				yaxis : 'y',
				type : 'scatter',
				mode : 'lines',
				line : {
					width : 1,
					cauto : true,
					color : dataObject.color,

				},
				name : logname

			};
			dataRetrievedLater.push(wellData);
		}

		Plotly.update(container, dataRetrievedLater, plotlayout)
		// console.log(dataRetrievedLater);
	}
}

window.getLogData_plotly = function(layoutName, logname, windowElemnt) {
	var container = document.getElementById(layoutName);

	var dataRetrievedLater = container.data;

	var x;
	var y;
	for (var i = 0; i < dataRetrievedLater.length; i++) {
		var data = dataRetrievedLater[i];
		if (data.name == logname) {
			x = data.x;
			y = data.y;
		}
	}

	windowElemnt.$server.updateGrid(x, y);
}

window.reSize = function(layoutName) {
	var container = document.getElementById(layoutName);
	Plotly.Plots.resize(container);
}