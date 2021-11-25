import express from 'express'
import testRoute from './test.js'

const router = express.Router()

router.use('/test', testRoute)

export default router