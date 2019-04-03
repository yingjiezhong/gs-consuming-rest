var xhr = function(url) {
    return new Promise(function(resolve, reject) {
        var xmhr = new XMLHttpRequest();
//        xmhr.onload = () => resolve(xmhr);
        xmhr.onerror = () => reject(new Error("xmhr loading error: " + url));
        xmhr.onprogress = () => resolve(event);

        xmhr.open("GET", url, true);
        xmhr.send();
    });
};

xhr('http://localhost:8080/job/rbe')
  .finally(() => alert("Promise ready"))
  .then( result => alert("loaded " + result.loaded + " bytes") )
  .catch( error => alert(error) );

