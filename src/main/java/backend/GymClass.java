package backend;

/**
 * Class to handle gym class management 
 * @author sonianarayanan
 *
 */

public class GymClass implements DatabaseObject {
  private String classID;
  private String name;
  private String startTime;
  private String endTime;
  private String days;
  private String room;

  public GymClass() {
    
  }
  
  public GymClass(String classID, String name, String startTime, String endTime, String days, String room) {
    this.classID = classID;
    this.name = name;
    this.startTime = startTime;
    this.endTime = endTime;
    this.days = days;
    this.room = room;
  }

  @Override
  public String getKeys() {
    return "classID, name, startTime, endTime, days, room";
  }

  @Override
  public String getValues() {
    return "'" + this.classID + "', '" + this.name + "', '" + this.startTime + "', '" + this.endTime + "', '" + this.days + "', '" + this.room + "'";
  }

  @Override
  public String getTable() {
    return "classes";
  }

  @Override
  public String getKeyIdentifier() {
    return "classID";
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getStart() {
    return startTime;
  }

  public void setStart(String start) {
    this.startTime = start;
  }

  public String getEnd() {
    return endTime;
  }

  public void setEnd(String end) {
    this.endTime = end;
  }

  public String getDays() {
    return days;
  }

  public void setDays(String days) {
    this.days = days;
  }

  public String getRoom() {
    return room;
  }

  public void setRoom(String room) {
    this.room = room;
  }

  public String getClassID() {
    return classID;
  }
  
  public void setClassID(String classID) {
    this.classID = classID;
  }
 

}
