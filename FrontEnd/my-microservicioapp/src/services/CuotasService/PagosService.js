import axios from 'axios';

const CUOTAS_API_BASE_URL = "http://localhost:8080/pagos";

class PagosService {
    getPagosRut(rutEstudiante){
        return axios.get(CUOTAS_API_BASE_URL + '/' + rutEstudiante);
    }
    pagarProximaCuota(rutEstudiante){
        return axios.post(CUOTAS_API_BASE_URL + '/pagar-cuota/' + rutEstudiante);
    }

}

export default new PagosService()
