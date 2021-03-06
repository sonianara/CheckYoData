package frontend;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import backend.DatabaseCommunicator;
import backend.Member;
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
 * Class to manage the members in the system.
 * Supports viewing, adding, editing, and deleting members
 * @author cbrown
 *
 */
public class MemberListController {
	@FXML private VBox memberContainer; 
	@FXML private Button newMemberButton; 
	@FXML private Button searchButton; 
	@FXML private Button sortFirstNameButton; 
	@FXML private Button sortLastNameButton; 
	
	private boolean firstAscending = true; 
	private boolean lastAscending = true; 
	
	List<Member> members = new ArrayList<Member>(); 
	
	@FXML
	public void initialize() {
		getMemberList(null, "last_name asc"); 

		populateMembers(); 
	}
	
	/**
	 * Method to populate the member list with database table
	 * Member list should be populated before calling this method
	 */
	public void populateMembers() {
		
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
		newPane.getChildren().remove(newPane.lookup("#reservationButton"));
	}

	/**
	 * Method to add members to the member container
	 * @param member one member from the list of members acquired from the database
	 */
	public void addMemberToContainer(Member member) {
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
		NewMemberFormController memberController = loader.<NewMemberFormController>getController();
		memberController.setMemberListController(this);
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
		System.out.println("search button clicked");
		String fxmlFile = "SearchMemberForm.fxml"; 
    	Stage stage = new Stage(); 
    	Pane myPane = null; 
    	FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
    	myPane = (Pane) loader.load();
    	SearchMemberFormController memberController = loader.<SearchMemberFormController>getController();
		memberController.setMemberListController(this);
    	Scene scene = new Scene(myPane); 
    	stage.setScene(scene);
    	stage.show(); 
	}
	
	
	@FXML
	public void sortByLastName(ActionEvent event) {
		String orderAttribute = "last_name "; 
		// sort by the opposing order
		orderAttribute += (lastAscending) ? "desc" : "asc"; 	
		getMemberList(null, orderAttribute); 
		populateMembers(); 
		lastAscending = !lastAscending; 
	}
	
	@FXML
	public void sortByFirstName(ActionEvent event) {
		String orderAttribute = "first_name "; 
		// sort by the opposing order
		orderAttribute += (firstAscending) ? "desc" : "asc"; 	
		getMemberList(null, orderAttribute); 
		populateMembers(); 
		firstAscending = !firstAscending; 
	}
	
	/**
	 * Method to get the list of members to populate the view
	 * Uses filter for the searching feature only
	 * @param filter can search by different attributes (first_name, list_name, phone number, email) 
	 * @param orderAttribute the attribute to sort the members by.
	 * Note the orderAttribute must be in the format "attribute order"
	 * attribute limited to first_name and last_name
	 * For example: "first_name asc"
	 */
	public void getMemberList(String filter, String orderAttribute) {
		members.clear();
		DatabaseCommunicator.getInstance();

		String query = "SELECT * FROM members";
		// add filters to the query if any were specified
		if (filter != null) 
			query += " WHERE " + filter;
		// apply any sorting
		if (orderAttribute != null) 
			query += " ORDER BY " + orderAttribute; 
		query += ";"; 
		
		System.out.println(query);
		
		// populate member list from back end
		List<HashMap<String, Object>> rows = DatabaseCommunicator.queryDatabase(
				query);
		for (HashMap<String, Object> row : rows) {
			Member member = new Member(); 
			member.setFirstName(row.get("first_name").toString());
			member.setLastName(row.get("last_name").toString());
			member.setEmail(row.get("email").toString());
			member.setPhoneNumber(row.get("phone_number").toString());
			member.setAddress(row.get("address").toString());
			member.setCity(row.get("city").toString());
			member.setState(row.get("state").toString());
			member.setZipCode(Integer.parseInt(row.get("zip_code").toString()));
			member.setMemberType(row.get("member_type").toString());
			this.members.add(member); 
		}
	}
}
