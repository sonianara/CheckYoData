package frontend;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import backend.Member;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage; 

/**
 * Class to manage the members in the system.
 * Supports viewing, adding, editing, and deleting members
 * @author cbrown
 *
 */
public class MemberListController {
	@FXML private VBox memberContainer; 
	@FXML private Button newMemberButton; 
	@FXML private Button searchButton; 
	
	List<Member> members = new ArrayList<Member>(); 
	
	@FXML
	public void initialize() {
		populateMembers(); 
	}
	
	/**
	 * Method to populate the member list with database table
	 */
	private void populateMembers() {
		// populate member list from back end
		
		//SAMPLE DATA ONLY
		Member mem1 = new Member("Courtney", "Brown", "cbrown83@calpoly.edu", "4256818114", "2909 233rd Ave SE", "Sammamish", "WA", 98075, "rec", true); 
		Member mem2 = new Member("Brandon", "Leon", "brleon@calpoly.edu", "8182319169", "7357 Genesta Ave", "Van Nuys", "CA", 91406, "rec", true); 
		members.add(mem1); 
		members.add(mem2); 
		// TODO(Courtney) Remove once hooked up to backend 
		
		memberContainer.getChildren().clear();

		addLabelsToContainer(); 
		
		for (int i = 0; i < members.size(); i++) {
			addMemberToContainer(members.get(i)); 
		}
		
	}
	
	/**
	 * Method to add the labels to the member container
	 * Format: 
	 * LAST NAME		FIRST NAME		PHONE NUMBER		MEMBER TYPE
	 */
	private void addLabelsToContainer() {
		Pane newPane = null;
		
		FXMLLoader loader = null;
		try {
			loader = new FXMLLoader(getClass().getResource("MemberEntry.fxml"));
			newPane = (Pane) loader.load();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		memberContainer.getChildren().add(newPane);
		newPane.getChildren().remove(newPane.lookup("#editButton"));
	}

	/**
	 * Method to add members to the member container
	 * @param member one member from the list of members acquired from the database
	 */
	private void addMemberToContainer(Member member) {
		// create a new "pane"
		Pane newPane = null;
		
		FXMLLoader loader = null;
		try {
			loader = new FXMLLoader(getClass().getResource("MemberEntry.fxml"));
			newPane = (Pane) loader.load();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		// Sets the value of the MemberEntryController so it knows which member to modify
		MemberEntryController memberController = loader.<MemberEntryController>getController();
		memberController.setMember(member);
		memberController.setMemberController(this);
		
	// set the values for the pane
		memberContainer.getChildren().add(newPane);
		Label firstName = (Label) newPane.lookup("#firstName");
		Label lastName = (Label) newPane.lookup("#lastName");
		Label phoneNumber = (Label) newPane.lookup("#phoneNumber");
		Label memberType = (Label) newPane.lookup("#memberType");
		
		firstName.setText(member.getFirstName());
		lastName.setText(member.getLastName());
		phoneNumber.setText(member.getPhoneNumber());
		memberType.setText(member.getMemberType());
	}
	
	/**
	 * Method to open a new member form upon clicking the "Add Member" button
	 * @param event necessary for javafx onAction
	 * @throws IOException
	 */
	@FXML
	public void addMemberButtonClick(ActionEvent event) throws IOException {
		String fxmlFile = "NewMemberForm.fxml"; 
    	Stage stage = new Stage(); 
    	Pane myPane = null; 
    	FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
    	myPane = (Pane) loader.load(); 
    	Scene scene = new Scene(myPane); 
    	stage.setScene(scene);
    	stage.show(); 
	}
	
	/**
	 * Method to search the member directory by the specified parameter
	 * Supports search by last name, phone number, or email
	 * @param event necessary for javafx onAction
	 * @throws IOException
	 */
	@FXML
	public void searchButtonClick(ActionEvent event) throws IOException {
		// TODO (Courtney) Urgent fill this in
	}
}
