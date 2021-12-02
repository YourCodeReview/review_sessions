"use strict";

const range = document.querySelector(".form-range");
let showLengthPassword = document.querySelector(".length");
const generatePassword = document.querySelector(".generate_password");
const numbers = document.getElementById("numbers");
const lowerCaseLetter = document.getElementById("lower_case");
const upperCaseLetter = document.getElementById("uppercase_letters");
const passwordShow = document.getElementById("Password_show");

let str = [];
let string = "";

//    function generate
function* generateSequence(start, end) {
  for (let i = start; i <= end; i++) yield i;
}

function* generatePasswordCodes() {
  // 0..9
  if (numbers.checked) {
    yield* generateSequence(48, 57);
  }
  // A..Z
  if (upperCaseLetter.checked) {
    yield* generateSequence(65, 90);
  }
  // a..z
  if (lowerCaseLetter.checked) {
    yield* generateSequence(97, 122);
  }
}

function randoomInt() {
  return Math.random() - 0.5;
}
function clearing() {
  str = [];
  string = "";
}

range.addEventListener("input", function () {
  showLengthPassword.innerHTML = `( ${this.value} )`;
});

generatePassword.addEventListener("click", () => {
  for (let code of generatePasswordCodes()) {
    str += String.fromCodePoint(code);
  }
  let stringChart = Array.from(str).sort(randoomInt);
  stringChart.length = range.value;
  string = stringChart;
  passwordShow.value = string.join("");
  clearing();
});
