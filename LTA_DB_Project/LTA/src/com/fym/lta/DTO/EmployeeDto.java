package com.fym.lta.DTO;

/**class to define employee attributes with setters, getters and constructors
 */
public class EmployeeDto {

    /* employee name */
    private String name;

    /* employee id */
    private int id;

    /* employee job */
    private String job;

    /* search key */
    private String search;

    private EmployeeUserDto account;


    /**
     * search attribute setter
     * @param search string
     */
    public void setSearch(String search) {
        this.search = search;
    }

    /**
     * search attribute getter
     * @return search string
     */
    public String getSearch() {
        return search;
    }

    /**
     * name attribute setter
     * @param name string
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * name attribute getter
     * @return name string
     */
    public String getName() {
        return name;
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
     * @return int id
     */
    public int getId() {
        return id;
    }

    /**
     * job attribute setter
     * @param job string
     */
    public void setJob(String job) {
        this.job = job;
    }

    /**
     * job attribute getter
     * @return job string
     */
    public String getJob() {
        return job;
    }

    /**
     *class constructor from search attribute
     * @param search
     */
    public EmployeeDto(String search) {
        this.search = search;
    }

    /**
     *
     * class main constructor
     */
    public EmployeeDto() {
        super();
    }
}
