import axios from 'axios';

const ESTUDIANTE_API_BASE_URL = "http://localhost:8080/estudiante";

class EstudianteService {
    getEstudiantes(){
        return axios.get(ESTUDIANTE_API_BASE_URL);
    }
    getEstudianteByRut(rut){
        return axios.get(ESTUDIANTE_API_BASE_URL + '/' + rut);
    }
    createEstudiante(estudiante){
        return axios.post(ESTUDIANTE_API_BASE_URL, estudiante);
    }
    updateEstudiante(estudiante){
        return axios.put(ESTUDIANTE_API_BASE_URL + '/actualizar', estudiante);
    }
    deleteEstudiante(rut){
        return axios.delete(ESTUDIANTE_API_BASE_URL + '/' + rut);
    }
}

export default new EstudianteService()