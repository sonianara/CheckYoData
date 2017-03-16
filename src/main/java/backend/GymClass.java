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
  private String date;
  private String room;
  private int capacity;
  private int reserved;

  public GymClass() {
    
  }
  
  public GymClass(String classID, String name, String startTime, String endTime, String date, String room, int capacity, int reserved) {
    this.classID = classID;
    this.name = name;
    this.startTime = startTime;
    this.endTime = endTime;
    this.date = date;
    this.room = room;
    this.capacity = capacity;
    this.reserved = reserved;
  }

  @Override
  public String getKeys() {
    return "classID, name, startTime, endTime, date, room, capacity, reserved";
  }

  @Override
  public String getValues() {
    return "'" + this.classID + "', '" + this.name + "', '" + this.startTime + "', '"
          + this.endTime + "', '" + this.date + "', '" + this.room + "', " + this.capacity + ", " + this.reserved;
  }

  @Override
  public String getTable() {
    return "classes";
  }

  @Override
  public String getKeyIdentifier() {
    return "classID";
  }

  public String getClassID() {
     return classID;
  }
  
  public void setClassID(String classID) {
     this.classID = classID;
  }
  
  public String getName() {
     return name;
  }
  
  public void setName(String name) {
     this.name = name;
  }
  
  public String getStartTime() {
     return startTime;
  }
  
  public void setStartTime(String startTime) {
     this.startTime = startTime;
  }
  
  public String getEndTime() {
     return endTime;
  }
  
  public void setEndTime(String endTime) {
     this.endTime = endTime;
  }
  
  public String getDate() {
     return date;
  }
  
  public void setDate(String date) {
     this.date = date;
  }
  
  public String getRoom() {
     return room;
  }
  
  public void setRoom(String room) {
     this.room = room;
  }
  
  public int getCapacity() {
     return capacity;
  }
  
  public void setCapacity(int capacity) {
     this.capacity = capacity;
  }
  
  public int getReserved() {
     return reserved;
  }
  
  public void setReserved(int reserved) {
     this.reserved = reserved;
  }
}
