package frontend;

import backend.DatabaseCommunicator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SearchMemberFormController {
   @FXML private TextField lastNameText;
   @FXML private TextField phoneNumberText;
   @FXML private TextField emailText;
   @FXML private Button searchButton;

   private MemberListController controller;

   @FXML
   public void searchButtonClick(ActionEvent event) {
      String lastName = lastNameText.getText();
      String phoneNumber = phoneNumberText.getText();
      String email = emailText.getText();

      DatabaseCommunicator.getInstance();

      String filter = "";
      // filter based on entered values
      if (!lastName.trim().isEmpty())
    	  filter += "last_name = '" + lastName + "'";
      if (!phoneNumber.trim().isEmpty()) {
    	  if (filter != "")
    		  filter += " and ";
    	  filter += "phone_number = '" + phoneNumber + "'";
      }
      if (!email.trim().isEmpty()) {
    	  if (filter != "")
    		  filter += " and ";
    	  filter += "email = '" + email + "'";
      }
      
      // if no filtering was entered, set the filter to null
      if (filter == "")
    	  filter = null;
      
      // update the member list view
      controller.getMemberList(filter, "last_name asc"); 
      controller.populateMembers();

      // close the new member form
      Stage currentStage = (Stage) searchButton.getScene().getWindow();
      currentStage.close();
   }

   public void setMemberListController(MemberListController controller) {
      this.controller = controller;
   }

}