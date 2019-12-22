package com.fym.lta.DTO;

import java.util.List;

/**class to define floor attributes with setters, getters and constructors
 */
public class FloorDto {

    /* floor id */
    private int id;

    /* building of floor */
    private BuildingDto build;

    /* floor code */
    private String code;

    /* floor description */
    private String description;

    /* number of location of floor */
    private int location_number;

    /* list of location exist in floor */
    private List<LocationDto> Locations;

    /* search key for floor */
    private String search;


    /**
     * class main constructor
     */
    public FloorDto() {
        super();

    }

    /**
     * number of locations attribute setter
     * @param location_number int
     */
    public void setLocation_number(int location_number) {
        this.location_number = location_number;
    }

    /**
     * number of locations attribute getter
     * @return int
     */
    public int getLocation_number() {
        return location_number;
    }

    /**
     * building attribute setter
     * @param build object
     */
    public void setBuild(BuildingDto build) {
        this.build = build;
    }

    /**
     * building attribute getter
     * @return object of buildingDto
     */
    public BuildingDto getBuild() {
        return build;
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
     * code attribute setter
     * @param code string
     */
    public FloorDto(String code) {
        this.code = code;
    }

    /**
     * list of locations attribute setter
     * @param Locations list
     */
    public void setLocations(List<LocationDto> Locations) {
        this.Locations = Locations;
    }

    /**
     * list of locations attribute getter
     * @return list
     */
    public List<LocationDto> getLocations() {
        return Locations;
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
