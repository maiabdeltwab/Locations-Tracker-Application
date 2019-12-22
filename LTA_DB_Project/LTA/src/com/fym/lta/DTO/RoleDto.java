package com.fym.lta.DTO;

/**class to define role attributes with setters, getters and constructors
 */
public class RoleDto {

    /* role name */
    private String name;

    /* role description */
    private String description;

    /* role id */
    private int id;

    /* search key for role */
    private String search;
    private RoleScreenDto power;


    /**
     * class main constructor
     */
    public RoleDto() {
        super();
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
     * @return string
     */
    public String getName() {
        return name;
    }

    /**
     * description attribute setter
     * @param description string
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * description attribute getter
     * @return string
     */
    public String getDescription() {
        return description;
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
     * constructor from search attribute
     * @param search string
     */
    public RoleDto(String search) {
        this.search = search;
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

}
