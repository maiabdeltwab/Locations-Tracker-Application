package com.fym.lta.DTO;

/**class to define staff attributes with setters, getters and constructors
 */
public class StaffDto {

    /* staff id */
    private int id;

    /* staff name */
    private String name;

    /* staff position */
    private String position;

    /* department that staff belongs to */
    private DepartmentDto department;

    /* staff search key */
    private String Search;

    /* user account of staff */
    private StaffUserDto account;

    /* courses that staff works on */
    private WorkOnDto courses;

    /* staff work hours */
    private int WorkHours;

    /* staff user data */
    private UserDto user;

    /**
     * class main constructor
     */
    public StaffDto() {
        super();
    }

    /**
     * search attribute setter
     * @param search string
     */
    public void setSearch(String search) {
        this.Search = search;
    }

    /**
     * search attribute getter
     * @return string
     */
    public String getSearch() {
        return Search;
    }

    /**
     * id attribute setter
     * @param id int
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * id attribute getter
     * @return int
     */
    public int getId() {
        return id;
    }

    /**
     * name attribute setter
     * @param mname string
     */
    public void setName(String mname) {
        this.name = mname;
    }

    /**
     * name attribute getter
     * @return string
     */
    public String getName() {
        return name;
    }

    /**
     * position attribute setter
     * @param position string
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * position attribute getter
     * @return string
     */
    public String getPosition() {
        return position;
    }

    /**
     * department attribute setter
     * @param department object
     */
    public void setDepartment(DepartmentDto department) {
        this.department = department;
    }

    /**
     * department attribute getter
     * @return object
     */
    public DepartmentDto getDepartment() {
        return department;
    }

    /**
     * work hours attribute setter
     * @param WorkHours int
     */
    public void setWorkHours(int WorkHours) {
        this.WorkHours = WorkHours;
    }

    /**
     * work hours attribute getter
     * @return int
     */
    public int getWorkHours() {
        return WorkHours;
    }

    /**
     * user attribute setter
     * @param user object
     */
    public void setUser(UserDto user) {
        this.user = user;
    }

    /**
     * user attribute getter
     * @return object
     */
    public UserDto getUser() {
        return user;
    }
}
