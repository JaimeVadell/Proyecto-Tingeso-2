import {Component} from "react";
import PruebaService from "../../services/GestorAcademicoService/PruebaService";

class CreatePruebaComponent extends Component {
    constructor(props){
        super(props)
        this.state = {
            selectedFile: null
        }
        this.handleFileUpload = this.handleFileUpload.bind(this);
        this.submitFile = this.submitFile.bind(this);
    }

    handleFileUpload(event) {
        this.setState({ selectedFile: event.target.files[0] });
    }

    async submitFile() {
        if (this.state.selectedFile) {
            const formData = new FormData();
            formData.append('archivo', this.state.selectedFile);

            try {
                const response = await PruebaService.createPruebaDocument(formData);
                console.log(response.status); // You can check the status here
                if(response.status === 200) {
                    alert("Archivo subido exitosamente");
                }
                else {
                    alert("Ocurrio un error al subir el archivo");
                }

            } catch (error) {
                console.error(error);
            }
        }
    }

    render() {
        return (
            <div style={{ width: '50%', margin: '0 auto', textAlign: 'center' }}>
                <h1>Subir Examen</h1>
                <p>Por favor elige un documento para revisar:</p>
                <div className="input-group mb-3">
                    <div className="custom-file">
                        <input type="file" className="custom-file-input" id="inputGroupFile01" accept=".xlsx,.xls" onChange={this.handleFileUpload} />
                    </div>
                </div>
                <button className="btn btn-success" style={{ marginTop: '10px' }} onClick={this.submitFile}>Subir Archivo</button>
            </div>
        )
    }
}

export default CreatePruebaComponent;