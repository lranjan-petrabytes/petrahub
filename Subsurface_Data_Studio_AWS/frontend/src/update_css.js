window.applyCSS = function(componenteId) {

	var element = document.getElementById(componenteId);
	element.classList.add("activeflowbutton");
	element.classList.remove("sidetoolbarbutton");
}
window.applyremoveCSS = function(componenteId) {

	var element = document.getElementById(componenteId);
	element.classList.remove("activeflowbutton");
	element.classList.add("sidetoolbarbutton");
}

window.applyDisableButtonCSS = function(componenteId) {

	var element = document.getElementById(componenteId);
	element.classList.remove("toptoolbutton");
}

window.updateProjectText = function(componenteId, projectname) {

	var element = document.getElementById(componenteId);
	element.innerHTML = projectname;
	document.getElementById(componenteId).style.color = "#00819B";

}

window.updateWellWellboreText = function(componenteId, wellname, wellborename) {

	var element = document.getElementById(componenteId);
	element.innerHTML = 'Well : ' + wellname;
	element.innerHTML = 'Wellbore : ' + wellborename;

}

window.enableComponents = function(keys) {
	var keyList = keys.split(",");
	for (var j = 0; j < keyList.length; j++) {
		var key = keyList[j];
		var element = document.getElementById(key);
		element.disabled = false;
	}
}

window.disableComponents = function(keys) {
	var keyList = keys.split(",");
	for (var j = 0; j < keyList.length; j++) {
		var key = keyList[j];
		var element = document.getElementById(key);
		element.disabled = true;
	}

}

window.getComponent = function(element, key) {
	var component = document.getElementById(key);
	element.$server.getCP(component);
}
