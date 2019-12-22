package com.fym.lta.DTO;

/**
 *
 * @author Nada El-Gammal
 */

/**class to define equipment type attributes with setters, getters and constructors
 */
public class EquipmentTypeDto {

    /* equipment type code */
    private String code;

    /* number of equipments of type */
    private int no_of_equip;

    /* name of equipment type */
    private String name;

    /* id of equipment type */
    private int id;

    /* search string for equipment type */
    private String search;

    /* specifications of equipemnt type */
    private EquipTypeSpecDto Specifications;


    /**
     * class main constructor
     */
    public EquipmentTypeDto() {
        super();
    }

    /**
     * constructor from class attributes
     * @param code
     * @param no_of_equip
     * @param name
     * @param id
     * @param search
     * @param Specifications
     */
    public EquipmentTypeDto(String code, int no_of_equip, String name, int id, String search,
                            EquipTypeSpecDto Specifications) {
        this.code = code;
        this.no_of_equip = no_of_equip;
        this.name = name;
        this.id = id;
        this.search = search;
        this.Specifications = Specifications;
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
     * @return
     */
    public String getCode() {
        return code;
    }

    /**
     * number of equipments attribute setter
     * @param no_of_equip int
     */
    public void setNo_of_equip(int no_of_equip) {
        this.no_of_equip = no_of_equip;
    }

    /**
     * number of equipments attribute getter
     * @return number of equipments int
     */
    public int getNo_of_equip() {
        return no_of_equip;
    }

    /**
     * name attribute getter
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
     * @return id int
     */
    public int getId() {
        return id;
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
     * specification attribute setter
     * @param Specifications object
     */
    public void setSpecifications(EquipTypeSpecDto Specifications) {
        this.Specifications = Specifications;
    }

    /**
     * specefication attribute getter
     * @return specification type details object
     */
    public EquipTypeSpecDto getSpecifications() {
        return Specifications;
    }

    /**
     * constructor from name attribute
     * @param name string
     */
    public EquipmentTypeDto(String name) {
        this.name = name;
    }

    /**
     * constructor from attributes
     * @param code
     * @param no_of_equip
     * @param name
     * @param id
     */
    public EquipmentTypeDto(String code, int no_of_equip, String name, int id) {
        this.code = code;
        this.no_of_equip = no_of_equip;
        this.name = name;
        this.id = id;
    }

    /**
     * constructor from id attribute
     * @param id int
     */
    public EquipmentTypeDto(int id) {
        this.id = id;
    }
}
