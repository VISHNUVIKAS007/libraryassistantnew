/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libraryassistantnew;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import library.assistant.database.DatabaseHandler;

/**
 * FXML Controller class
 *
 * @author Vishnu
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private JFXTextField title;
    @FXML
    private JFXTextField id;
    @FXML
    private JFXTextField author;
    @FXML
    private JFXTextField publication;
    @FXML
    private JFXButton saveBtn;
    @FXML
    private JFXButton cancel;

    /**
     * Initializes the controller class.
     * 
     * 
     */
    DatabaseHandler databaseHandler;
    @FXML
    private AnchorPane rootPane;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        databaseHandler = DatabaseHandler.getInstance();
        checkData();
    }    

    @FXML
    private void addBook(ActionEvent event) {
        String bookId= id.getText();
        String bookAuthor= author.getText();
        String bookTitle= title.getText();
        String bookPublisher= publication.getText();
        if(bookId.isEmpty()||bookAuthor.isEmpty()||bookTitle.isEmpty()||bookPublisher.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please enter in all fields");
            alert.showAndWait();
            return;
            
        }
        String qu="INSERT INTO BOOK VALUES ("+
            "'"+bookId+"'"+","+
            "'"+bookTitle+"'"+","+
            "'"+bookAuthor+"'"+","+
            "'"+bookPublisher+"'"+","+
            ""+true+""+

            ")";
            System.out.println(qu);
            if(databaseHandler.execAction(qu)){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Success");
                alert.showAndWait();
            
            }else{
                 Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Failiure");
                alert.showAndWait();
               
            }

        
    }

    @FXML
    private void cancel(ActionEvent event) {
        Stage stage =(Stage) rootPane.getScene().getWindow();
        stage.close();
    }

    private void checkData() {
       String qu ="SELECT title FROM BOOK";
        ResultSet rs= databaseHandler.executeQuery(qu);
        try {
            while(rs.next()){
                String titlex = rs.getString("title");
                System.err.println(titlex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
