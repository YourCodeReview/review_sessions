const { server, prismaMock } = global
const baseUrl = '/api/v1/test/item'

describe('GET', () => {
  it(baseUrl, async () => {
    const res = await server.get(baseUrl).expect(200)
    expect(Array.isArray(res.body)).toBeTruthy()
    expect(res.body.length).toBe(4)
  })

  it(`${baseUrl}/:id`, async () => {
    const { body: items } = await server.get(baseUrl).expect(200)
    const res = await server.get(`${baseUrl}/${items[0].id}`).expect(200)
    expect(res.body).toEqual(items[0])
  })

  it(`${baseUrl}/wtf`, async () => {
    const res = await server.get(`${baseUrl}/wtf`).expect(403)
    expect(res.body).toHaveProperty('error')
  })

  it(`${baseUrl}/999`, async () => {
    const res = await server.get(`${baseUrl}/999`).expect(404)
    expect(res.body).toHaveProperty('error')
  })
})

describe('CREATE', () => {
  const newItem = { name: 'create', type: 'Book', count: 777, price: 777 }

  it(baseUrl, async () => {
    const resCreate = await server.post(baseUrl).send(newItem).expect(201)
    expect(resCreate.body).toHaveProperty('id')
    newItem.id = resCreate.body.id

    const resGet = await server.get(`${baseUrl}/${resCreate.body.id}`).expect(200)
    expect(resGet.body).toEqual(newItem)
  })

  it(`${baseUrl} - wrong params`, async () => {
    delete newItem.name
    const resCreate = await server.post(baseUrl).send(newItem).expect(400)
    expect(resCreate.body).toHaveProperty('error')
  })

  it(`${baseUrl} - empty body`, async () => {
    const resCreate = await server.post(baseUrl).send().expect(400)
    expect(resCreate.body).toHaveProperty('error')
  })
})

describe('UPDATE', () => {
  const newItem = { name: 'update', type: 'Book', count: 777, price: 777 }

  it(`${baseUrl}/:id`, async () => {
    const { body: items } = await server.get(baseUrl).expect(200)
    await server.put(`${baseUrl}/${items[0].id}`).send(newItem).expect(201)
    newItem.id = items[0].id
    const resGet = await server.get(`${baseUrl}/${items[0].id}`).expect(200)
    expect(resGet.body).toEqual(newItem)
  })

  it(`${baseUrl}/wtf`, async () => {
    const resUpdate = await server.put(`${baseUrl}/wft`).send(newItem).expect(403)
    expect(resUpdate.body).toHaveProperty('error')
  })

  it(`${baseUrl}/999`, async () => {
    const resUpdate = await server.put(`${baseUrl}/999`).send(newItem).expect(404)
    expect(resUpdate.body).toHaveProperty('error')
  })

  it(`${baseUrl}/:id - empty body`, async () => {
    const { body: items } = await server.get(baseUrl).expect(200)
    const resUpdate = await server.put(`${baseUrl}/${items[0].id}`).send().expect(400)
    expect(resUpdate.body).toHaveProperty('error')
  })
})

describe('DELETE', () => {
  it(`${baseUrl}/:id`, async () => {
    const { body: items } = await server.get(baseUrl).expect(200)

    const resDelete = await server.delete(`${baseUrl}/${items[0].id}`).expect(200)
    expect(resDelete.body).toEqual(items[0])
  })

  it(`${baseUrl}/wtf`, async () => {
    const resDelete = await server.delete(`${baseUrl}/wtf`).expect(403)
    expect(resDelete.body).toHaveProperty('error')
  })

  it(`${baseUrl}/999`, async () => {
    const resDelete = await server.delete(`${baseUrl}/999`).expect(404)
    expect(resDelete.body).toHaveProperty('error')
  })
})