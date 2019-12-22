package com.fym.lta.DTO;

/**class to define screen attributes with setters, getters and constructors
 */
public class ScreenDto {

    /* screen id */
    private int id;

    /* screen description */
    private String descripltion;

    /* screen name */
    private String name;

    private RoleScreenDto Role;


    /**
     * class main constructor
     */
    public ScreenDto() {
        super();
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
     * description attribute setter
     * @param descripltion string
     */
    public void setDescripltion(String descripltion) {
        this.descripltion = descripltion;
    }

    /**
     * description attribute getter
     * @return string
     */
    public String getDescripltion() {
        return descripltion;
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
}
