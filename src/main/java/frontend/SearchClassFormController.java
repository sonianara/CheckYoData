package frontend;

import backend.DatabaseCommunicator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SearchClassFormController {
   @FXML private TextField classIDText;
   @FXML private Button searchButton;

   private ClassListController controller;

   @FXML
   public void searchButtonClick(ActionEvent event) {
      String classID = classIDText.getText();

      DatabaseCommunicator.getInstance();

      String filter = "";
      // filter based on entered values
      if (!classID.trim().isEmpty())
          filter += "classID = '" + classID + "'";
      
      // if no filtering was entered, set the filter to null
      if (filter == "")
          filter = null;
      
      // update the member list view
      controller.getClassList(filter); 
      controller.populateClasses();

      // close the new member form
      Stage currentStage = (Stage) searchButton.getScene().getWindow();
      currentStage.close();
   }

   public void setClassListController(ClassListController controller) {
      this.controller = controller;
   }

}