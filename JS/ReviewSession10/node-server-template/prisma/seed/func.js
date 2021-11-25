const itemData = [
  {
    name: 'Blood, sweat and pixels',
    type: 'Book',
    count: 41,
    price: 49.99
  },
  {
    name: 'Avocado',
    type: 'Food',
    count: 150,
    price: 1.99
  },
  {
    name: 'Bicycle',
    type: 'Sport',
    count: 20,
    price: 129.99
  },
  {
    name: 'iPhone Pro',
    type: 'Tech',
    count: 100,
    price: 879.99
  },
]

async function seed(db) {
  for (const data of itemData) {
    const item = await db.item.create({ data })
  }
}

export default seed