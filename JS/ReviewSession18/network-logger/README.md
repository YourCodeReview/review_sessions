
# <img src='https://raw.githubusercontent.com/ShinaZin/network-logger/master/assets/icons/icon128.png' height='128' alt='Netlogger Logo' aria-label='Network logger'/> 
# Network logger
![GitHub](https://img.shields.io/github/license/ShinaZin/network-logger)
![GitHub](https://img.shields.io/github/last-commit/ShinaZin/network-logger) 
![GitHub Release Date](https://img.shields.io/github/release-date/ShinaZin/network-logger)
![GitHub manifest version](https://img.shields.io/github/manifest-json/v/ShinaZin/network-logger)

Browser Extension to log specific network traffic
- Network logger is the better alternative to `console.log()` for debugging applications.
- Watching network data similar to variable watchers

Example for repeating requests:

![Netlogger](https://user-images.githubusercontent.com/18055854/158056378-8250d7a2-4397-468e-8ea8-895e52547063.gif)

## Help
- First input: partial URL of request
- Second input: 
  - `path to JSON field`, _or_
  - `?` - log only query string, _or_
  - `*` - log full JSON, _or_
  - empty - log nothing

## Scripts

* `npm run dist` - build the extension into `./dist` folder
