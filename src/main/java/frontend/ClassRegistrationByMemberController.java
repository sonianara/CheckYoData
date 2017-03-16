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

public class ClassRegistrationByMemberController {
	@FXML TextField memberName; 
	@FXML ComboBox dateBox; 
	@FXML ComboBox classTypeBox; 
	@FXML ComboBox classNameBox; 
	@FXML ComboBox timeBox; 
	@FXML Button registerButton; 
	
	Member member;
	
	@FXML 
	public void initialize() {
		List<HashMap<String, Object>> rows = DatabaseCommunicator.queryDatabase("SELECT DISTINCT date FROM classes");
		ArrayList<String> dates = new ArrayList<String>(); 
		for (HashMap<String, Object> row : rows) {
			dates.add(row.get("date").toString()); 
		}
		System.out.println(dates.toString());
		dateBox.setItems(FXCollections.observableArrayList(dates));
	}
	
	@FXML 
	public void populateClassNames() {
		String query = "SELECT DISTINCT name FROM classes WHERE date='" + dateBox.getValue() + "';"; 
		List<HashMap<String, Object>> rows = DatabaseCommunicator.queryDatabase(query);
		ArrayList<String> names = new ArrayList<String>(); 
		for (HashMap<String, Object> row : rows) {
			names.add(row.get("name").toString()); 
		}
		classNameBox.setItems(FXCollections.observableArrayList(names));
	}
	
	@FXML 
	public void populateTimes() {
		String query = "SELECT DISTINCT startTime FROM classes WHERE date='" + dateBox.getValue() + "' AND name='" + classNameBox.getValue() + "';"; 
		List<HashMap<String, Object>> rows = DatabaseCommunicator.queryDatabase(query);
		ArrayList<String> times = new ArrayList<String>(); 
		for (HashMap<String, Object> row : rows) {
			times.add(row.get("startTime").toString()); 
		}
		timeBox.setItems(FXCollections.observableArrayList(times));

	}
	
	@FXML
	public void register(ActionEvent event) {
		GymClass gymClass = getGymClass(); 

		// reserve a new spot
		if (gymClass.getReserved() < gymClass.getCapacity()) {
			Reservation reservation = new Reservation(gymClass, member); 
			reservation.addToDatabase(); 
			Alert alertSuccess = new Alert(AlertType.INFORMATION);
		    alertSuccess.setContentText("Class was reserved for: \n" + member.getFirstName() + " " + member.getLastName());
			alertSuccess.showAndWait(); 
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
	
	private GymClass getGymClass() {
		String query = "SELECT classID FROM classes WHERE date='" + dateBox.getValue() + 
				"' AND name='" + classNameBox.getValue() + "' AND startTime='" + timeBox.getValue() + "';"; 
		System.out.println(query);
		List<HashMap<String, Object>> classList = DatabaseCommunicator.queryDatabase(query); 
		String classID = classList.get(0).get("classID").toString(); 
		GymClass gymClass = DatabaseCommunicator.getGymClass(classID); 

		return gymClass;
	}
	
	public void setMember(Member member) {
		this.member = member; 
	}
}
