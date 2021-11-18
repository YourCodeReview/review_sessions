'use strict'
class Products {
    constructor() {
        this.classNameActive = 'products-element__btn__active'
        this.labelAdd = 'buy now'
        this.labelRemove = 'remove goods'
    }
    handlerSaveproducts(element, id) {
        const {pushProducts, products} = local.setProducts(id)

        if (pushProducts) {
            element.classList.add(this.classNameActive)
            element.innerHTML = this.labelRemove
        } else {
            element.classList.remove(this.classNameActive)
            element.innerHTML = this.labelAdd
        }

        headerPage.render(products.length)
    }

    render() {
        const productsStore = local.getProducts()
        let htmlCatalog = ''

        CATALOG.forEach( ({ id, name, img, price }) => {
            let activeClass = ''
            let activeText = ''

            if (productsStore.indexOf(id) === -1) {
                activeText = this.labelAdd
            } else {
                activeClass = ` ${this.classNameActive}`
                activeText = this.labelRemove
            }

            htmlCatalog += `
                <li class="products-element">
                    <img class="products-element__img" src="${img}">
                    <div class="products-element__text-information">
                        <span class="products-element__name">${name}</span>
                        <span class="products-element__price">${price} $</span>
                    </div>
                    <button class="products-element__btn${activeClass}" onclick="products.handlerSaveproducts(this, '${id}')">${activeText}</button>
                </li>
            `
        })
        const html = `
            <ul class="products-container">
                ${htmlCatalog}
            </ul>
        `

        ROOT_PRODUCTS.innerHTML = html
    }
}

const products = new Products()
products.render();
