package Library;

public class Visitor {
    private int idVisitor;
    private String firstName;
    private String lastName;

    public Visitor(int idVisitor, String firstName, String lastName) {
        this.idVisitor = idVisitor;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getVisitorID() {
        return idVisitor;
    }

    public void setVisitorID(int idVisitor) {
        this.idVisitor = idVisitor;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setIdVisitor(int idVisitor) {
        this.idVisitor = idVisitor;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
