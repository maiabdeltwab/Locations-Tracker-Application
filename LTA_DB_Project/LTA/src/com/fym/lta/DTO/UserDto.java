package com.fym.lta.DTO;

import javax.sql.rowset.JdbcRowSet;

/**class to define user attributes with setters, getters and constructors
 */
public class UserDto {

    /* user id */
    private int id;

    /* username of user */
    private String username;

    /* user role */
    private RoleDto role;

    /* user password */
    private String password;

    /* user email */
    private String email;

    /* search key for user */
    private String search;

    /* keep user logged in application */
    private int keep_login;

    /* staff account */
    private StaffDto staffAcc;

    /* employee account */
    private EmployeeDto employeeAcc;

    /* name of screen */
    private String screen_name;

    /* name of id */
    private int screen_id;

    /* user connection with database */
    private JdbcRowSet connect;


    /**
     * user connection setter
     * @param connect
     */
    public void setConnect(JdbcRowSet connect) {
        this.connect = connect;
    }

    /**
     * user connection getter
     * @return
     */
    public JdbcRowSet getConnect() {
        return connect;
    }

    /**
     * screen name attribute setter
     * @param screen_name string
     */
    public void setScreen_name(String screen_name) {
        this.screen_name = screen_name;
    }

    /**
     * screen name attribute getter
     * @return string
     */
    public String getScreen_name() {
        return screen_name;
    }

    /**
     * scrren id attribute setter
     * @param Screen_id int
     */
    public void setScreen_id(int Screen_id) {
        this.screen_id = Screen_id;
    }

    /**
     * screen id attribute getter
     * @return int
     */
    public int getScreen_id() {
        return screen_id;
    }

    /**
     * staff account attribute setter
     * @param staffAcc object
     */
    public void setStaffAcc(StaffDto staffAcc) {
        this.staffAcc = staffAcc;
    }

    /**
     * staff account attribute getter
     * @return object
     */
    public StaffDto getStaffAcc() {
        return staffAcc;
    }

    /**
     * employee account attribute setter
     * @param employeeAcc object
     */
    public void setEmployeeAcc(EmployeeDto employeeAcc) {
        this.employeeAcc = employeeAcc;
    }

    /**
     * employee account attribute getter
     * @return object
     */
    public EmployeeDto getEmployeeAcc() {
        return employeeAcc;
    }

    /**
     * keep_login attribute setter
     * @param keep_login int
     */
    public void setKeep_login(int keep_login) {
        this.keep_login = keep_login;
    }

    /**
     * keep_login attribute getter
     * @return int
     */
    public int getKeep_login() {
        return keep_login;
    }

    /**
     * class main constructor
     */
    public UserDto() {
        super();
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
     * username attribute setter
     * @param username string
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * username attribute getter
     * @return string
     */
    public String getUsername() {
        return username;
    }

    /**
     * role attribute setter
     * @param role object
     */
    public void setRole(RoleDto role) {
        this.role = role;
    }

    /**
     * role attribute getter
     * @return object
     */
    public RoleDto getRole() {
        return role;
    }

    /**
     * password attribute setter
     * @param password string
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * password attribute getter
     * @return string
     */
    public String getPassword() {
        return password;
    }

    /**
     * email attribute setter
     * @param email string
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * email attribute getter
     * @return string
     */
    public String getEmail() {
        return email;
    }

    /**
     * search attribute setter
     * @param search string
     */
    public void setSearch(String search) {
        this.search = search;
    }

    /**
     * search attribute getter
     * @return string
     */
    public String getSearch() {
        return search;
    }

    /**
     * constructor from search attribute
     * @param search
     */
    public UserDto(String search) {
        this.search = search;
    }
}
