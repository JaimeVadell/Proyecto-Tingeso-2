import {Component} from "react";
import CuotasService from "../../services/CuotasService/CuotasService";
import PagosService from "../../services/CuotasService/PagosService";

class CreateCuotasComponent extends Component {
    constructor(props){
        super(props)

        this.state = {
            rut: '',
            cuotas: [],
            pagarCuota: false
        }
        this.handleNextPayment = this.handleNextPayment.bind(this);
    }

    handleNextPayment() {
        PagosService.pagarProximaCuota(this.state.rut).then((res) => {
            //Recargar pagina actual
            CuotasService.getCuotasRut(this.state.rut).then((res) => {
                this.setState({ cuotas: res.data});
            } );
        });

    }
    cancel(){
        this.props.history.push('/estudiantes');
    }
    componentDidMount(){
        const rut = this.props.match.params.rut;
        this.setState({rut: rut})
        CuotasService.getCuotasRut(rut).then((res) => {
            if (Array.isArray(res.data)) {
                this.setState({ cuotas: res.data});
                const pagarCuota = res.data.some(cuota => !cuota.pagada);
                this.setState({ pagarCuota });
                console.log("pagarCuota", pagarCuota)
            } else {
                this.setState({ cuotas: null });
            }
        });
    }

    render() {
        return (
            <div>
                <h2 className="text-center">Cuotas Estudiante</h2>
                <div className="row">
                    {this.state.pagarCuota ? (
                        <button className="btn btn-primary" style={{ marginBottom: "10px" }} onClick={this.handleNextPayment}>
                            Pagar siguiente cuota
                        </button>
                    ) : null}
                    {this.state.cuotas ? (
                        <table className="table table-striped table-bordered">
                            <thead>
                            <tr>
                                <th>Id Cuota</th>
                                <th>Monto Cuota</th>
                                <th>Plazo Máximo Pago</th>
                                <th>Pagada</th>
                                <th>Pago</th>
                            </tr>
                            </thead>
                            <tbody>
                            {
                                this.state.cuotas.map(
                                    cuota =>
                                        <tr key={cuota.idCuota}>
                                            <td>{cuota.idCuota}</td>
                                            <td>{cuota.montoCuota}</td>
                                            <td>{cuota.plazoMaximoPago}</td>
                                            <td>{cuota.pagada ? 'Sí' : 'No'}</td>
                                            <td>{cuota.pagada ? cuota.pago.idPago : 'No aplica'}</td>
                                        </tr>
                                )
                            }
                            </tbody>
                        </table>
                    ) : (
                        <p>El estudiante no tiene cuotas porque pagó al contado</p>
                    )}
                    <button className="btn btn-danger" onClick={() => this.props.history.push('/estudiantes')}>Volver</button>
                </div>
            </div>
        )
    }
}

export default CreateCuotasComponent;