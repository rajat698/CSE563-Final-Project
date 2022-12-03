import java.util.HashMap;
import java.util.Map;

/**
* This Student class consists of getters and setters
* for information about the student.
*/
public class Student {
    
    private String ID;
    private String FirstName;
    private String LastName;
    private String ASURITE;
    private Map<String, Integer> attendance;

    public Student(String ID,String FirstName,String LastName,String ASURITE) {
        this.ID = ID;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.ASURITE = ASURITE;
        this.attendance = new HashMap<>();
    }

    /**
    * This method is used to get ID of student.
    * @return ID of student.
    */
    public String getID() {
        return ID;
    }

    /**
    * This method is used to set ID of student.
    * @param ID is the ID of student.
    */
    public void setID(String iD) {
        ID = iD;
    }

    /**
    * This method is used to get First Name of student.
    * @return First Name of student.
    */
    public String getFirstName() {
        return FirstName;
    }

     /**
      * This method is used to set First Name of student.
      * @param firstName is the first name of student.
      */    
    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

     /**
      * This method is used to get Last Name of student.
      * @return Last Name of student.
      */
    public String getLastName() {
        return LastName;
    }

     /**
      * This method is used to set Last Name of student.
      * @param lastName is the last name of student.
      */
    public void setLastName(String lastName) {
        LastName = lastName;
    }

     /**
      * This method is used to get ASURITE of student.
      * @return ASURITE of student.
      */
    public String getASURITE() {
        return ASURITE;
    }

     /**
      * This method is used to set ASUrite ID of student.
      * @param aSURITE is the ASUrite of student.
      */
    public void setASURITE(String aSURITE) {
        ASURITE = aSURITE;
    }

     /**
      * This method is used to get attendance of student.
      * @return a map of date and attendance.
      */    
    public Map<String, Integer> getAttendance() {
        return attendance;
    }

     /**
      * This method is used to set attendance of student for given dates.
      * @param attendance is a map of date and attendance.
      */    
    public void setAttendance(Map<String, Integer> attendance) {
        this.attendance = attendance;
    }
}
