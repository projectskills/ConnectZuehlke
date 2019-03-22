package ch.zuehlke.fullstack.ConnectZuehlke.domain;

public class EmployeeRating {
    private Employee employee;
    private int rating;


    public EmployeeRating(Employee employee, int rating) {
        this.employee = employee;
        this.rating = rating;
    }

    public Employee getEmployee() {
        return employee;
    }

    public int getRating() {
        return rating;
    }
}
