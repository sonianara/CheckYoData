package frontend;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import backend.DatabaseCommunicator;
import backend.GymClass;
import frontend.ClassEntryController;
import frontend.NewClassFormController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * This class fetches class information from the database and populates the front-end list 
 * @author sonianarayanan
 *
 */
public class ClassListController {
  List<GymClass> classes = new ArrayList<GymClass>(); 
  
  @FXML private VBox classContainer; 
  @FXML private Button newClassButton; 
  @FXML private Button searchButton; 
  
  @FXML
  public void initialize() {
      populateClasses(); 
  }
  
  /**
   * Method to populate the class list with database table
   */
  public void populateClasses() {
    
      getClassList(); 
      
      classContainer.getChildren().clear();

      addLabelsToContainer(); 
      
      for (int i = 0; i < classes.size(); i++) {
          addClassToContainer(classes.get(i)); 
      }
  }
  
  /**
   * Method to add the labels to the class container
   * Format: 
   * CLASS ID  CLASS NAME   START TIME  END TIME   DAYS   ROOM
   */
  private void addLabelsToContainer() {
      Pane newPane = null;
      
      FXMLLoader loader = null;
      try {
          loader = new FXMLLoader(getClass().getResource("ClassEntry.fxml"));
          newPane = (Pane) loader.load();
      } 
      catch (IOException e) {
          e.printStackTrace();
      }
      classContainer.getChildren().add(newPane);
      newPane.getChildren().remove(newPane.lookup("#editButton"));
  }

  /**
   * Method to add class to the class container
   * @param add one class from the list of class acquired from the database
   */
  public void addClassToContainer(GymClass gymClass) {
      // create a new "pane"
      Pane newPane = null;
      
      FXMLLoader loader = null;
      try {
          loader = new FXMLLoader(getClass().getResource("ClassEntry.fxml"));
          newPane = (Pane) loader.load();
      } 
      catch (IOException e) {
          e.printStackTrace();
      }
      
      // Sets the value of the ClassEntryController so it knows which class to modify
      ClassEntryController classController = loader.<ClassEntryController>getController();
      classController.setClass(gymClass);
      classController.setClassController(this);
      
  // set the values for the pane
      classContainer.getChildren().add(newPane);
      Label classID = (Label) newPane.lookup("#classID");
      Label name = (Label) newPane.lookup("#className");
      Label startTime = (Label) newPane.lookup("#startTime");
      Label endTime = (Label) newPane.lookup("#endTime");
      Label days = (Label) newPane.lookup("#days");
      Label room = (Label) newPane.lookup("#room");
      
      classID.setText(gymClass.getClassID());
      name.setText(gymClass.getName());
      startTime.setText(gymClass.getStart());
      endTime.setText(gymClass.getEnd());
      days.setText(gymClass.getDays());
      room.setText(gymClass.getRoom());
  }
  
  /**
   * Method to open a new class form upon clicking the "Add Class" button
   * @param event necessary for javafx onAction
   * @throws IOException
   */
  @FXML
  public void addClassButtonClick(ActionEvent event) throws IOException {
      String fxmlFile = "NewClassForm.fxml"; 
      Stage stage = new Stage(); 
      Pane myPane = null; 
      FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
      myPane = (Pane) loader.load(); 
      NewClassFormController classController = loader.<NewClassFormController>getController();
      classController.setClassListController(this);
      Scene scene = new Scene(myPane); 
      stage.setScene(scene);
      stage.show(); 
  }
  
  /**
   * Search for a class 
   * @param javafx action event 
   * @throws IOException
   */
  @FXML
  public void searchButtonClick(ActionEvent event) throws IOException {
      //TO-DO
  }
  
  /**
   * fetch classes from the classes table 
   */
  private void getClassList() {
      classes.clear();
      DatabaseCommunicator.getInstance();
      // populate class list from back end
      List<HashMap<String, Object>> rows = DatabaseCommunicator.queryDatabase(
              "SELECT * from classes");
      for (HashMap<String, Object> row : rows) {
          GymClass gymClass = new GymClass(); 
          gymClass.setClassID(row.get("classID").toString());
          gymClass.setName(row.get("name").toString());
          gymClass.setStart(row.get("startTime").toString());
          gymClass.setEnd(row.get("endTime").toString());
          gymClass.setDays(row.get("days").toString());
          gymClass.setRoom(row.get("room").toString());
          this.classes.add(gymClass); 
      }
  }
  
}
