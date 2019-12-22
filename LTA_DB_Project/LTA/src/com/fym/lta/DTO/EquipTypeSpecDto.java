package com.fym.lta.DTO;

/**
 *
 * @author Nada El-Gammal
 */

/**class to define equipment type-specification relation attributes
 * with setters, getters and constructors
 */
public class EquipTypeSpecDto {

    /* relation id */
    private int id;

    /* type id */
    private int type_id;

    /* specification id */
    private int specification_id;


    /**
     * constructor from attributes
     * @param id
     * @param type_id
     * @param specification_id
     */
    public EquipTypeSpecDto(int id, int type_id, int specification_id) {
        this.id = id;
        this.type_id = type_id;
        this.specification_id = specification_id;
    }

    /**
     * class main constructor
     */
    public EquipTypeSpecDto() {
        super();
    }

    /**
     * type is attribute setter
     * @param type_id int
     */
    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    /**
     * type id attribute getter
     * @return type id int
     */
    public int getType_id() {
        return type_id;
    }

    /**
     * specification id attribute setter
     * @param specification_id int
     */
    public void setSpecification_id(int specification_id) {
        this.specification_id = specification_id;
    }

    /**
     * specification id attribute getter
     * @return specification id int
     */
    public int getSpecification_id() {
        return specification_id;
    }

    /**
     * relation id attribute setter
     * @param id int
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * realation id attribute getter
     * @return id int
     */
    public int getId() {
        return id;
    }
}
