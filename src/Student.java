public class Student {
    
    private String ID;
    private String FirstName;
    private String LastName;
    private String ASURITE;

    public Student(String ID,String FirstName,String LastName,String ASURITE) {
        this.ID = ID;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.ASURITE = ASURITE;
    }

    public String getID() {
        return ID;
    }

    public void setID(String iD) {
        ID = iD;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getASURITE() {
        return ASURITE;
    }

    public void setASURITE(String aSURITE) {
        ASURITE = aSURITE;
    }

    
}
