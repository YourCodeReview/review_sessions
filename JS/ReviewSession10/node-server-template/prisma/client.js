import prisma from '@prisma/client'
import logger from './logger.js'

export const client = new prisma.PrismaClient()

if (process.env.MODE === 'dev') {
  client.$use(logger)
}

export const errors = {
  know: prisma.PrismaClientKnownRequestError,
  unknow: prisma.PrismaClientUnknownRequestError,
  init: prisma.PrismaClientInitializationError
}

export default client