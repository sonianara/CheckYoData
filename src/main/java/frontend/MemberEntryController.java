package frontend;

import java.io.IOException;

import backend.DatabaseCommunicator;
import backend.Member;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Class to handle each member for use in the MemberListView
 * Enables the edit button per each member
 * @author cbrown
 *
 */
public class MemberEntryController {
	Member member; 
	MemberListController memberListController; 
	
	@FXML private Label firstName; 
	@FXML private Label lastName; 
	@FXML private Label phoneNumber; 
	@FXML private Label memberType; 
	@FXML private Button editButton; 
	@FXML private Button reservationButton; 
	
	/**
	 * Method to inform the EditMemberController which member it is editing
	 * @param event necessary for javafx onClick action
	 */
	@FXML
    private void editAction(ActionEvent event) throws IOException {	
Stage stage = new Stage();
    	Pane myPane = null; 

    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EditMemberForm.fxml"));     
		myPane = (Pane)fxmlLoader.load();

		// Sets values so the ResourceEntryController knows which course it contains.
		EditMemberController editMemberController = fxmlLoader.<EditMemberController>getController();
		DatabaseCommunicator.getInstance(); 
		editMemberController.setMember(DatabaseCommunicator.getMember(phoneNumber.getText())); 
		editMemberController.setController(memberListController);

		
		editMemberController.populateFields();
	
		Scene scene = new Scene(myPane); 
    	stage.setScene(scene);    
    	stage.show();   
	}
	
	@FXML 
	public void reserveAction(ActionEvent event) throws IOException {
		Stage stage = new Stage();
    	Pane myPane = null; 

    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ClassRegistrationByMemberView.fxml"));     
		myPane = (Pane)fxmlLoader.load();

		// Sets values so the ResourceEntryController knows which course it contains.
		ClassRegistrationByMemberController registrationController = fxmlLoader.<ClassRegistrationByMemberController>getController();
		TextField memberName = (TextField) myPane.lookup("#memberName");
		memberName.setText(member.getFirstName() + " " + member.getLastName());
		memberName.setAlignment(Pos.CENTER);
		memberName.setDisable(true); 
		DatabaseCommunicator.getInstance(); 
		registrationController.setMember(DatabaseCommunicator.getMember(phoneNumber.getText())); 
	
		Scene scene = new Scene(myPane); 
    	stage.setScene(scene);    
    	stage.show();   
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
	public void setMemberController(MemberListController controller) {
		this.memberListController = controller; 
	}
}
