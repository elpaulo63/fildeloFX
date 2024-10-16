package com.paul.filedelofx;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.image.ImageView;
import org.json.simple.parser.ParseException;

/**
 * FXML Controller class
 *
 * @author Paul
 */
public class PrimaryController implements Initializable {

    @FXML
    private ComboBox<Enseignant> userComboBox;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            userComboBox.setItems(SingletonWebServiceClient.getInstance().getAllEnseignantsAdmin());
        } catch (IOException | ParseException ex) {
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void clickconnexion(ActionEvent event) {
        try {
            System.out.println("prout");
            String login = userComboBox.getSelectionModel().getSelectedItem().getLogin();
            String mdp = passwordField.getText();
            Enseignant e = SingletonWebServiceClient.getInstance().identifier(login, mdp);
            App.setRoot("secondary");
        } catch (IOException | ParseException ex) {
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
            Alert alerte = new Alert(Alert.AlertType.ERROR);
            alerte.setContentText("connecte toi s'il te plaît p'tite merde");
            alerte.showAndWait();
        }
    }
    
}
