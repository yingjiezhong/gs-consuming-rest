var xhr = function(url) {
    return new Promise(function(resolve, reject) {
        let script = document.createElement('script');
        script.src = url;
        script.onload = () => resolve(script);
        script.onerror = () => reject(new Error("script loading error: " + url));
        document.head.append(script);

//      setTimeout(() => reject(new Error("Whoops!")), 1000);
//      setTimeout(() => resolve("done!"), 1000);
    });
};

xhr('https://cdnjs.cloudflare.com/ajax/libs/lodash.js/4.17.11/lodash.js')
  .finally(() => alert("Promise ready"))
  .then( result => alert(result) )
  .catch( error => alert(error) );

