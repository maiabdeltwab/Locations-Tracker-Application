package com.fym.lta.DTO;

/**
 *
 * @author Nada El-Gammal
 */

/**class to define equipment specifications attributes
 * with setters, getters and constructors
 */
public class EquipSpecificationDto {

    /* specification name */
    private String name;

    /* specification value */
    private String value;

    /* specification id */
    private int id;

    /* search string for specification */
    private String search;

    /* type specification relation object */
    private EquipTypeSpecDto type;


    /**
     * class main constructor
     */
    public EquipSpecificationDto() {
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
     * @return name string
     */
    public String getName() {
        return name;
    }

    /**
     * value attribute setter
     * @param value string
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * value attribute getter
     * @return value string
     */
    public String getValue() {
        return value;
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
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * type specification attribute setter
     * @param type object
     */
    public void setType(EquipTypeSpecDto type) {
        this.type = type;
    }

    /**
     * type specification attribute getter
     * @return object
     */
    public EquipTypeSpecDto getType() {
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
     * @return string
     */
    public String getSearch() {
        return search;
    }

    /**
     * constructor from search attribute
     * @param search
     */
    public EquipSpecificationDto(String search) {
        this.search = search;
    }

    /**
     * constructor from attributes
     * @param name
     * @param value
     * @param id
     */
    public EquipSpecificationDto(String name, String value, int id) {
        this.name = name;
        this.value = value;
        this.id = id;
    }
}
