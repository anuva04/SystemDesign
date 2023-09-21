/**
 * Adapter design pattern is used in scenarios where we want 2 incompatible types to collaborate.
 * One typical example of this scenario is when we want data from obtained from a certain API/database to be utilised in a different
 * code which is expecting a different schema.
 * It is used to make legacy code compatible with new code.
 * In this example, Summary class is legacy code which is compatible with OldEmployee class but not NewEmployee.
 * We don't want to modify legacy code.
 * So we create an adapter for NewEmployee which extends OldEmployee and hence is compatible with Summary.
 */
class AdapterPattern {
    public static void main(String[] args) {
        OldEmployee employee1 = new OldEmployee("firstname1", "lastname1", "designation1", "old-employee-abc");
        NewEmployee employee2 = new NewEmployee("employee2", "lastname2", "designation2", "new-employee-def");

        Summary.getSummary(employee1); // works fine
        // Summary.getSummary(employee2); // throws compilation error

        NewEmployeeAdapter employeeAdapter = new NewEmployeeAdapter(employee2);
        Summary.getSummary(employeeAdapter); // works fine
    }
}

class Summary {
    public static void getSummary(OldEmployee employee) {
        System.out.println("Summary of employee with id " + employee.getEmployeeId());
        System.out.println("First Name: " + employee.getFirstName());
        System.out.println("Last Name: " + employee.getLastName());
        System.out.println("Designation: " + employee.getDesignation());
    }
}

class OldEmployee {
    private String firstName;
    private String lastName;
    private String designation;
    private String employeeId;

    public OldEmployee(String firstName, String lastName, String designation, String employeeId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.designation = designation;
        this.employeeId = employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDesignation() {
        return designation;
    }

    public String getEmployeeId() {
        return employeeId;
    }
}

class NewEmployee {
    private String name;
    private String surname;
    private String jobTitle;
    private String employeeId;

    public NewEmployee(String name, String surname, String jobTitle, String employeeId) {
        this.name = name;
        this.surname = surname;
        this.jobTitle = jobTitle;
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getEmployeeId() {
        return employeeId;
    }
}

class NewEmployeeAdapter extends OldEmployee {
    public NewEmployeeAdapter(NewEmployee newEmployee) {
        super(newEmployee.getName(), newEmployee.getSurname(), newEmployee.getJobTitle(), newEmployee.getEmployeeId());
    }
}