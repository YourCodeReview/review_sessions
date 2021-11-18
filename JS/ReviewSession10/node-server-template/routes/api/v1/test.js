import express from 'express'
import { getItemList, getItem, createItem, updateItem, deleteItem } from '../../../controllers/api/v1/test.js'

const router = express.Router()

router
  .get('/item', getItemList)
  .get('/item/:id', getItem)
  .post('/item', createItem)
  .put('/item/:id', updateItem)
  .delete('/item/:id', deleteItem)

export default router
