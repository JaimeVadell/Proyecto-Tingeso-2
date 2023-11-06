import {Component} from "react";
import DescuentosService from "../../services/GestorAcademicoService/DescuentosService";

class ActualizarDescuentosPruebas extends Component {
    constructor(props){
        super(props)
        this.actualizarDescuentosPrueba = this.actualizarDescuentosPrueba.bind(this);
    }

    async actualizarDescuentosPrueba() {
        try {
            const response = await DescuentosService.actualizarDescuentosPruebas();
            console.log(response.status); // You can check the status here
            if(response.status === 200) {
                alert("Descuentos de prueba actualizados exitosamente");
            }
            else {
                alert("Ocurrio un error al actualizar los descuentos de la prueba");
            }

        } catch (error) {
            console.error(error);
        }
    }

    render() {
        return (
            <div style={{ width: '50%', margin: '0 auto', textAlign: 'center' }}>
                <h1>Actualizar Descuentos de Prueba</h1>
                <button className="btn btn-success" style={{ marginTop: '10px' }} onClick={this.actualizarDescuentosPrueba}>Actualizar Descuentos Prueba</button>
            </div>
        )
    }
}

export default ActualizarDescuentosPruebas;