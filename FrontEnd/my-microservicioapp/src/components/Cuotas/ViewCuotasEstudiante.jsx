import {Component} from "react";
import CuotasService from "../../services/CuotasService/CuotasService";


class CreateCuotasComponent extends Component {
    constructor(props){
        super(props)

        this.state = {
            rut: '',
            cuotas: []
        }
    }

    cancel(){
        this.props.history.push('/estudiantes');
    }
    componentDidMount(){
        const rut = this.props.match.params.rut;
        this.setState({rut: rut})
        CuotasService.getCuotasRut(rut).then((res) => {
            this.setState({ cuotas: res.data});
        });
    }

    render() {
        return (
            <div>
                <h2 className="text-center">Cuotas Estudiante</h2>
                <div className="row">
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
                                        <td>{cuota.pagada ? cuota.pago : 'No aplica'}</td>
                                    </tr>
                            )
                        }
                        </tbody>
                    </table>
                </div>
            </div>
        )
    }
}

export default CreateCuotasComponent;