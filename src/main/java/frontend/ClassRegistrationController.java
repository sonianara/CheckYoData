package frontend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import backend.DatabaseCommunicator;
import backend.GymClass;
import backend.Member;
import backend.Reservation;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ClassRegistrationController {
  @FXML TextField classID;
  @FXML ComboBox memberBox;
  @FXML Button registerButton; 
  
  private GymClass gymClass;
  private ClassListController classListController; 
  
  @FXML 
  public void initialize() {
    List<HashMap<String, Object>> rows = DatabaseCommunicator.queryDatabase("SELECT email FROM members");
    ArrayList<String> emails = new ArrayList<String>(); 
    for (HashMap<String, Object> row : rows) {
      emails.add(row.get("email").toString()); 
    }
    memberBox.setItems(FXCollections.observableArrayList(emails));
  }
  
  @FXML
  public void register(ActionEvent event) {
    Member member = getMember(); 

    // reserve a new spot
    if (gymClass.getReserved() < gymClass.getCapacity()) {
      Reservation reservation = new Reservation(gymClass, member); 
      reservation.addToDatabase(); 
      Alert alertSuccess = new Alert(AlertType.INFORMATION);
        alertSuccess.setContentText("Class was reserved for: \n" + member.getFirstName() + " " + member.getLastName());
      alertSuccess.showAndWait(); 
      
      classListController.initialize(); 
    }
    
    // class is full
    else {
      Alert alertClassFull = new Alert(AlertType.INFORMATION);
      alertClassFull.setContentText("Class is full"); 
      alertClassFull.showAndWait();
    }
    
    Stage currentStage = (Stage) registerButton.getScene().getWindow();
        currentStage.close();
  }
  
  private Member getMember() {
    String query = "SELECT phone_number FROM members WHERE email = '" + memberBox.getValue() + "';";
    List<HashMap<String, Object>> memberList = DatabaseCommunicator.queryDatabase(query);
    String phone_number = memberList.get(0).get("phone_number").toString(); 
    Member member = DatabaseCommunicator.getMember(phone_number); 

    return member;
  }
  
  public void setGymClass(GymClass gymClass) {
    this.gymClass = gymClass; 
  }
  
  public void setClassListController(ClassListController controller) {
	  this.classListController = controller; 
  }
}
