import {Component} from "react";
import DescuentosService from "../../services/GestorAcademicoService/DescuentosService";

class ActualizarDescuentosPruebas extends Component {
    constructor(props){
        super(props)
        this.state = {
            fecha: ''
        }
        this.handleDateChange = this.handleDateChange.bind(this);
        this.submitDate = this.submitDate.bind(this);
    }

    handleDateChange(event) {
        this.setState({ fecha: event.target.value });
    }

    async submitDate() {
        if (this.state.fecha) {
            try {
                const response = await DescuentosService.actualizarAranceles(this.state.fecha);
                console.log(response.status); // You can check the status here
                if(response.status === 200) {
                    alert("Descuentos recalculados exitosamente");
                }
                else {
                    alert("Ocurrio un error al recalcular los descuentos");
                }

            } catch (error) {
                console.error(error);
            }
        }
    }

    render() {
        return (
            <div style={{ width: '50%', margin: '0 auto', textAlign: 'center' }}>
                <h1>Actualizar Descuentos</h1>
                <p>Por favor ingresa una fecha para recalcular los descuentos:</p>
                <input type="date" value={this.state.fecha} onChange={this.handleDateChange} />
                <button className="btn btn-success" style={{ marginTop: '10px' }} onClick={this.submitDate}>Recalcular Descuentos</button>
            </div>
        )
    }
}

export default ActualizarDescuentosPruebas;