package frontend;

import java.io.IOException;

import backend.GymClass;
import backend.DatabaseCommunicator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Class that manages class information 
 * @author sonianarayanan
 *
 */
public class ClassEntryController {
  GymClass gymClass; 
  ClassListController classListController; 
  
  @FXML private Label classID; 
  @FXML private Label className; 
  @FXML private Label startTime; 
  @FXML private Label endTime; 
  @FXML private Label days; 
  @FXML private Label room; 
  @FXML private Button editButton; 

  /**
   * Method to inform the EditClassController which class it is editing
   * @param event that identifies which action is being performed
   */
  @FXML
  private void editAction(ActionEvent event) throws IOException { 
      Stage stage = new Stage();
      Pane myPane = null; 

      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EditClassForm.fxml"));     
      myPane = (Pane)fxmlLoader.load();

      // Sets values so the ResourceEntryController knows which course it contains.
      EditClassController editClassController = fxmlLoader.<EditClassController>getController();
      DatabaseCommunicator.getInstance(); 
      editClassController.setClass(DatabaseCommunicator.getGymClass(classID.getText())); 
      editClassController.setController(classListController);

      
      editClassController.populateFields();
  
      Scene scene = new Scene(myPane); 
      stage.setScene(scene);    
      stage.show();   
  }
  
  public void setClass(GymClass gymClass) {
      this.gymClass = gymClass; 
  }
  
  public void setClassController(ClassListController controller) {
      this.classListController = controller; 
  }

}
