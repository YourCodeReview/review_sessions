import swaggerAutogen from 'swagger-autogen'

const outputFile = './docs/swagger/output.json'
const endpointsFiles = ['./app.js']

const doc = {
  info: {
    version: "1.0.0",
    title: "Node server template API",
    description: "Documentation automatically generated."
  },
  host: "localhost:3000",
  basePath: "/",
  schemes: ['http'],
  consumes: ['application/json'],
  produces: ['application/json'],
  definitions: {
    Item: {
      $name: 'Carrot',
      $type: 'Food',
      $count: 100,
      $price: 2.9
    },
    ItemList: [{
      $name: 'Carrot',
      $type: 'Food',
      $count: 100,
      $price: 2.9
    }],
    ItemCreated: {
      $id: 3,
      $name: 'Carrot',
      $type: 'Food',
      $count: 100,
      $price: 2.9
    },
    Item400: { error: 'Error: Wrong item parameters' },
    Item403: { error: 'Item id must be an integer' },
    Item404: { error: 'Not found item by id - 333' }
  }
}

swaggerAutogen()(outputFile, endpointsFiles, doc)
