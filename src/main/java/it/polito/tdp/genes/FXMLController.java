/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.genes;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.genes.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model ;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnContaArchi"
    private Button btnContaArchi; // Value injected by FXMLLoader

    @FXML // fx:id="btnRicerca"
    private Button btnRicerca; // Value injected by FXMLLoader

    @FXML // fx:id="txtSoglia"
    private TextField txtSoglia; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doContaArchi(ActionEvent event) {

    	double soglia = 0.0;
    	try {
    		soglia = Double.parseDouble(txtSoglia.getText());
    	}catch(NumberFormatException e) {
    		txtResult.appendText("ERRORE: La soglia inserita deve essere un valore compreso tra un minimo di " + this.model.getPesoMinimo() + " e un massimo di "+ this.model.getPesoMassimo()+"\n");
    	    return;
    	}
    	txtResult.appendText("Soglia = " + soglia + " ---> " + "Maggiori " + this.model.contaArchiMaggiori(soglia) +", minori " + this.model.contaArchiMinori(soglia) + "\n");
    	
    	
    }

    @FXML
    void doRicerca(ActionEvent event) {
        txtResult.clear();
        double soglia = 0.0;
     	try {
     		soglia = Double.parseDouble(txtSoglia.getText());
     	}catch(NumberFormatException e) {
     		txtResult.appendText("ERRORE: La soglia inserita deve essere un valore compreso tra un minimo di " + this.model.getPesoMinimo() + " e un massimo di "+ this.model.getPesoMassimo()+"\n");
     	    return;
     	}
        List<Integer> percorso = this.model.trovaPercorso(soglia);
    	for(Integer chromosome: percorso) {
    		txtResult.appendText(chromosome + "\n");
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnContaArchi != null : "fx:id=\"btnContaArchi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnRicerca != null : "fx:id=\"btnRicerca\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtSoglia != null : "fx:id=\"txtSoglia\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model ;
		this.model.creaGrafo();
		
		txtResult.appendText("Grafo creato!\n");
    	txtResult.appendText("#VERTICI: "+ this.model.nVertici()+"\n");
    	txtResult.appendText("#ARCHI: "+ this.model.nArchi()+"\n");
		
    	txtResult.appendText("Peso minimo = " + model.getPesoMinimo() + "\n");
    	txtResult.appendText("Peso massimo = " + model.getPesoMassimo()+ "\n");
	}
}
