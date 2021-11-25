import swaggerUi from 'swagger-ui-express'
import { readFile } from 'fs/promises'

const swaggerFile = JSON.parse(await readFile(new URL('./output.json', import.meta.url)))

export const docServe = swaggerUi.serve
export const docSetup = swaggerUi.setup(swaggerFile)
