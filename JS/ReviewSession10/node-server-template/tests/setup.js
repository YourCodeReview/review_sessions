import supertest from 'supertest'
import server from '../app.js'
import db from '../prisma/client.js'
import seed from '../prisma/seed/func.js'

global.server = supertest(server)

beforeAll(async () => {
  await seed(db)
})

afterAll(async () => {
  const deleteItems = db.item.deleteMany()

  await db.$transaction([deleteItems])

  server.close()
  await db.$disconnect()
})