/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.assistant.ui.main;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.effects.JFXDepthManager;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
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
    @FXML
    private JFXTextField bookID;
    @FXML
    private ListView<String> issueDataList;
    Boolean isReadyForSubmission=false;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        JFXDepthManager.setDepth(bookInfo, 1);
        JFXDepthManager.setDepth(memberInfo, 1);
        databaseHandler = DatabaseHandler.getInstance();
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
    @FXML
    private void loadSettings2(ActionEvent event) {
        loadWindow("/library/assistant/settings/settings.fxml", "Settings");
    }
    

    void loadWindow(String loc, String title) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(loc));
            Stage stage = new Stage(StageStyle.DECORATED);
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
        String qu = "SELECT * FROM BOOK WHERE id=" + "'" + id + "'";
        ResultSet rs = databaseHandler.executeQuery(qu);
        Boolean flag = false;
        try {
            while (rs.next()) {
                String bName = rs.getString("title");
                String bAuthor = rs.getString("author");
                Boolean bStatus = rs.getBoolean("isAvail");
                bookName.setText(bName);
                bookAuthor.setText(bAuthor);
                String status = (bStatus) ? "Available" : "Not Available";
                bookAvail.setText(status);

                flag = true;
            }
            if (!flag) {
                bookName.setText("No such book!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void loadMemberInfo(ActionEvent event) {
        String id = memberInput.getText();
        String qu = "SELECT * FROM MEMBER WHERE id=" + "'" + id + "'";
        ResultSet rs = databaseHandler.executeQuery(qu);
        Boolean flag = false;
        try {
            while (rs.next()) {
                String mName = rs.getString("name");
                String mContact = rs.getString("mobile");
                memberName.setText(mName);
                memberContact.setText(mContact);
                flag = true;
            }
            if (!flag) {
                memberName.setText("No such Member!");
                memberContact.setText("");
            }

        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void loadIssueOperation(ActionEvent event) {
        String mid = memberInput.getText();
        String bid = bookIdInput.getText();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Issue");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to issue " + bookName.getText() + "\n" + "to " + memberName.getText());
        Optional<ButtonType> response = alert.showAndWait();
        if (response.get() == ButtonType.OK) {
            String str = "INSERT INTO ISSUE(memberID,bookID) VALUES ("
                    + "'" + mid + "',"
                    + "'" + bid + "'"
                    + ")";
            String str2 = "UPDATE BOOK SET isAvail= false WHERE id='" + bid + "'";
            System.err.println("str1 " + str + " str2 " + str2);
            if (databaseHandler.execAction(str) && databaseHandler.execAction(str2)) {
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("Book Issued");
                alert2.setHeaderText(null);
                alert2.setContentText("Book " + bid + " is issued to " + mid);
                alert2.showAndWait();
            } else {
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("ERROR OCCUERED");
                alert1.setHeaderText(null);
                alert1.setContentText("Error issuing the book " + bid);
                alert1.showAndWait();
            }
        } else {
            Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
            alert3.setTitle("Issue Not Done");
            alert3.setHeaderText(null);
            alert3.setContentText("Issue Canceled");
            alert3.showAndWait();
        }

    }

    @FXML
    private void loadBookInfo2(ActionEvent event) {
        //renewalor submission
        ObservableList<String> issueData = FXCollections.observableArrayList();
        isReadyForSubmission = false;
        String id = bookID.getText();
        String qu = "SELECT * FROM ISSUE WHERE bookID= '" + id + "' ";
        ResultSet rs = databaseHandler.executeQuery(qu);
        try {
            while (rs.next()) {
                String mBookID = id;
                String mMemberID = rs.getString("memberID");
                Timestamp mIssueTime = rs.getTimestamp("issueTime");
                int mRenewCount = rs.getInt("renew_count");

                issueData.add("issue date and time: " + mIssueTime.toGMTString());
                issueData.add("Renew count: " + mRenewCount);
                
                issueData.add("Book Information: ");

                qu = "SELECT * FROM BOOK WHERE id='" + mBookID + "'";
                ResultSet r1 = databaseHandler.executeQuery(qu);
                while (r1.next()) {
                    issueData.add("Book Name: "+r1.getString("title"));
                    issueData.add("Book ID: "+r1.getString("id"));
                    issueData.add("Book Author: "+r1.getString("author"));
                    issueData.add("Book Publication: "+r1.getString("publisher"));
                }
                issueData.add("Book Information: ");
                
                qu = "SELECT * FROM MEMBER WHERE id='" + mMemberID + "'";
                r1 = databaseHandler.executeQuery(qu);
                while (r1.next()) {
                    issueData.add("Member Name: "+r1.getString("name"));
                    issueData.add("Member ID: "+r1.getString("id"));
                    issueData.add("Member Contact: "+r1.getString("mobile"));
                    issueData.add("Member Email: "+r1.getString("email"));
                }
                isReadyForSubmission=true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        issueDataList.getItems().setAll(issueData);
    }

    @FXML
    private void loadSubmission(ActionEvent event) {
       if(!isReadyForSubmission){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Please select a book for submission!");
            alert.setContentText("");
            alert.showAndWait();
            return;
        }
        String id= bookID.getText();
        String ac1="DELETE FROM ISSUE WHERE bookID='"+id+"'";
        String ac2="UPDATE BOOK SET ISAVAIL= TRUE WHERE id='"+id+"'";
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Issue");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to SUBMIT ?");
        Optional<ButtonType> response = alert.showAndWait();
        if (response.get() == ButtonType.OK) {
        if(databaseHandler.execAction(ac1)&& databaseHandler.execAction(ac2)){
            Alert alert1=new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("Sucess");
            alert1.setHeaderText("Book has been submitted");
            alert1.setContentText("");
            alert1.showAndWait();
        }else{
            Alert alert1=new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Error");
            alert1.setHeaderText("Book submission failed!");
            alert1.setContentText("");
            alert1.showAndWait();
        }
        }
    }

    @FXML
    private void renewBookOp(ActionEvent event) {
        if(!isReadyForSubmission){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Please select a book for Renew!");
            alert.setContentText("");
            alert.showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Issue");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to RENEW ");
        Optional<ButtonType> response = alert.showAndWait();
        if (response.get() == ButtonType.OK) {
        String ac="UPDATE ISSUE SET issueTime = CURRENT_TIMESTAMP, renew_count=renew_count+1 WHERE bookID='"+bookID.getText()+"'";
            System.err.println(ac);
            if(databaseHandler.execAction(ac)){
                Alert alert1=new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("Sucess");
            alert1.setHeaderText("Book has been Renewed");
            alert1.setContentText("");
            alert1.showAndWait();
            }else{
                Alert alert1=new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Error");
            alert1.setHeaderText("Book renewal failed!");
            alert1.setContentText("");
            alert1.showAndWait();
            
            }
        }
        
    }

    

    

   
}
