package com.fym.lta.DTO;

/**class to define employee user account attributes
 * with setters, getters and constructors
 */
public class EmployeeUserDto {

    /* user account id */
    private int user_id;

    /* object of employeeDto */
    private EmployeeDto employee;

    /**
     * class main constructor
     */
    public EmployeeUserDto() {
        super();
    }

    /**
     * employee object attribute setter
     * @param employee object
     */
    public void setEmployee(EmployeeDto employee) {
        this.employee = employee;
    }

    /**
     * employee object attribute getter
     * @return employee object
     */
    public EmployeeDto getEmployee() {
        return employee;
    }

    /**
     * user id attribute setter
     * @param user_id int
     */
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    /**
     * user id attribute getter
     * @return id int
     */
    public int getUser_id() {
        return user_id;
    }

}
