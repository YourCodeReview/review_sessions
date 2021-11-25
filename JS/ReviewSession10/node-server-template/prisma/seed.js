import prisma from '@prisma/client'
import seed from './seed/func.js'

const db = new prisma.PrismaClient()

seed(db)
  .catch((e) => {
    console.error(e)
    process.exit(1)
  })
  .finally(async () => {
    await db.$disconnect()
  })