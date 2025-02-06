package view.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 *
 * @author RafaelNunes
 */
public class FXMLTelaPrincipalController implements Initializable {
    
    @FXML
    private Button buttonSocio;
    
    @FXML
    private Button buttonTiket;
    
    @FXML
    private Button buttonValidacao;

    @FXML
    private void testeButton() {
        System.out.println("Teste Button");
    }  

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
}
