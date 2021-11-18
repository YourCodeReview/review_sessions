import fs from 'fs'
import path from 'path'
import morgan from 'morgan'
import chalk from 'chalk'

const __dirname = path.resolve()
const { MODE } = process.env

const options = {}
let logFunction = () => { };

if (MODE === 'dev') {
  logFunction = function (tokens, req, res) {
    const method = tokens.method(req, res)
    const url = tokens.url(req, res)
    const status = tokens.status(req, res)
    const time = tokens['response-time'](req, res) + ' ms'
    const timestamp = chalk.underline(tokens.date(req, res, 'clf').slice(0, -6))

    return [
      timestamp,
      parseStatus(status),
      parseMethod(method),
      parseTime(time),
      parseUrl(url),
    ].join(' | ')
  }
} else if (MODE === 'prod') {
  logFunction = function (tokens, req, res) {
    const method = tokens.method(req, res).padEnd(6, ' ')
    const url = tokens.url(req, res).padEnd(21, ' ')
    const status = tokens.status(req, res)
    const time = (tokens['response-time'](req, res) + ' ms').padEnd(9, ' ')
    const timestamp = tokens.date(req, res, 'clf').slice(0, -6)

    return [
      timestamp,
      status,
      method,
      time,
      url,
      tokens['remote-addr'](req, res),
      tokens['user-agent'](req, res)
    ].join(' | ')
  }

  const accessLogStream = fs.createWriteStream(path.join(__dirname, './test.log'), { flags: 'a' })
  options.stream = accessLogStream
}

const logger = morgan(logFunction, options)

const methods = {
  'GET': chalk.blue,
  'PUT': chalk.hex('#FBA12F'),
  'DELETE': chalk.red,
  'POST': chalk.green
}

function parseMethod(method) {
  return methods[method](method.padEnd(6, ' '))
}

function parseUrl(url) {
  return chalk.bold(url)
}

function parseStatus(status) {
  let result = chalk.red;
  if (status < 300) {
    result = chalk.green
  } else if (status < 400) {
    result = chalk.hex('#FBA12F')
  }
  return result.bold(status)
}

function parseTime(time) {
  return chalk.italic(time.padEnd(9, ' '))
}

export default logger