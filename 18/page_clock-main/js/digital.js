const arr = []
function test() {
  for (let item of elementDom) {
    arr.push(item.value);
  }
}
test()
function runDigitalTime(deg = 6) {
    let hours = arr[0] + arr[1]
    let minutes = arr[2] + arr[3]
    hour.style.transform = `rotate(${hours + (minutes * deg / 12)}deg)`
    min.style.transform = `rotate(${arr[2] + arr[3]}deg)`
}
runDigitalTime()
