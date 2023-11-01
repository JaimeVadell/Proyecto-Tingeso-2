import React, { Component } from "react";
import EstudianteService from "../../services/EstudianteService";

class ListEstudianteComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            estudiantes: []
        }
        this.addestudiantes = this.addestudiantes.bind(this);
    }
    addEstudiante() {
        this.props.history.push('/add-estudiante/_add');
    }

}