import 'dotenv/config'
import express from 'express'
import logger from './logs/logger.js'
import apiRoute from './routes/api/v1'
import { docServe, docSetup } from './docs/swagger/init.js'

const { PORT, MODE } = process.env

const app = express()
// application/x-www-form-urlencoded
app.use(express.urlencoded({ extended: false }))
app.use(express.json())
app.use(logger)

app.use(express.static('./public'))
app.use('/api/v1', apiRoute)

app.use('/docs', docServe, docSetup)
/* istanbul ignore next */
const server = app.listen(PORT, () => {
  if (MODE !== 'test') {
    console.log(`App listening at http://localhost:${PORT}`)
  }
})

export default server
