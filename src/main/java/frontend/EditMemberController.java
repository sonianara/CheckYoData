package frontend;

import java.util.ArrayList;
import java.util.Arrays;

import backend.DatabaseCommunicator;
import backend.Member;
import javafx.event.ActionEvent;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Class to manage changes to member information
 * @author cbrown
 *
 */
public class EditMemberController {
	@FXML private TextField firstNameText; 
	@FXML private TextField lastNameText; 
	@FXML private TextField emailText; 
	@FXML private TextField phoneNumberText; 
	@FXML private TextField addressText; 
	@FXML private TextField cityText; 
	@FXML private ComboBox stateBox; 
	@FXML private TextField zipCodeText; 
	@FXML private Button saveButton; 
	@FXML private Button deleteButton; 
	
	
	private Member member; 
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
	
	/** 
	 * Method to auto-fill the fields in the edit member template
	 * Uses the member passed in from the MemberEntryController
	 */
	public void populateFields() {
		firstNameText.setText(member.getFirstName());
		lastNameText.setText(member.getLastName());
		emailText.setText(member.getEmail());
		phoneNumberText.setText(member.getPhoneNumber());
		addressText.setText(member.getAddress());
		cityText.setText(member.getCity());
		stateBox.setValue(member.getState());
		zipCodeText.setText(Integer.toString(member.getZipCode()));
	}

	/**
	 * Method to update the member's information in the database and system
	 * @param event necessary for javafx onAction
	 */
	@FXML
	public void saveMember(ActionEvent event) {
		String firstName = firstNameText.getText();  
		String lastName = lastNameText.getText(); 
		String email = emailText.getText();
		String phoneNumber = phoneNumberText.getText();
		String address = addressText.getText();
		String city = cityText.getText(); 
		String state = stateBox.getValue().toString();
		int zipCode = Integer.parseInt(zipCodeText.getText()); 
		String memberType = "active"; 		
		
		Member updatedMember = new Member(firstName, lastName, email, phoneNumber, address, city, state, zipCode, memberType); 

		DatabaseCommunicator.getInstance(); 
		DatabaseCommunicator.replaceDatabase(updatedMember);

		// update member list view
		controller.populateMembers();
		
		// close the new member form
		Stage currentStage = (Stage) saveButton.getScene().getWindow();
		currentStage.close();
	}
	
	/**
	 * Method to remove the member from the database and system
	 * @param event necessary for javafx onAction
	 */
	@FXML
	public void deleteMember(ActionEvent event) {
		DatabaseCommunicator.deleteFromDatabase(member, "'"+member.getPhoneNumber()+"'"); 

		// update member list view
		controller.populateMembers();
		
		// close the new member form
		Stage currentStage = (Stage) saveButton.getScene().getWindow();
		currentStage.close();
	}
	
	/**
	 * Method to specify which member is being edited
	 * @param member The Member that is being edited
	 */
	public void setMember(Member member) {
		this.member = member; 
	}
	
	/**
	 * Method used to update the view after the member is updated
	 * @param controller The controller that modifies the member list view 
	 */
	public void setController(MemberListController controller) {
		this.controller = controller; 
	}
}
