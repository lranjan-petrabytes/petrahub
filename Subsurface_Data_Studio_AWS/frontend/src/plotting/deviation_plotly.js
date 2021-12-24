window.deviationPlotly = function(mainObject, layoutName) {

	// logs.put("Date", dateArray);
	// logs.put("MD", mdArray);
	// logs.put("MW", datVal);
	// logs.put("Data Type", datType);
	// logs.put("Log", logName)
	
	var md = {
		x : mainObject.MD,
		y : mainObject.TVD,
		//xaxis : 'x1',
		//yaxis : 'y1',
		type : 'scatter',
		mode : 'lines+markers',
		name : "MD",
		marker : {
			size : 3
		}
	};
	
	var inc = {
		x : mainObject.Inclination,
		y : mainObject.TVD,
		xaxis : 'x2',
		yaxis : 'y2',
		type : 'scatter',
		mode : 'lines+markers',
		name : "Inclination",
		marker : {
			size : 3
		}
	};
	
	var azm = {
		x : mainObject.Azimuth,
		y : mainObject.TVD,
		xaxis : 'x3',
		yaxis : 'y3',
		type : 'scatter',
		mode : 'lines+markers',
		name : "Azimuth",
		marker : {
			size : 3
		}
	};
	/*
	var east = {
		x : mainObject.Easting,
		y : mainObject.TVD,
		xaxis : 'x4',
		yaxis : 'y4',
		type : 'scatter',
		mode : 'lines+markers',
		name : "Easting",
		marker : {
			size : 3
		}
	};
	
	var north = {
		x : mainObject.Northing,
		y : mainObject.TVD,
		xaxis : 'x5',
		yaxis : 'y5',
		type : 'scatter',
		mode : 'lines+markers',
		name : "Northing",
		marker : {
			size : 3
		}
	};
	*/
	
    var data = [md, inc, azm];
    /*
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
	        args: [{'marker.color': 'black', 'line.color': 'magenta'},[6]],
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
        args: ['line.color', 'black'],
        label: 'black'
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
    */
	var layout = {
        autoresize : true,
        autosize : true,
		uirevision:'true',
		margin : {
			l : 40,
			r : 10,
			b : 45,
			t : 10
		},
		paper_bgcolor : 'rgb(200, 200, 200)',
		plot_bgcolor : 'rgb(255, 255, 255)',
        xaxis : {
			title : {
				text : 'MD (m)',
				font : {
					family : 'Arial, sans-serif',
					size : 12,
					color : 'black'
				},
				standoff : 0
			},
			zeroline : false,
			side : 'bottom',
			linecolor : 'rgb(60, 60, 60)',
			ticks : 'outside',
			tickfont : {
				color : 'rgb(60, 60, 60)',
				size : 9
			},
			rangemode : 'tozero'
		},
		
		xaxis2 : {
			title : {
				text : 'Inclination (dega)',
				font : {
					family : 'Arial, sans-serif',
					size : 12,
					color : 'black'
				},
				standoff : 0
			},
			zeroline : false,
			side : 'bottom',
			linecolor : 'rgb(60, 60, 60)',
			ticks : 'outside',
			tickfont : {
				color : 'rgb(60, 60, 60)',
				size : 9
			},
			rangemode : 'tozero'
		},
		
		xaxis3 : {
			title : {
				text : 'Azimuth (dega)',
				font : {
					family : 'Arial, sans-serif',
					size : 12,
					color : 'black'
				},
				standoff : 0
			},
			zeroline : false,
			side : 'bottom',
			linecolor : 'rgb(60, 60, 60)',
			ticks : 'outside',
			tickfont : {
				color : 'rgb(60, 60, 60)',
				size : 9
			},
			rangemode : 'tozero'
		},
		
		yaxis : {

			title : 'TVD (m)',
			zeroline : false,
			titlefont : {
				family : 'Arial, sans-serif',
				size : 10,
				color : 'black',
				standoff : 0
			},
			autorange : 'reversed',
			showticklabels : true,
			scrollZoom : true,
			linecolor : 'rgb(60, 60, 60)',
			ticks : 'outside',
			tickfont : {
				color : 'rgb(60, 60, 60)',
				size : 9
			},
			rangemode : 'tozero'
		},
		
		yaxis2 : {

			title : 'TVD (m)',
			zeroline : false,
			titlefont : {
				family : 'Arial, sans-serif',
				size : 10,
				color : 'black',
				standoff : 0
			},
			autorange : 'reversed',
			showticklabels : true,
			scrollZoom : true,
			linecolor : 'rgb(60, 60, 60)',
			ticks : 'outside',
			tickfont : {
				color : 'rgb(60, 60, 60)',
				size : 9
			},
			rangemode : 'tozero'
		},
		
		yaxis3 : {

			title : 'TVD (m)',
			zeroline : false,
			titlefont : {
				family : 'Arial, sans-serif',
				size : 10,
				color : 'black',
				standoff : 0
			},
			autorange : 'reversed',
			showticklabels : true,
			scrollZoom : true,
			linecolor : 'rgb(60, 60, 60)',
			ticks : 'outside',
			tickfont : {
				color : 'rgb(60, 60, 60)',
				size : 9
			},
			rangemode : 'tozero'
		},
		grid : {
			rows: 1,
			columns: 3,
			pattern: 'independent',
			xgap : 0.25
		},
		showlegend : false
        /*
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
					color : 'black'
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
				color : 'black'
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
        */
	};

	var config = {
		displayModeBar: false
	};
	var container = document.getElementById(layoutName);
	// Plotly.react(container, data, layout,{displayModeBar: false});
	Plotly.react(container, data, layout, config);
}

