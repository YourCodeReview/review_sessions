// remove element of array time
const removeIndex = (arr, element) => {
  for (let i = 0; i < arr.length; i++) {
    if (arr[i] === element) {
      arr.splice(i, 1);
    }
  }
  return arr;
};
// cut string time
const cutTimeString = (time) => {
  let cutStringTime = time.join("").substring(0, 4);
  return cutStringTime;
};
function renderCurrentTime() {
  // transfor new Date object to string
  let objectTime = new Date().toLocaleTimeString().split("");
  let time = cutTimeString(removeIndex(objectTime, ":"));
  for (let i = 0; i < time.length; i++) {
    elementDom[0].value = time[0];
    elementDom[1].value = time[1];
    elementDom[2].value = time[2];
    elementDom[3].value = time[3];
  }
}

// START FUNCTION CLOCK !!!
let currentTime = setInterval(runTime, 1000);
let digitalYime = setInterval(renderCurrentTime, 1000);

// setting switching between current and digital time
for (let input of elementDom) {
  input.onblur = function () {
    console.log("покинул элеиент");
    if (input !== "") {
      input.value = 0;
    }
    if (Array.from(elementDom).every((item) => {})) console.log(true);
  };
  input.onfocus = function () {
    clearInterval(digitalYime);
    input.value = "";
  };
}
