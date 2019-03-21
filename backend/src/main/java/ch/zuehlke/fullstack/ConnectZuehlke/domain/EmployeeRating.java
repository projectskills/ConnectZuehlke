package ch.zuehlke.fullstack.ConnectZuehlke.domain;

public class EmployeeRating {
    private Employee employee;
    private Double rating;


    public EmployeeRating(Employee employee, Double rating) {
        this.employee = employee;
        this.rating = rating;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Double getRating() {
        return rating;
    }
}
