package frontend;

import java.util.ArrayList;
import java.util.Arrays;

import backend.Member;
import javafx.event.ActionEvent;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 * Class to manage changes to member information
 * @author cbrown
 *
 */
public class EditMemberController {
	@FXML private TextField firstName; 
	@FXML private TextField lastName; 
	@FXML private TextField email; 
	@FXML private TextField phoneNumber; 
	@FXML private TextField address; 
	@FXML private TextField city; 
	@FXML private ComboBox state; 
	@FXML private TextField zipCode; 
	@FXML private Button saveButton; 
	@FXML private Button deleteButton; 
	@FXML private CheckBox isActive; 
	
	
	private Member member = new Member(); 
	private MemberListController controller; 

	@FXML
	public void initialize() {
		
		ArrayList<String> states = new ArrayList<String>(Arrays.asList("AK","AL","AR","AZ","CA",
				"CO","CT","DC","DE","FL","GA","GU","HI","IA","ID", "IL","IN","KS","KY","LA","MA",
				"MD","ME","MH","MI","MN","MO","MS","MT","NC","ND","NE","NH","NJ","NM","NV","NY", 
				"OH","OK","OR","PA","PR","PW","RI","SC","SD","TN","TX","UT","VA","VI","VT","WA",
				"WI","WV","WY")); 
		state.setItems(FXCollections.observableArrayList(states)); 

		populateFields(); 
	}
	
	/** 
	 * Method to auto-fill the fields in the edit member template
	 * Uses the member passed in from the MemberEntryController
	 */
	private void populateFields() {
		//TODO fill this in
	}

	/**
	 * Method to update the member's information in the database and system
	 * @param event necessary for javafx onAction
	 */
	@FXML
	public void saveMember(ActionEvent event) {
		//TODO Fill this in
		Member updatedMember = new Member(); 
		// delete member from database
		// add the updated member
	}
	
	/**
	 * Method to remove the member from the database and system
	 * @param event necessary for javafx onAction
	 */
	@FXML
	public void deleteMember(ActionEvent event) {
		//TODO fill this in

	}
	
	public void setFirstName(String firstName) {
		this.member.setFirstName(firstName); 
	}
	
	public void setLastName(String lastName) {
		this.member.setLastName(lastName);
	}

	public void setPhoneNumber(String phoneNumber) {
		this.member.setPhoneNumber(phoneNumber);
	}
	
	public void setMemberType(String memberType) {
		this.member.setMemberType(memberType);
	}
	
	public void setController(MemberListController controller) {
		this.controller = controller; 
	}
}
