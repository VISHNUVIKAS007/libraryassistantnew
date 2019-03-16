/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.assistant.ui.addmember;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import library.assistant.database.DatabaseHandler;

/**
 * FXML Controller class
 *
 * @author Vishnu
 */
public class Member_addController implements Initializable {
    DatabaseHandler handler ;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private JFXTextField name;
    @FXML
    private JFXTextField id;
    @FXML
    private JFXTextField mobile;
    @FXML
    private JFXTextField email;
    @FXML
    private JFXButton saveBtn;
    @FXML
    private JFXButton cancel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        handler= DatabaseHandler.getInstance();
    }    

    @FXML
    private void addMember(ActionEvent event) {
        String mName=name.getText();
        String mId=id.getText();
        String mMobile=mobile.getText();
        String mEmail=email.getText();
        
        Boolean flag = mName.isEmpty()||mId.isEmpty()||mMobile.isEmpty()||mEmail.isEmpty();
        if(flag){    
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please enter in all fields");
            alert.showAndWait();
            return;
        }
        String st= "INSERT INTO MEMBER VALUES ("
                +"'"+mId+"'"+","
                +"'"+mName+"'"+","
                +"'"+mMobile+"'"+","
                +"'"+mEmail+"'"
                +")";
        System.out.println(st);
        if(handler.execAction(st)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("SAVED");
            alert.showAndWait();
            //return;
        }else{
             Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("ERROR");
            alert.showAndWait();
            //return;
        }
    }

    @FXML
    private void cancel(ActionEvent event) {
    }
    
}
