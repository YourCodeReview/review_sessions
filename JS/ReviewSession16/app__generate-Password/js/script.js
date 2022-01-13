const form = document.forms;
let lengthValue = document.querySelector(".length");

// function iterable for number from Unicode table
function symbolCodePoint(start, end) {
  let array = [];

  for (let i = start; i <= end; i++) {
    array.push(i);
  }
  return array;
}
// generate symbol from Unicode table
function generatePasswordCodes() {
  const array = [];

  for (let elementForm of form) {
    // numbers symbol for password
    if (elementForm[1].checked) {
      let codeSymbolNumber = symbolCodePoint(48, 57);
      array.push(codeSymbolNumber);
    }
    // lowerCase symbol for password
    if (elementForm[2].checked) {
      let codeSymbolLowerCase = symbolCodePoint(65, 90);
      array.push(codeSymbolLowerCase);
    }
    // upperCase symbol for password
    if (elementForm[3].checked) {
      let codeSymbolUpperCase = symbolCodePoint(97, 122);
      array.push(codeSymbolUpperCase);
    }
  }
  return array;
}
// sort array element for generatePasswordCodes()
function sortCode(arr) {
  let result = [].concat(...arr);
  result.sort(() => Math.random() - 0.5);

  return result;
}
// function transform symbol Unicode in string
function symbolUniCode() {
  let str = "";
  for (let code of sortCode(generatePasswordCodes())) {
    str += String.fromCodePoint(code);
  }
  for (let sliceSring of form) {
    return str.slice(0, sliceSring[0].value);
  }
}
// event handlers
for (let lengthPassword of form) {
  // input class password length
  lengthPassword[0].addEventListener("input", function () {
    lengthValue.innerHTML = `( ${this.value} )`;
  });
}

for (let generatePassword of form) {
  generatePassword[5].addEventListener("click", () => {
    generatePassword[4].value = symbolUniCode();
  });
}