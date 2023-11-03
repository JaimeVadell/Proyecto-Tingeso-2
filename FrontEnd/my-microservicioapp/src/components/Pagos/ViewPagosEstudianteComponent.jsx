import {Component} from "react";
import PagosService from "../../services/CuotasService/PagosService";

class ViewPagosComponent extends Component {
    constructor(props){
        super(props)
        this.state = {
            rut: '',
            pagos: []
        }
    }
    componentDidMount() {
        const rut = this.props.match.params.rut;
        this.setState({rut: rut})
        PagosService.getPagosRut(rut).then((res) => {
            this.setState({pagos: res.data});
        });
    }
    cancel(){
        this.props.history.push('/estudiantes');
    }
    render() {
        return (
            <div>
                <h3 className="text-center">Pagos del Estudiante</h3>
                <div className="row">
                    <table className="table table-striped table-bordered">
                        <thead>
                        <tr>
                            <th> ID Pago</th>
                            <th> Monto Pagado</th>
                            <th> Fecha Pago</th>
                            <th> Tipo Pago</th>
                        </tr>
                        </thead>
                        <tbody>
                        {
                            this.state.pagos.map(
                                pago =>
                                    <tr key = {pago.idPago}>
                                        <td> {pago.idPago} </td>
                                        <td> {pago.montoPagado}</td>
                                        <td> {pago.fechaPago}</td>
                                        <td> {pago.tipoPago}</td>
                                    </tr>
                            )
                        }
                        </tbody>
                    </table>
                </div>
                <button className="btn btn-danger" onClick={this.cancel.bind(this)} style={{marginLeft: "10px"}}>Volver</button>
            </div>
        )
    }
}

export default ViewPagosComponent;