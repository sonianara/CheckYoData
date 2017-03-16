package frontend;

import backend.DatabaseCommunicator;
import backend.GymClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewClassFormController {
  @FXML private TextField classIDText; 
  @FXML private TextField classNameText; 
  @FXML private TextField startTimeText; 
  @FXML private TextField endTimeText; 
  @FXML private TextField yearText;
  @FXML private TextField monthText;
  @FXML private TextField dayText;
  @FXML private TextField roomText; 
  @FXML private TextField capacityText;
  @FXML private Button submitButton; 
  
  private ClassListController controller; 

  @FXML 
  public void submitButtonClick(ActionEvent event) {
    String classID = classIDText.getText().toString();  
    String className = classNameText.getText().toString(); 
    String startTime = startTimeText.getText().toString();
    String endTime = endTimeText.getText().toString();
    String date = yearText.getText() + "-" + monthText.getText() + "-" + dayText.getText();
    String room = roomText.getText().toString(); 
    int capacity = Integer.parseInt(capacityText.getText());

    GymClass newClass = new GymClass(classID, className, startTime, endTime, date, room, capacity, 0); 
      
    DatabaseCommunicator.getInstance(); 
    DatabaseCommunicator.addToDatabase(newClass);
    
    // update the member list view
    controller.initialize();
    
    // close the new class form
    Stage currentStage = (Stage) submitButton.getScene().getWindow();
    currentStage.close();
  }
  


  public void setClassListController(ClassListController classListController) {
    this.controller = classListController;
    
  }

}
