import React, { Component } from "react";
import EstudianteService from "../../services/EstudianteService";

class ListEstudianteComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            estudiantes: []
        }
        this.addestudiantes = this.addEstudiante.bind(this);
    }
    addEstudiante() {
        this.props.history.push('/add-estudiante/_add');
    }
    viewEstudiante(id) {
        this.props.history.push(`/view-estudiante/${id}`);
    }
    componentDidMount() {
        EstudianteService.getEstudiantes().then((res) => {
            this.setState({ estudiantes: res.data });
        });
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
                                                <button onClick={() => this.viewEstudiante(estudiante.id)} className="btn btn-info">View </button>
                                                <button style={{ marginLeft: "10px" }} onClick={() => this.editEstudiante(estudiante.id)} className="btn btn-info">Update </button>
                                                <button style={{ marginLeft: "10px" }} onClick={() => this.deleteEstudiante(estudiante.id)} className="btn btn-danger">Delete </button>
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