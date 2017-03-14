package frontend;

import java.util.ArrayList;
import java.util.Arrays;

import backend.DatabaseCommunicator;
import backend.Member;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewMemberFormController {
	@FXML private TextField firstNameText; 
	@FXML private TextField lastNameText; 
	@FXML private TextField emailText; 
	@FXML private TextField phoneNumberText; 
	@FXML private TextField addressText; 
	@FXML private TextField cityText; 
	@FXML private ComboBox stateBox; 
	@FXML private TextField zipCodeText;
	@FXML private Button submitButton;
	
	private MemberListController controller; 

	@FXML
	public void initialize() {
		ArrayList<String> states = new ArrayList<String>(Arrays.asList("AK","AL","AR","AZ","CA",
				"CO","CT","DC","DE","FL","GA","GU","HI","IA","ID", "IL","IN","KS","KY","LA","MA",
				"MD","ME","MH","MI","MN","MO","MS","MT","NC","ND","NE","NH","NJ","NM","NV","NY", 
				"OH","OK","OR","PA","PR","PW","RI","SC","SD","TN","TX","UT","VA","VI","VT","WA",
				"WI","WV","WY")); 
		stateBox.setItems(FXCollections.observableArrayList(states)); 
	}
	
	@FXML 
	public void submitButtonClick(ActionEvent event) {
		String firstName = firstNameText.getText();  
		String lastName = lastNameText.getText(); 
		String email = emailText.getText();
		String phoneNumber = phoneNumberText.getText();
		String address = addressText.getText();
		String city = cityText.getText(); 
		String state = stateBox.getValue().toString();
		int zipCode = Integer.parseInt(zipCodeText.getText()); 
		String memberType = "active"; 

		Member newMember = new Member(firstName, lastName, email, phoneNumber, address, city, state, zipCode, memberType); 
		
		DatabaseCommunicator.getInstance(); 
		DatabaseCommunicator.addToDatabase(newMember);
		
		// update the member list view
		controller.populateMembers(null);
		
		// close the new member form
		Stage currentStage = (Stage) submitButton.getScene().getWindow();
        currentStage.close();
	}
	
	public void setMemberListController(MemberListController controller) {
		this.controller = controller; 
	}
	
}
