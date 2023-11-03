import axios from "axios";

const CUOTAS_API_BASE_URL = "http://localhost:8080/cuotas";

class CuotasService {
    getCuotasRut(rutEstudiante){
        return axios.get(CUOTAS_API_BASE_URL + '/' + rutEstudiante);
    }

}
export default new CuotasService()