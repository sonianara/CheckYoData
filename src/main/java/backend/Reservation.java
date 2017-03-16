package backend;

public class Reservation implements DatabaseObject {
	private GymClass gymClass; 
	private Member member;
	
	public Reservation(GymClass gymClass, Member member) {
		this.gymClass = gymClass;
		this.member = member;
	}

	public GymClass getGymClass() {
		return gymClass;
	}
	public void setGymClass(GymClass gymClass) {
		this.gymClass = gymClass;
	}
	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}

	@Override
	public String getKeys() {
		return "member_email, class_id";
	}

	@Override
	public String getValues() {
		return "'" + member.getEmail() + "', '" + gymClass.getClassID() + "'";
	}

	@Override
	public String getTable() {
		return "reservations";
	}

	@Override
	public String getKeyIdentifier() {
		return "id";
	} 
	
	public void addToDatabase() {
		DatabaseCommunicator.addToDatabase(this);
	}
	
}
