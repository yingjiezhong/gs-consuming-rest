var sse = new EventSource('http://localhost:8080/job/sseInResponseEntity');
alert("created sse object")
sse.onmessage = function (evt) {
    console.log("received event: " + evt.data)
//    var el = document.getElementById('sseInResponseEntity');
//    el.appendChild(document.createTextNode(evt.data));
//    el.appendChild(document.createElement('br'));
//    alert(el);
};
alert("done sse")