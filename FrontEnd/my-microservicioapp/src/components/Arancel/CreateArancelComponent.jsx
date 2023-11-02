import {Component} from "react";
import ArancelService from "../../services/CuotasService/ArancelService";
class CreateArancelComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            rut: '',
            tipoColegio : '',
            EMedioPago: '',
            numeroCuotas: ''
        }
        this.handleMedioPagoChange = this.handleMedioPagoChange.bind(this);
        this.handleNumeroCuotasChange = this.handleNumeroCuotasChange.bind(this);
        this.saveArancel = this.saveArancel.bind(this);
    }

    saveArancel = (e) => {
        e.preventDefault();
        let arancelDTO = {
            rut: this.state.rut,
            EmedioPago: this.state.EMedioPago,
            numeroCuotas: this.state.numeroCuotas};
        console.log("Arancel a enviar:", arancelDTO);
        ArancelService.createArancel(arancelDTO).then(res => {
            this.props.history.push('/estudiantes');
        });
    }

    handleMedioPagoChange = (event) => {
        const value = event.target.value;
        this.setState({
            EMedioPago: value,
            numeroCuotas: value === "CONTADO" ? 0 : this.state.numeroCuotas
        });
    }


    handleNumeroCuotasChange = (event) => {
        const value = event.target.value;
        const { tipoColegio } = this.state;

        let maxCuotas;

        // Establecer el número máximo de cuotas según el tipo de colegio
        if (tipoColegio === "PRIVADO") {
            maxCuotas = 4;
        } else if (tipoColegio === "SUBVENCIONADO") {
            maxCuotas = 7;
        } else if (tipoColegio === "MUNICIPAL") {
            maxCuotas = 10;
        } else {
            // Establecer un valor predeterminado si el tipo de colegio no está definido
            maxCuotas = 0;
        }

        // Asegúrate de que el valor no exceda el máximo permitido
        if (value <= maxCuotas) {
            this.setState({ numeroCuotas: value });
        } else {
            this.setState({ numeroCuotas: maxCuotas });
        }
    }
    cancel(){
        this.props.history.push('/estudiantes');
    }
    componentDidMount() {
        const params = new URLSearchParams(this.props.location.search);
        const rut = params.get("rut");
        const tipoColegio = params.get("tipoColegio");
        this.setState({
            rut: rut,
            tipoColegio: tipoColegio
        });
    }

    render() {
        return (
            <form>
                <div className="form-group">
                    <label> Tipo de Pago: </label>
                    <select name="tipoPago" className="form-control" value={this.state.EMedioPago} onChange={this.handleMedioPagoChange}>
                        <option value="CONTADO">Contado</option>
                        <option value="CUOTAS">Cuotas</option>
                    </select>
                </div>
                <div className="form-group">
                    <label>Numero de Cuotas:</label>
                    <input type="number" name="numeroCuotas" value={this.state.numeroCuotas} onChange={this.handleNumeroCuotasChange} min="0" max="10" disabled={this.state.medioPago === "CONTADO"} />
                </div>
                <button className = "btn btn-success" onClick={this.saveArancel} >Crear Matricula</button>
                <button className="btn btn-danger" onClick={this.cancel.bind(this)} style={{marginLeft: "10px"}}>Cancel</button>
            </form>
        );
    }
}

export default CreateArancelComponent;