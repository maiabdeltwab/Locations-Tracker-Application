package com.fym.lta.DTO;

/**class to define staff-user account relation attributes
 * with setters, getters and constructors
 */
public class StaffUserDto {

    /* user account id */
    private int user_id;

    /* staff object */
    private StaffDto staff;

    /**
     * staff attribute setter
     * @param staff object
     */
    public void setStaff(StaffDto staff) {
        this.staff = staff;
    }

    /**
     * staff attribute getter
     * @return object
     */
    public StaffDto getStaff() {
        return staff;
    }

    /**
     * user id attribute setter
     * @param user_id int
     */
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    /**
     * user id attribute  getter
     * @return int
     */
    public int getUser_id() {
        return user_id;
    }

    /**
     * class main constructor
     */
    public StaffUserDto() {
        super();
    }

}
