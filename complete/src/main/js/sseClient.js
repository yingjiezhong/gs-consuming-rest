var sse = new EventSource('http://localhost:8080/job/sseInResponseEntity');
alert("created sse object")

sse.onopen = function() {
    console.log("connection open")
}

sse.onclose = function() {
    console.log("connection close")
}

sse.addEventListener('connections', updateConnections, false);

function updateConnections(evt) {
console.log("update connections: " + evt.data)
}

sse.addEventListener('progress', updateProgress, false);

function updateProgress(evt) {
console.log("update Progress: " + evt.data)
}

sse.addEventListener('complete', updateComplete, false);

function updateComplete(evt) {
console.log("update Complete: " + evt.data)
sse.close();
}


sse.onmessage = function (evt) {
    console.log("received event: " + evt.data)
//    var el = document.getElementById('sseInResponseEntity');
//    el.appendChild(document.createTextNode(evt.data));
//    el.appendChild(document.createElement('br'));
//    alert(el);
};
alert("done sse")