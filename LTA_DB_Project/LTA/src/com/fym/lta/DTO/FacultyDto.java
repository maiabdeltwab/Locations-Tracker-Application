package com.fym.lta.DTO;

import java.util.List;

/**class to define faculty attributes with setters, getters and constructors
 */
public class FacultyDto {
    /* faculty description */
    private String description;

    /* faculty name */
    private String name;

    /* faculty number of stages */
    private int stages;

    /* faculty list of departments */
    private List<DepartmentDto> Departments;
    
    /* serach key */
    private String search;
    
    
    private String term;


    /**
     * departments attribute setter
     * @param Departments list
     */
    public void setDepartments(List<DepartmentDto> Departments) {
        this.Departments = Departments;
    }

    /**
     * departments attribute getter
     * @return list of departments
     */
    public List<DepartmentDto> getDepartments() {
        return Departments;
    }

    /**
     * class main constructor
     */
    public FacultyDto() {
        super();
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
     *name attribute setter
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
     * number of stages attribute setter
     * @param stages
     */
    public void setStages(int stages) {
        this.stages = stages;
    }

    /**
     * number of stages attribute getter
     * @return int
     */
    public int getStages() {
        return stages;
    }

    /**
     * search key setter
     * @param search string
     */
    public void setSearch(String search) {
        this.search = search;
    }

    /**
     * search key getter
     * @return search String
     */
    public String getSearch() {
        return search;
    }

    /**
     * term attribute setter
     * @param term string
     */
    public void setTerm(String term) {
        this.term = term;
    }
 
    /**
     * term attribute getter
     * @return term string
     */
    public String getTerm() {
        return term;
    }
}
