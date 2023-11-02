import axios from 'axios';

const CUOTAS_API_BASE_URL = "http://localhost:8080/arancel";


class ArancelService {
    getArancel(rut){
        return axios.get(CUOTAS_API_BASE_URL + '/' + rut);
    }

    createArancel(CrearArancelDTO){
        return axios.post(CUOTAS_API_BASE_URL + '/crear', CrearArancelDTO);
    }

}



export default new ArancelService()
