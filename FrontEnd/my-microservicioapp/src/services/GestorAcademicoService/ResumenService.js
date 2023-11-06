import axios from "axios";

const RESUMEN_API_BASE_URL = "http://localhost:8080/resumen-estudiante";

class ResumenService {
  getResumenEstudiante(rutEstudiante) {
    return axios.get(RESUMEN_API_BASE_URL + "/" + rutEstudiante);
  }
}

export default new ResumenService();