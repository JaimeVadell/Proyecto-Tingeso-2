import axios from "axios";

const RESUMEN_API_BASE_URL = "http://localhost:8080/pruebas";

class PruebaService {
    createPruebaDocument(prueba){
        return axios.post(RESUMEN_API_BASE_URL + '/guardar-prueba', prueba);
    }

}

export default new PruebaService();