package com.fym.lta.DTO;

/**
 *
 * @author Nada El-Gammal
 */

/**class to define equipment attributes with setters, getters and constructors
 */
public class EquipmentDto {

    /* equipment id */
    private int id;

    /* equipment code */
    private String code;

    /* equiment made country */
    private String country;

    /* equiment type object */
    private EquipmentTypeDto type;

    /* location of equipment object */
    private LocationDto location;

    /* search key foe equipment */
    private String search;


    /**
     * class main constructor
     */
    public EquipmentDto() {
        super();
    }

    /**
     * constructor with search attribute
     * @param search
     */
    public EquipmentDto(String search) {
        this.search = search;
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
     * @return id int
     */
    public int getId() {
        return id;
    }

    /**
     * code attribute setter
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * code attribute getter
     * @return code string
     */
    public String getCode() {
        return code;
    }

    /**
     * made coutry attribute setter
     * @param country string
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * made country attribute getter
     * @return country string
     */
    public String getCountry() {
        return country;
    }

    /**
     * type of equipment attribute setter
     * @param type object
     */
    public void setType(EquipmentTypeDto type) {
        this.type = type;
    }

    /**
     * type of equipment attribute getter
     * @return type object
     */
    public EquipmentTypeDto getType() {
        return type;
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
     * @return search String
     */
    public String getSearch() {
        return search;
    }

    /**
     * equipment location attribute setter
     * @param location object
     */
    public void setLocation(LocationDto location) {
        this.location = location;
    }

    /**
     * equipment location attribute getter
     * @return location object
     */
    public LocationDto getLocation() {
        return location;
    }
}
