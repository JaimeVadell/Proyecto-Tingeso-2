import {Component} from "react";
import ResumenService from "../../services/GestorAcademicoService/ResumenService";

class ViewResumenEstudianteComponent extends Component {
    constructor(props) {
        super(props);
        this.state = {
            resumen: ''
        }
    }

    componentDidMount() {
        const rut = this.props.match.params.rut;
        ResumenService.getResumenEstudiante(rut).then((res) => {
            this.setState({resumen: res.data});
        });
    }
    cancel(){
        this.props.history.push('/estudiantes');
    }


    render() {
        return (
            <div>
                <h3 className="text-center">Resumen del Estudiante</h3>
                <div className="table-responsive">
                    <table className="table table-striped table-bordered">
                        <thead>
                        <tr>
                            <th>RUT</th>
                            <th>Nombre</th>
                            <th>Apellido</th>
                            <th>Numero Examenes Rendidos</th>
                            <th>Promedio Examenes Rendidos</th>
                            <th>Monto Total Arancel</th>
                            <th>Tipo Pago</th>
                            <th>Numero Cuotas Pactadas</th>
                            <th>Numero Cuotas Pagadas</th>
                            <th>Monto Total Pagado</th>
                            <th>Fecha Ultimo Pago</th>
                            <th>Monto Por Pagar</th>
                            <th>Numero Cuotas Con Retraso</th>
                        </tr>
                        </thead>
                        <tbody>
                        {
                            <tr key={this.state.resumen.rut}>
                                <td>{this.state.resumen.rut}</td>
                                <td>{this.state.resumen.nombre}</td>
                                <td>{this.state.resumen.apellido}</td>
                                <td>{this.state.resumen.numeroExamenesRendidos === 0 ? 'Sin Examenes' : this.state.resumen.numeroExamenesRendidos}</td>
                                <td>{this.state.resumen.numeroExamenesRendidos === 0 ? 'No aplica' : this.state.resumen.promedioExamenesRendidos}</td>
                                <td>{this.state.resumen.montoTotalArancel ? this.state.resumen.montoTotalArancel.toLocaleString('de-DE') : ''}</td>
                                <td>{this.state.resumen.tipoPago}</td>
                                <td>{this.state.resumen.numeroCuotasPactadas}</td>
                                <td>{this.state.resumen.numeroCuotasPagadas}</td>
                                <td>{this.state.resumen.montoTotalPagado ? this.state.resumen.montoTotalPagado.toLocaleString('de-DE') : 0}</td>
                                <td>{this.state.resumen.fechaUltimoPago === null ? 'Sin Pagos' : this.state.resumen.fechaUltimoPago}</td>
                                <td>{this.state.resumen.montoPorPagar ? this.state.resumen.montoPorPagar.toLocaleString('de-DE') : ''}</td>
                                <td>{this.state.resumen.numeroCuotasConRetraso}</td>
                            </tr>
                        }
                        </tbody>
                    </table>
                </div>
                <button className="btn btn-danger" onClick={this.cancel.bind(this)} style={{marginLeft: "10px"}}>Volver</button>
            </div>
        )
    }


}

export default ViewResumenEstudianteComponent;