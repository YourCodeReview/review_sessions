class Error {

    render() {
        const html = `
            <div class="error-container">
                <h2 class="error-title">No access</h2>
                <p class="error-description">no address data</p>
            </div>
        `

        ROOT_ERROR.innerHTML = html
    }

    
}

const errorPage = new Error