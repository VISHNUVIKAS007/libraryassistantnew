/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.assistant.settings;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Vishnu
 */
public class SettingsController implements Initializable {

    @FXML
    private JFXTextField nDaysWithoutFine;
    @FXML
    private JFXTextField finePerDay;
    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField password;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initDefaultValues();
    }    

    @FXML
    private void handleSaveBtnAction(ActionEvent event) {
        int nDays =Integer.parseInt(nDaysWithoutFine.getText());
        float fine= Float.parseFloat(finePerDay.getText());
        String uname= username.getText();
        String pword=password.getText();
        
        Preferences preferences = Preferences.getPreferences();
        preferences.setnDaysWithoutFine(nDays);
        preferences.setFinePerDay(fine);
        preferences.setUsername(uname);
        preferences.setPassword(pword);
        
        Preferences.writePreferencesToFile(preferences);
    }

    @FXML
    private void handleCancelBtnAction(ActionEvent event) {
        ((Stage)nDaysWithoutFine.getScene().getWindow()).close();
    }

    private void initDefaultValues() {
       Preferences preferences = Preferences.getPreferences();
       nDaysWithoutFine.setText(String.valueOf(preferences.getnDaysWithoutFine()));
       finePerDay.setText(String.valueOf(preferences.getFinePerDay()));
       username.setText(String.valueOf(preferences.getUsername()));
       password.setText(String.valueOf(preferences.getPassword()));
    }
    
}
