package university.employees;

import university.communication.Message;
import university.communication.OfficialRequest;
import university.users.User;

public abstract class Employee extends User {
    private static final long serialVersionUID = 1L;

    private String employeeId;
    private double salary;

    protected Employee(String id, String name, String email, String passwordHash, String employeeId, double salary) {
        super(id, name, email, passwordHash);
        this.employeeId = employeeId;
        this.salary = salary;
    }

    public Message sendMessage(Employee to, String text) {
        return new Message("MSG-" + System.nanoTime(), this, to, text);
    }

    public OfficialRequest createOfficialRequest(String description) {
        return new OfficialRequest("OFF-" + System.nanoTime(), this, description);
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
