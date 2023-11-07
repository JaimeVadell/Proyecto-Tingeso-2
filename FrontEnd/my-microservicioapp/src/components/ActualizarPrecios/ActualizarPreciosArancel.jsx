import {Component} from "react";
import DescuentosService from "../../services/GestorAcademicoService/ActualizarPreciosService";

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
                    alert("Calculo realizado exitosamente");
                }
                else {
                    alert("Ocurrio un error al recalcular las cuotas");
                }

            } catch (error) {
                console.error(error);
            }
        }
    }

    render() {
        return (
            <div style={{ width: '50%', margin: '0 auto', textAlign: 'center' }}>
                <h1>Actualizar Precio Cuotas</h1>
                <p>Por favor ingresa una fecha para recalcular el precio de las cuotas:</p>
                <input type="date" value={this.state.fecha} onChange={this.handleDateChange} />
                <button className="btn btn-success" style={{ marginTop: '10px' }} onClick={this.submitDate}>Recalcular </button>
                <button className="btn btn-danger" style={{ marginTop: '10px', marginLeft: '10px' }} onClick={() => this.props.history.push('/estudiantes')}>Volver</button>
            </div>
        )
    }
}

export default ActualizarDescuentosPruebas;