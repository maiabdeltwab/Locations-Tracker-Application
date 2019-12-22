package com.fym.lta.DTO;

/**
 *
 * @author Mai-AbdEltwab
 */

/**class to define location type attributes with setters, getters and constructors
 */
public class LocationTypeDto {

    /* location type code */
    private String code;

    /* location type color */
    private String color;

    /* number of location of type */
    private int num_of_locations;

    /* type description */
    private String description;

    /* type id */
    private int id;

    /* search key for type */
    private String search;


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
     * constructor form id attribute
     * @param id int
     */
    public LocationTypeDto(int id) {
        this.id = id;
    }

    /**
     * class main constructor
     */
    public LocationTypeDto() {
        super();
    }

    /**
     * code attribute setter
     * @param code string
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * code attribute getter
     * @return string
     */
    public String getCode() {
        return code;
    }

    /**
     * color attribute setter
     * @param color string
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * color attribute getter
     * @return string
     */
    public String getColor() {
        return color;
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
     * number of loaction attribute setter
     * @param num_of_locations int
     */
    public void setNum_of_locations(int num_of_locations) {
        this.num_of_locations = num_of_locations;
    }

    /**
     * number of location attribute getter
     * @return int
     */
    public int getNum_of_locations() {
        return num_of_locations;
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
}
