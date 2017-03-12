package frontend;

import java.io.IOException;

import backend.Member;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
    private void editAction(ActionEvent event) {	
		Stage stage = new Stage();
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EditMemberForm.fxml"));     

    	Parent root = null;
		try {
			root = (Parent)fxmlLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}          
		// Sets values so the ResourceEntryController knows which course it contains.
		// TODO(Courtney) might want to change to fetch the database member then just pass that
		EditMemberController memberController = fxmlLoader.<EditMemberController>getController();
		memberController.setFirstName(member.getFirstName());
		memberController.setLastName(member.getLastName());
		memberController.setPhoneNumber(member.getPhoneNumber());
		memberController.setMemberType(member.getMemberType());
		memberController.setController(memberListController);
    	
		Scene scene = new Scene(root); 
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
