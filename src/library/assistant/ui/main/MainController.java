/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.assistant.ui.main;

import com.jfoenix.effects.JFXDepthManager;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import library.assistant.database.DatabaseHandler;

/**
 * FXML Controller class
 *
 * @author Vishnu
 */
public class MainController implements Initializable {

    @FXML
    private HBox bookInfo;
    @FXML
    private HBox memberInfo;
    @FXML
    private TextField bookIdInput;
    @FXML
    private Text bookName;
    @FXML
    private Text bookAuthor;
    @FXML
    private Text bookAvail;
    @FXML
    private Text memberName;
    @FXML
    private Text memberContact;
    DatabaseHandler databaseHandler;
    @FXML
    private TextField memberInput;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        JFXDepthManager.setDepth(bookInfo, 1);
        JFXDepthManager.setDepth(memberInfo, 1);
        databaseHandler=DatabaseHandler.getInstance();
    }    

    @FXML
    private void loadAddMember(ActionEvent event) {
        loadWindow("/library/assistant/ui/addmember/member_add.fxml", "Add Member");
    }


    @FXML
    private void loadAddBook(ActionEvent event) {
        loadWindow("/library/assistant/ui/addbook/add_book.fxml", "Add Book");
    }

    @FXML
    private void loadMemberTable(ActionEvent event) {
        loadWindow("/library/assistant/ui/listmember/member_list.fxml", "Member Table");
    }

    @FXML
    private void loadBookTable(ActionEvent event) {
        loadWindow("/library/assistant/ui/listbook/book_list.fxml", "Book Table");
    }
    void loadWindow(String loc,String title){
        try {
            Parent parent=FXMLLoader.load(getClass().getResource(loc));
            Stage stage= new Stage(StageStyle.DECORATED);
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void loadBookInfo(ActionEvent event) {
        String id = bookIdInput.getText();
        String qu = "SELECT * FROM BOOK WHERE id="+"'"+id+"'";
        ResultSet rs= databaseHandler.executeQuery(qu);
        Boolean flag=false;
        try {
            while(rs.next()){
                String bName = rs.getString("title");
                String bAuthor = rs.getString("author");
                Boolean bStatus =rs.getBoolean("isAvail");
                bookName.setText(bName);
                bookAuthor.setText(bAuthor);
                String status= (bStatus)?"Available":"Not Available";
                bookAvail.setText(status);
                
                flag=true;
            }
            if(!flag){
                bookName.setText("No such book!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void loadMemberInfo(ActionEvent event) {
        String id = memberInput.getText();
        String qu = "SELECT * FROM MEMBER WHERE id="+"'"+id+"'";
        ResultSet rs= databaseHandler.executeQuery(qu);
        Boolean flag=false;
        try {
            while(rs.next()){
                String mName = rs.getString("name");
                String mContact = rs.getString("mobile");
                memberName.setText(mName);
                memberContact.setText(mContact);
                flag=true;
            }
            if(!flag){
                memberName.setText("No such Member!");
                memberContact.setText("");
            }
         
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
