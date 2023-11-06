import {Component} from "react";
import ReembolsoService from "../../services/GestorAcademicoService/ReembolsoService";

class ViewReembolsoEstudianteComponent extends Component {
    constructor(props){
        super(props)
        this.state = {
            rutEstudiante: this.props.match.params.rut, // Obtén el rut desde los parámetros de ruta
            reembolso: null
        }
        this.reclamarReembolso = this.reclamarReembolso.bind(this);
    }

    async componentDidMount() {
        try {
            const response = await ReembolsoService.getReembolsoEstudiante(this.state.rutEstudiante);
            if(response.status === 200) {
                // Si el monto del reembolso es 0, establece reembolso en null
                if(response.data.montoReembolso === 0) {
                    this.setState({ reembolso: null });
                } else {
                    this.setState({ reembolso: response.data });
                }
            }
        } catch (error) {
            console.error(error);
        }
    }

    async reclamarReembolso() {
        try {
            const response = await ReembolsoService.reclamarReembolsoEstudiante(this.state.rutEstudiante);
            if(response.status === 200) {
                alert("Reembolso reclamado exitosamente");
            }
            else {
                alert("Ocurrió un error al reclamar el reembolso");
            }
        } catch (error) {
            console.error(error);
        }
    }

    render() {
        return (
            <div style={{ width: '50%', margin: '0 auto', textAlign: 'center' }}>
                <h1>Reembolso del Estudiante</h1>
                {this.state.reembolso ? (
                    <div>
                        <p>Monto del Reembolso: {this.state.reembolso.montoReembolso}</p>
                        <p>Rut del Estudiante: {this.state.reembolso.rutEstudiante}</p>
                        <button className="btn btn-success" style={{ marginTop: '10px' }} onClick={this.reclamarReembolso}>Reclamar Reembolso</button>
                    </div>
                ) : (
                    <p>El estudiante no tiene un monto para reembolsar.</p>
                )}
            </div>
        )
    }
}

export default ViewReembolsoEstudianteComponent;