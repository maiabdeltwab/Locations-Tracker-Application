package com.fym.lta.DTO;

import java.util.List;


/**class to define building attributes with setters, getters and constructors
 */
public class BuildingDto {

    /* building id */
    private int id;

    /* no of floors for building */
    private int floorsNo;

    /* building code */
    private String code;

    /* building description */
    private String description;

    /* list of floors of building */
    private List<FloorDto> Floors;

    /* list of departments of building */
    private DepartBuildingDto departments;

    /* search key for building */
    private String search;


    /**
     * floorsNo attribute setter
     * @param floorsNo
     */
    public void setFloorsNo(int floorsNo) {
        this.floorsNo = floorsNo;
    }

    /**
     * floorsNo attribute getter
     * @return floorsNo
     */
    public int getFloorsNo() {
        return floorsNo;
    }

    /**
     * search attribute constructor
     * @param search
     */
    public BuildingDto(String search) {
        this.search = search;
    }

    /**
     * id attribute costructor
     * @param id
     */
    public BuildingDto(int id) {
        this.id = id;
    }

    /**
     * serach attribute setter
     * @param search
     */
    public void setSearch(String search) {
        this.search = search;
    }

    /**
     * search attribute getter
     * @return search
     */
    public String getSearch() {
        return search;
    }

    /**
     * list of floors attribute setter
     * @param Floors
     */
    public void setFloors(List<FloorDto> Floors) {
        this.Floors = Floors;
    }

    /**
     * list of floors attribute getter
     * @return list of floors
     */
    public List<FloorDto> getFloors() {
        return Floors;
    }

    /**
     * description attribute getter
     * @return description
     */
    public String getDescription() {
        return description;
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
     * @return code
     */
    public String getCode() {
        return code;
    }

    /**
     * description attribute setter
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * id attribute setter
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * id attribute getter
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * class main costructor
     */
    public BuildingDto() {
        super();
    }
}
