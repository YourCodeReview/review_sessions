class Header {
  handlerOpenShoppingPage() {
    shoppingPage.render();
  }

  render(counter) {
    const html = `
        <div class="header-container">
            <div class="header-container__counter" onclick="headerPage.handlerOpenShoppingPage()">
                ${counter}
                <img class="header-container__image" src="./image/icon-purchase.png">
            </div>
        </div>
    `
    ROOT_HEADER.innerHTML = html;
  }
}

const headerPage = new Header();


const productsStore = local.getProducts();
headerPage.render(productsStore.length);

