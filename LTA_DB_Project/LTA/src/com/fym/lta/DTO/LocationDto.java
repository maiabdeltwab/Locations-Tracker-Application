package com.fym.lta.DTO;

import java.util.List;

/**class to define location attributes with setters, getters and constructors
 */
public class LocationDto {

    /* location code */
    private String code;

    /* location id */
    private int id;

    /* location capacity */
    private int capacity;

    /* floor of location */
    private FloorDto floor;

    /* location name */
    private String name;

    /* equipments of location */
    private List<EquipmentDto> Equipments;

    /* type of location */
    private LocationTypeDto type;

    /* building of location */
    private BuildingDto build;

    /* search key for location */
    private String search;

    /* number of free slots of location */
    private int freeSlots;

    /* number of reserved slots for location */
    private int reserved_number;


    /**
     * reserved number attribute setter
     * @param reserved_number int
     */
    public void setReserved_number(int reserved_number) {
        this.reserved_number = reserved_number;
    }

    /**
     *reserved number attribute getter
     * @return int
     */
    public int getReserved_number() {
        return reserved_number;
    }

    /**
     * free slots attribute setter
     * @param freeSlots int
     */
    public void setFreeSlots(int freeSlots) {
        this.freeSlots = freeSlots;
    }

    /**
     * free slots attribute getter
     * @return int
     */
    public int getFreeSlots() {
        return freeSlots;
    }

    /**
     * search attribute setter
     * @param search string
     */
    public void setSearch(String search) {
        this.search = search;
    }

    /**
     *  search attribute getter
     * @return string
     */
    public String getSearch() {
        return search;
    }

    /**
     * building attribute setter
     * @param build object
     */
    public void setBuild(BuildingDto build) {
        this.build = build;
    }

    /**
     * building of location attribute getter
     * @return object
     */
    public BuildingDto getBuild() {
        return build;
    }

    /**
     * floor of location attribute setter
     * @param floor object
     */
    public void setFloor(FloorDto floor) {
        this.floor = floor;
    }

    /**
     * floor of location attribute getter
     * @return object
     */
    public FloorDto getFloor() {
        return floor;
    }

    /**
     * location equipmets attribute setter
     * @param Equipments list
     */
    public void setEquipments(List<EquipmentDto> Equipments) {
        this.Equipments = Equipments;
    }

    /**
     * location equipments attribute getter
     * @return list
     */
    public List<EquipmentDto> getEquipments() {
        return Equipments;
    }

    /**
     * type attribute setter
     * @param type object
     */
    public void setType(LocationTypeDto type) {
        this.type = type;
    }

    /**
     * type attribute  getter
     * @return object
     */
    public LocationTypeDto getType() {
        return type;
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
     * capacity attribute setter
     * @param capacity int
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * capacity attribute getter
     * @return int
     */
    public int getCapacity() {
        return capacity;
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
     * class main constructor
     */
    public LocationDto() {
        super();
    }

    /**
     * constructor with name attribute
     * @param name string
     */
    public LocationDto(String name) {
        this.name = name;
    }

    /**
     * costructor with id attribute
     * @param id int
     */
    public LocationDto(int id) {
        this.id = id;
    }
}
