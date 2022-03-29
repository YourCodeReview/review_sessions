chrome.devtools.panels.create(
  'Netlogger',
  'assets/icons/icon32.png',
  'devtools/app/index.html',
  () => {
    console.log('DevTools panel loaded!');
  }
);
