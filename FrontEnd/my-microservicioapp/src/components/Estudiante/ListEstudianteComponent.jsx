import React, { Component } from "react";
import { Link } from "react-router-dom";
import EstudianteService from "../../services/EstudianteService/EstudianteService";
import ArancelService from "../../services/CuotasService/ArancelService";

class ListEstudianteComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            estudiantes: []
        }
        this.addEstudiante = this.addEstudiante.bind(this);
    }
    addEstudiante() {
        this.props.history.push('/add-estudiante');
    }
    viewEstudiante(id) {
        this.props.history.push(`/view-estudiante/${id}`);
    }

    async checkArancel(rutEstudiante) {
        try {
            const res = await ArancelService.getArancelByRut(rutEstudiante);
            return res.status === 200;
        } catch {
            return false;
        }
    }

    async componentDidMount() {
        const response = await EstudianteService.getEstudiantes();
        const estudiantes = response.data;
        const estudiantesWithArancelCheck = await Promise.all(estudiantes.map(async (estudiante) => {
            const hasArancel = await this.checkArancel(estudiante.rut);
            return { ...estudiante, hasArancel };
        }));
        this.setState({ estudiantes: estudiantesWithArancelCheck });
    }
    render() {
        return (
            <div>
                <h2 className="text-center">Estudiantes List</h2>
                <div className="row">
                    <button className="btn btn-primary" onClick={this.addEstudiante}> Add Estudiante</button>
                </div>
                <div className="row">
                    <table className="table table-striped table-bordered">
                        <thead>
                            <tr>
                                <th> RUT</th>
                                <th> Nombre</th>
                                <th> Apellido</th>
                                <th> Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            {
                                this.state.estudiantes.map(
                                    estudiante =>
                                        <tr key={estudiante.rut}>
                                            <td> {estudiante.rut} </td>
                                            <td> {estudiante.nombre} </td>
                                            <td> {estudiante.apellido}</td>
                                            <td>
                                                {!estudiante.hasArancel ? (
                                                    <Link
                                                        to={`/add-arancel?rut=${estudiante.rut}&tipoColegio=${estudiante.tipoColegio}`}
                                                        className="btn btn-primary"
                                                    >
                                                        Crear Arancel
                                                    </Link>
                                                ) : null}
                                                {estudiante.hasArancel ? (
                                                    <>
                                                        <Link to={`/cuotas/${estudiante.rut}`} className="btn btn-info" style={{ marginLeft: "10px" }}>
                                                            Ver Cuotas
                                                        </Link>
                                                        <Link to={`/pagos/${estudiante.rut}`} className="btn btn-info" style={{ marginLeft: "10px" }}>
                                                            Ver Pagos
                                                        </Link>
                                                        <Link to={`/resumen/${estudiante.rut}`} className="btn btn-info" style={{ marginLeft: "10px" }}>
                                                            Resumen Estudiante
                                                        </Link>
                                                    </>
                                                ) : null}
                                            </td>
                                        </tr>
                                )
                            }
                        </tbody>
                    </table>
                </div>

            </div>
        );
    }



}
export default ListEstudianteComponent;