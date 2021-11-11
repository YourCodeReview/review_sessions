'use strict'

class Loader {
    handlerLoaderPageClose() {
        ROOT_LOADER.innerHTML = ''
    }

    render() {
        const html = `
            <div class="loader-container">
                    <img class="loader__image" src="./components/Loader/Radio.svg" />
            </div>
        `
        ROOT_LOADER.innerHTML = html
    }
    
}

const loaderPage = new Loader()