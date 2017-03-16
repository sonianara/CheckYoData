package frontend;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import backend.DatabaseCommunicator;
import backend.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class NewAccountFormController {
   @FXML private TextField usernameText;
   @FXML private PasswordField passwordText;
   @FXML private Button createAccountButton;

   private MemberListController controller;

   @FXML
   public void createAccountButtonClick(ActionEvent event) {
      Alert alertTakenUser = new Alert(AlertType.INFORMATION);
      alertTakenUser.setContentText("Username is already taken");
      Alert alertUsername = new Alert(AlertType.INFORMATION);
      alertUsername.setContentText("Username is not valid");
      Alert alertPassword = new Alert(AlertType.INFORMATION);
      alertPassword.setContentText("Password is not valid");
      String username = usernameText.getText();
      String password = passwordText.getText();
      User newUser = new User(username, password);
      
      // check if the username and password are whitespace
      if (username.trim().isEmpty())
         alertUsername.showAndWait();
      else if (password.trim().isEmpty())
         alertPassword.showAndWait();
      else {
         DatabaseCommunicator.getInstance();

         // check to see if the username already exists
         String query = "SELECT * from users where username = '" + username + "';";
         List<HashMap<String, Object>> rows = DatabaseCommunicator.queryDatabase(
               query);
         
         if (rows.size() > 0)
            alertTakenUser.showAndWait();
         else {
            DatabaseCommunicator.addToDatabase(newUser);
            // close the new member form
            Stage currentStage = (Stage) createAccountButton.getScene().getWindow();
            currentStage.close();
         }
         
      }
   }

   public void setMemberListController(MemberListController controller) {
      this.controller = controller;
   }

}