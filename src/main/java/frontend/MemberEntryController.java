package frontend;

import java.io.IOException;

import backend.DatabaseCommunicator;
import backend.Member;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane; 
import javafx.scene.control.Button; 
import javafx.scene.control.Label; 
import javafx.scene.Parent;
import javafx.scene.Scene;
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
	
	public void setMember(Member member) {
		this.member = member; 
	}
	
	public void setMemberController(MemberListController controller) {
		this.memberListController = controller; 
	}
}
