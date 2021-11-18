class Shopping {
    handlerCloseShoppingPage() {
        ROOT_SHOPPING.innerHTML = ''
    }

    render() {
        const productsStore = local.getProducts()
        let htmlCatalog = ''
        let sumCatalog = 0

        CATALOG.forEach( ({ id, name, img, price }) => {

            if (productsStore.indexOf(id) !== -1) {
                htmlCatalog += `
                    <tr>
                        <td class="shoppingPage__name">${name}</td>
                        <td class="shoppingPage__price">${price} $</td>
                    </tr>
                `
                sumCatalog += price
            }        
        })
        const html = `
        <div class="shoppingPage-container">
            <div class="shopping-close" onclick="shoppingPage.handlerCloseShoppingPage()">
                <img class="shopping-close__img" src="./image/close-cross.png">
            </div>
            <table class="shoppingPage__table" >${htmlCatalog}
                <tr class="shoppingPage__sum">
                    <td>Sum</td>
                    <td>${sumCatalog.toFixed(2)} $</td>
                </tr>
            </table>
            <div></div>
        </div>
    `
        ROOT_SHOPPING.innerHTML = html
    }

}

const shoppingPage = new Shopping()

