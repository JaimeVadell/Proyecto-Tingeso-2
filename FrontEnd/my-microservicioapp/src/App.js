import React from 'react';
import './App.css';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom'
import HeaderComponent from './components/Common/HeaderComponent';
import ListEstudianteComponent from './components/Estudiante/ListEstudianteComponent';
import CreateEstudianteComponent from './components/Estudiante/CreateEstudianteComponent';
import CreateArancelComponent from './components/Arancel/CreateArancelComponent';
import ViewCuotasEstudiante from "./components/Cuotas/ViewCuotasEstudiante";
import ViewPagosComponent from "./components/Pagos/ViewPagosEstudianteComponent";
import ViewResumenEstudianteComponent from "./components/ResumenEstudiante/ViewResumenEstudianteComponent";


function App() {
  return (
    <div>
        <Router>
              <HeaderComponent />
                <div className="container">
                    <Switch> 
                          <Route path = "/" exact component = {ListEstudianteComponent}></Route>
                            <Route path = "/estudiantes" component = {ListEstudianteComponent}></Route>
                            <Route path = "/add-estudiante" component = {CreateEstudianteComponent}></Route>
                            <Route path = "/add-arancel" component = {CreateArancelComponent}></Route>
                            <Route path = "/cuotas/:rut" component = {ViewCuotasEstudiante}></Route>
                            <Route path = "/pagos/:rut" component = {ViewPagosComponent}></Route>
                            <Route path = "/resumen/:rut" component = {ViewResumenEstudianteComponent}></Route>

                    </Switch>
                </div>
        </Router>
    </div>
    
  );
}

export default App;