window.deviationPlotly3D = function(mainObject, layoutName) {
	
	var topMarker1 = {
			x : [mainObject.Easting[0]],
			y : [mainObject.Northing[0]],
			z : [mainObject.TVD[0]],
			type : 'scatter3d',
			mode : 'markers',

			opacity: 1.0,
			marker: {
			size: 12,
			color: 'rgba(255, 0, 0, 0.8)',
			symbol: 'cross'
			// one of ( "circle" | "circle-open" | "cross" | "diamond" | "diamond-open" |
			//"square" | "square-open" | "x" )
			}
			};

			var topMarker2 = {
			x : [mainObject.Easting[0]],
			y : [mainObject.Northing[0]],
			z : [mainObject.TVD[0]],
			type : 'scatter3d',
			mode : 'markers',

			opacity: 1.0,
			marker: {
			size: 12,
			color: 'rgba(255, 0, 0, 0.8)',
			symbol: 'circle-open'
			// one of ( "circle" | "circle-open" | "cross" | "diamond" | "diamond-open" |
			//"square" | "square-open" | "x" )
			}
			};

			    
	
	var bore = {
		x : mainObject.Easting,
		y : mainObject.Northing,
		z : mainObject.TVD,
		type : 'scatter3d',
		mode : 'lines',
		opacity: 0.7,
		line: {
		   width: 10,
		   colorscale: 'Viridis'
		   }
	};
	
	var data = [bore, topMarker1, topMarker2];
    
	var layout = {
        autoresize : true,
        autosize : true,
		uirevision:'true',
		showlegend : false,
		margin : {
			l : 40,
			r : 10,
			b : 45,
			t : 10
		},
		paper_bgcolor : 'rgb(200, 200, 200)',
		plot_bgcolor : 'rgb(255, 255, 255)',
		
		scene : {
			xaxis : {
				title : {
					text : 'Easting (m)',
					font : {
						family : 'Arial, sans-serif',
						size : 12,
						color : 'black'
					},
					standoff : 0
				},
				zeroline : false,
				side : 'bottom',
				linecolor : 'rgb(60, 60, 60)',
				ticks : 'outside',
				tickfont : {
					color : 'rgb(60, 60, 60)',
					size : 9
				},
				rangemode : 'tozero'
			},
			yaxis : {
				title : 'Northing (m)',
				zeroline : false,
				titlefont : {
					family : 'Arial, sans-serif',
					size : 12,
					color : 'black',
					standoff : 0
				},
				showticklabels : true,
				scrollZoom : true,
				linecolor : 'rgb(60, 60, 60)',
				ticks : 'outside',
				tickfont : {
					color : 'rgb(60, 60, 60)',
					size : 9
				},
				rangemode : 'tozero'
			},
			zaxis : {
				title : {
					text : 'TVD (m)',
					font : {
						family : 'Arial, sans-serif',
						size : 12,
						color : 'black'
					},
					standoff : 0
				},
				autorange : 'reversed',
				zeroline : false,
				side : 'bottom',
				linecolor : 'rgb(60, 60, 60)',
				ticks : 'outside',
				tickfont : {
					color : 'rgb(60, 60, 60)',
					size : 9
				},
				rangemode : 'tozero'
			}
		}
	};

	var config = {
		displayModeBar: true
	};
	var container = document.getElementById(layoutName);
	// Plotly.react(container, data, layout,{displayModeBar: false});
	Plotly.react(container, data, layout, config);
}