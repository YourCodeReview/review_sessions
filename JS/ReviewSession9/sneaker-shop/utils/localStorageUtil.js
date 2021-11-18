'use strict'

class LocalStorageUtil {
    constructor() {
        this.keyname = 'products'
    }
        getProducts() {
            let productsLocalStorage = localStorage.getItem(this.keyname) 
            if (productsLocalStorage !== null) {
                return JSON.parse(productsLocalStorage)
            } else {
                return []
            }
        }
        setProducts(id) {
            let products = this.getProducts()
            let pushProducts = false;
            let index = products.indexOf(id)

            if (index === -1) {
                products.push(id)
                pushProducts = true
            } else {
                products.splice(index, 1)
            }

            localStorage.setItem(this.keyname, JSON.stringify(products))

            return {
                pushProducts: pushProducts,
                products: products 
            }
        }
    
}

const local = new LocalStorageUtil()

local.getProducts()