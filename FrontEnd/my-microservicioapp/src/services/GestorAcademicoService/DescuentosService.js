import Axios from "axios";

const BASE_URL = "http://localhost:8080/actualizar-precios";

class ActualizarPreciosService {
    actualizarAranceles(fecha){
        return Axios.post(BASE_URL + '/aranceles', JSON.stringify(fecha), {
            headers: { 'Content-Type': 'application/json' }
        });
    }

    actualizarDescuentosPruebas(){
        return Axios.post(BASE_URL + '/pruebas');
    }
}

export default new ActualizarPreciosService();