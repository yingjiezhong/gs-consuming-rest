"use strict"

//let message
//message = 'an alert!'
//alert( message );

let age = prompt('provide your age?', "");

alert(`You are above 18: ${checkAge(age)} `);

let ca = checkAge;

alert(checkAge)

function checkAge(age) {
  if (age > 18) {
    return true;
  } else {
    // ...
    return confirm('Did parents allow you?');
  }
}