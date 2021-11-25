import { client as db, errors } from '../../../prisma/client.js'

const Items = db.item

async function getItemList(req, res) {
  const items = await Items.findMany()
  res.status(200).json(items)
  /*
    #swagger.tags = ['Item']
    #swagger.summary = 'Get item list'
    #swagger.responses[200] = {
      description: 'Responce array of items',
      schema: { $ref: "#/definitions/ItemList" }
    }
  */
}

async function getItem(req, res) {
  const id = Number(req.params.id)
  if (isNaN(id)) {
    res.status(403).json({ error: 'Item id must be an integer' })
  } else {
    const item = await Items.findUnique({
      where: { id }
    })
    if (item === null) {
      res.status(404).json({ error: `Not found item by id - ${id}` })
    } else {
      res.status(200).json(item)
    }
  }
  /*
    #swagger.tags = ['Item']
    #swagger.summary = 'Get item by id'
    #swagger.responses[200] = {
      description: 'Responce item',
      schema: { $ref: "#/definitions/Item" }
    }
    #swagger.responses[403] = {
      description: 'Error: Forbidden',
      schema: { $ref: "#/definitions/Item403" }
    }
    #swagger.responses[404] = {
      description: 'Error: Not Found',
      schema: { $ref: "#/definitions/Item404" }
    }
  */
}

async function createItem(req, res) {
  try {
    const { name, type, count, price } = req.body
    const result = await Items.create({ data: { name, type, count: +count, price: +price } })
    res.status(201).json(result)
  } catch {
    res.status(400).json({ error: 'Wrong item parameters' })
  }
  /*
    #swagger.tags = ['Item']
    #swagger.summary = 'Create item'
    #swagger.parameters['item'] = {
      in: 'body',
      type: 'object',
      description: 'Item object',
      schema: { $ref: "#/definitions/Item" }
    }
    #swagger.responses[201] = {
      description: 'Created',
      schema: { $ref: "#/definitions/ItemCreated" }
    }
    #swagger.responses[400] = {
      description: 'Bad Request',
      schema: { $ref: "#/definitions/Item400" }
    }
  */
}

async function updateItem(req, res) {
  const id = Number(req.params.id)
  if (isNaN(id)) {
    res.status(403).json({ error: 'Item id must be an integer' })
  } else {
    try {
      const { name, type, count, price } = req.body
      const result = await Items.update({ where: { id }, data: { name, type, count: +count, price: +price } })
      res.status(201).json(result)
    } catch (error) {
      if (error instanceof errors.know) {
        res.status(404).json({ error: `Not found item by id - ${id}` })
      } else {
        res.status(400).json({ error: 'Wrong item parameters' })
      }
    }
  }
  /*
    #swagger.tags = ['Item']
    #swagger.summary = 'Update item by id'
    #swagger.parameters['id'] = {
      description: 'Item ID',
    }
    #swagger.parameters['item'] = {
      in: 'body',
      type: 'object',
      description: 'New item data',
      schema: { $ref: "#/definitions/Item" }
    }
    #swagger.responses[200] = {
      description: 'OK'
    }
    #swagger.responses[400] = {
      description: 'Bad Request',
      schema: { $ref: "#/definitions/Item400" }
    }
    #swagger.responses[403] = {
      description: 'Error: Forbidden',
      schema: { $ref: "#/definitions/Item403" }
    }
    #swagger.responses[404] = {
      description: 'Error: Not Found',
      schema: { $ref: "#/definitions/Item404" }
    }
  */
}

async function deleteItem(req, res) {
  const id = Number(req.params.id)
  if (isNaN(id)) {
    res.status(403).json({ error: 'Item id must be an integer' })
  } else {
    try {
      const result = await Items.delete({ where: { id } })
      res.status(200).json(result)
    } catch {
      res.status(404).json({ error: `Not found item by id - ${id}` })
    }
  }
  /*
    #swagger.tags = ['Item']
    #swagger.summary = 'Delete item by id'
    #swagger.parameters['id'] = {
      description: 'Item ID',
    }
    #swagger.responses[403] = {
      description: 'Error: Forbidden',
      schema: { $ref: "#/definitions/Item403" }
    }
    #swagger.responses[404] = {
      description: 'Error: Not Found',
      schema: { $ref: "#/definitions/Item404" }
    }
  */
}

export { getItemList, getItem, createItem, updateItem, deleteItem }
