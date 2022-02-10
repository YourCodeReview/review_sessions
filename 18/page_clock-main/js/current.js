// main function, set clock time this page
function runTime(deg = 6) {
  let time = new Date();
  const HOURS = time.getHours();
  const MINUTES = time.getMinutes();

  let hh = HOURS * 30;
  let mm = HOURS * 360 + MINUTES * deg;

  hour.style.transform = `rotate(${hh + (MINUTES * deg) / 12}deg)`;
  min.style.transform = `rotate(${mm}deg)`;
}
