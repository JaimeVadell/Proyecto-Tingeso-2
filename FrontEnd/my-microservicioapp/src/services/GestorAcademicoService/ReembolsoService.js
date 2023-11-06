import Axios from "axios";

const BASE_URL = "http://localhost:8080/reembolso";

class ReembolsoService {
    getReembolsoEstudiante(rutEstudiante) {
        return Axios.get(BASE_URL + "/" + rutEstudiante);
    }
    reclamarReembolsoEstudiante(rutEstudiante){
        return Axios.post(BASE_URL  + "/" + rutEstudiante);
    }
}

export default new ReembolsoService();