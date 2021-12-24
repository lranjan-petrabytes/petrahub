window.drillReportPlotly = function(mainObject, layoutName) {

	// logs.put("Date", dateArray);
	// logs.put("MD", mdArray);
	// logs.put("MW", datVal);
	// logs.put("Data Type", datType);
	// logs.put("Log", logName)

	var logPos = [];
	var i;
	logPos.push(0);
	var curLog = mainObject.Log[0];
	for (i = 1; i < mainObject.Log.length; i++) {
		if (mainObject.Log[i] != curLog) {
			logPos.push(i);
			curLog = mainObject.Log[i];
		}
	}

	
	
	var data = [];
	for (i = 0; i < logPos.length; i++) {
		var startInd = logPos[i];
		var endInd = logPos[i + 1];
		var xDat = mainObject.Date.slice(startInd, endInd);
		var yDatMW = mainObject.MW.slice(startInd, endInd);
		var yDatMD = mainObject.MD.slice(startInd, endInd);
		var logName = mainObject.Log[logPos[i]];
		var wellData1 = {
			x : xDat,
			y : yDatMW,
			xaxis : 'x',
			yaxis : 'y2',
			type : 'scatter',
			mode : 'markers',
			name : logName + " MW",
			marker: {symbol: i+100}
		};
		
		var wellData2 = {
			x : xDat,
			y : yDatMD,
			xaxis : 'x',
			yaxis : 'y1',
			type : 'scatter',
			mode : 'markers',
			name : logName + " MD",
			marker: {symbol: i}
		};
		
		if (logName == "MW Drilling Depth")
		{
			wellData2 = {
				x : xDat,
				y : yDatMD,
				xaxis : 'x',
				yaxis : 'y1',
				type : 'scatter',
				mode : 'lines+markers',
				name : logName + " MD"
			};
		}
		
		var logButtons = [];
		logButtons.push ({
			method: 'restyle',
	        args: [{'marker.color': 'green', 'line.color': 'magenta'},[6]],
			label:  logName + " MW"
		});
		
		
		logButtons.push ({
			
			method: 'restyle',
	        args: [{'marker.color': 'yellow', 'line.color': 'orange'},[7]],
			label:  logName + " MD"
		});

		data.push(wellData1, wellData2);
	}

	var colorButtons = [{
        method: 'restyle',
        args: [{'line.color': 'red'}] ,
        label: 'red'
    }, {
        method: 'restyle',
        args: ['line.color', 'blue'],
        label: 'blue'
    }, {
        method: 'restyle',
        args: ['line.color', 'green'],
        label: 'green'
    }];
	
	var dropMenus =[];
	dropMenus.push({
		x: 0.8,
        y: 0.9,
        yanchor: 'top',
        buttons: colorButtons
    });
	dropMenus.push({
		x: 0.8,
        y: 1.0,
        yanchor: 'top',
        buttons: logButtons
    });
	
	
//	dropMenus = [{
//		x: 0.8,
//        y: 1.0,
//        yanchor: 'top',
//        buttons: colorButtons
//    }];

	var layout = {
		// title: "Drilling Report",
		autoresize : true,
		// font: { size: 14 },
		paper_bgcolor : "rgb(255, 255, 255)",
		autosize : true,
		showlegend : true,
		legend : {
			orientation : 'h',
			x : 0,
			y : 0,
			xanchor : 'left',
			font : {
				family : 'Arial, sans-serif',
				size : 10
			}
		},
		updatemenus: dropMenus,
		xaxis : {
			title : {
				text : 'Date',
				font : {
					family : 'Arial, sans-serif',
					size : 18,
					color : 'green'
				},
				standoff : 0
			},
			type : 'date',
			zeroline : false,
			side : 'top'
		},
		yaxis1 : {

			title : 'Measured Depth',
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
			}
		},
		yaxis2 : {

			title : 'Mud Weight',
			zeroline : false,
			titlefont : {
				family : 'Arial, sans-serif',
				size : 18,
				color : 'blue'
			},
			overlaying : 'y',
			side : 'right',
			showticklabels : true,
			tickfont : {
				family : 'Old Standard TT, serif',
				size : 14,
				color : 'blue'
			}
		}
	};

	var config = {
		showEditInChartStudio : true,
		plotlyServerURL : "https://chart-studio.plotly.com",
		modeBarButtonsToRemove: ['toImage', 'select2d', 'lasso2d', 'toggleSpikelines','hoverCompareCartesian'],
		displaylogo: false
	};
	var container = document.getElementById(layoutName);
	// Plotly.react(container, data, layout,{displayModeBar: false});
	Plotly.react(container, data, layout, config);
}

window.updateWellPlot = function(mainObject, yaxisLabel, layoutName, logName) {

}