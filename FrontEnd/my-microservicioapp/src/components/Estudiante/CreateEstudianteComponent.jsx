import {Component} from "react";
import EstudianteService from "../../services/EstudianteService/EstudianteService";

class CreateEstudianteComponent extends Component {
    constructor(props){
        super(props)

        this.state = {
            rut: '',
            nombre: '',
            apellido: '',
            fechaNacimiento: '',
            tipoColegio: '',
            nombreColegio: '',
            anioEgreso: ''
        }
        this.changeRutHandler = this.changeRutHandler.bind(this);
        this.changeNombreHandler = this.changeNombreHandler.bind(this);
        this.changeApellidoHandler = this.changeApellidoHandler.bind(this);
        this.changeFechaNacimientoHandler = this.changeFechaNacimientoHandler.bind(this);
        this.changeTipoColegioHandler = this.changeTipoColegioHandler.bind(this);
        this.changeNombreColegioHandler = this.changeNombreColegioHandler.bind(this);
        this.changeAnioEgresoHandler = this.changeAnioEgresoHandler.bind(this);
        this.saveEstudiante = this.saveEstudiante.bind(this);
    }
    saveEstudiante = (e) => {
        e.preventDefault();
        let estudiante = {
            rut: this.state.rut,
            nombre: this.state.nombre,
            apellido: this.state.apellido,
            fechaNacimiento: this.state.fechaNacimiento,
            tipoColegio: this.state.tipoColegio,
            nombreColegio: this.state.nombreColegio,
            anioEgreso: this.state.anioEgreso};
        console.log("Estudiante a enviar:", estudiante);
        EstudianteService.createEstudiante(estudiante).then(res => {
            this.props.history.push('/estudiantes');
        });
    }



    changeRutHandler = (event) => {
        this.setState({rut: event.target.value});
    }
    changeNombreHandler = (event) => {
        this.setState({nombre: event.target.value});
    }
    changeApellidoHandler = (event) => {
        this.setState({apellido: event.target.value});
    }
    changeFechaNacimientoHandler = (event) => {
        this.setState({fechaNacimiento: event.target.value});
    }
    changeTipoColegioHandler = (event) => {
        this.setState({tipoColegio: event.target.value});
    }
    changeNombreColegioHandler = (event) => {
        this.setState({nombreColegio: event.target.value});
    }
    changeAnioEgresoHandler = (event) => {
        this.setState({anioEgreso: event.target.value});
    }
    componentDidMount() {
        this.setState({
            tipoColegio: "MUNICIPAL"
        })
    }

    cancel(){
        this.props.history.push('/estudiantes');
    }
    render() {
        console.log(this.state); // Imprime el estado actual en la consola
        return (
            <div>
                <br></br>
                <div className="container">
                    <div className="row">
                        <div
                            className="card col-md-6 offset-md-3 offset-md-3">
                            <h3 className="text-center">Agregar Estudiante</h3>
                            <div className="card-body">
                                <form>
                                    <div className="form-group">
                                        <label> Rut: </label>
                                        <input placeholder="Rut" name="rut"
                                               className="form-control"
                                               value={this.state.rut}
                                               onChange={this.changeRutHandler}/>
                                    </div>
                                    <div className="form-group">
                                        <label> Nombre: </label>
                                        <input placeholder="Nombre" name="nombre"
                                               className="form-control"
                                               value={this.state.nombre}
                                               onChange={this.changeNombreHandler}/>
                                    </div>
                                    <div className="form-group">
                                        <label> Apellido: </label>
                                        <input placeholder="Apellido" name="apellido"
                                               className="form-control"
                                               value={this.state.apellido}
                                               onChange={this.changeApellidoHandler}/>
                                    </div>
                                    <div className="form-group">
                                        <label> Fecha de Nacimiento: </label>
                                        <input
                                            type="date" // Utilizamos un input de tipo "date" para la fecha
                                            name="fechaNacimiento"
                                            className="form-control"
                                            value={this.state.fechaNacimiento}
                                            onChange={this.changeFechaNacimientoHandler}/>
                                    </div>
                                    <div className="form-group">
                                        <label> Tipo de Colegio: </label>
                                        <select name="tipoColegio" className="form-control" value={this.state.tipoColegio} onChange={this.changeTipoColegioHandler}>
                                            <option value="MUNICIPAL">Municipal</option>
                                            <option value="SUBVENCIONADO">Subvencionado</option>
                                            <option value="PRIVADO">Privado</option>
                                        </select>
                                    </div>
                                    <div
                                        className="form-group">
                                        <label> Nombre de Colegio: </label>
                                        <input placeholder="Nombre de Colegio" name="nombreColegio"
                                               className="form-control"
                                               value={this.state.nombreColegio}
                                               onChange={this.changeNombreColegioHandler}/>
                                    </div>
                                    <div
                                        className="form-group">
                                        <label> Año de Egreso: </label>
                                        <input
                                            type="date" // Utilizamos un input de tipo "date" para la fecha
                                            name="anioEgreso"
                                            className="form-control"
                                            placeholder="Año de Egreso"
                                               value={this.state.anioEgreso}
                                               onChange={this.changeAnioEgresoHandler}/>
                                    </div>
                                    <button className="btn btn-success"
                                            onClick={this.saveEstudiante}>Guardar
                                    </button>
                                    <button className="btn btn-danger"
                                            onClick={this.cancel.bind(this)}
                                            style={{marginLeft: "10px"}}>Cancelar
                                    </button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        );

    }
}

export default CreateEstudianteComponent;
