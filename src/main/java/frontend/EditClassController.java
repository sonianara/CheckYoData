package frontend;

import backend.DatabaseCommunicator;
import backend.GymClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditClassController {
  @FXML private TextField classIDText; 
  @FXML private TextField classNameText; 
  @FXML private TextField startTimeText; 
  @FXML private TextField endTimeText; 
  @FXML private TextField monthText;
  @FXML private TextField dayText;
  @FXML private TextField yearText;
  @FXML private TextField roomText; 
  @FXML private TextField capacityText;
  @FXML private Button saveButton; 
  @FXML private Button deleteButton; 
  
  private GymClass gymClass; 
  private ClassListController controller; 

  public void setController(ClassListController classListController) {
    this.controller = classListController;
    
  }

  public void setClass(GymClass gymClass) {
    this.gymClass = gymClass;
    
  }
  
  @FXML
  public void saveClass(ActionEvent event) {
      String classID = classIDText.getText();  
      String className = classNameText.getText(); 
      String startTime = startTimeText.getText();
      String endTime = endTimeText.getText();
      String date = yearText.getText() + "-" + monthText.getText() + "-" + dayText.getText();
      String room = roomText.getText();
      int capacity = Integer.parseInt(capacityText.getText());
           
      GymClass updatedClass = new GymClass(classID, className, startTime, endTime, date, room, capacity, gymClass.getReserved()); 

      DatabaseCommunicator.getInstance(); 
      DatabaseCommunicator.replaceDatabase(updatedClass);

      // update class list view
      controller.initialize();

      // close the new class form
      Stage currentStage = (Stage) saveButton.getScene().getWindow();
      currentStage.close();
  }
  
  @FXML
  public void deleteClass(ActionEvent event) {
      System.out.println("the class id: " + gymClass.getClassID());
      DatabaseCommunicator.deleteFromDatabase(gymClass, "'"+gymClass.getClassID()+"'"); 

      // update class list view
      controller.populateClasses();
      
      // close the new class form
      Stage currentStage = (Stage) saveButton.getScene().getWindow();
      currentStage.close();
  }

  public void populateFields() {
    classIDText.setText(gymClass.getClassID());
    classNameText.setText(gymClass.getName());
    startTimeText.setText(gymClass.getStartTime());
    endTimeText.setText(gymClass.getEndTime());
    
    String[] splitDate = gymClass.getDate().split("-"); 
    yearText.setText(splitDate[0]);
    monthText.setText(splitDate[1]);
    dayText.setText(splitDate[2]);
    roomText.setText(gymClass.getRoom());
    capacityText.setText(Integer.toString(gymClass.getCapacity()));
  }

}
