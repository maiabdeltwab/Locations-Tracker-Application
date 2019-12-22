package com.fym.lta.DTO;

import java.util.List;

/**class to define department attributes with setters, getters and constructors
 */
public class DepartmentDto {

    /* department id */
    private int id;

    /* department code */
    private String code;

    /* department name */
    private String name;

    /* department faculty */
    private FacultyDto faculty;

    /* list of staff members of department */
    private List<StaffDto> depart_staff;

    /* list of department stages */
    private List<StageDto> Stages;

    /* courses of department */
    private CourseDepartDto courses;

    /* department search key */
    private String search;

    /* list of buildings for department */
    private List<BuildingDto> buildings;


    /**
     * class constructor from name
     * @param name string
     */
    public DepartmentDto(String name) {
        this.name = name;
    }

    /**
     * class constructor from id attribute
     * @param id int
     */
    public DepartmentDto(int id) {
        this.id = id;
    }

    /**
     * seatch attribute setter
     * @param search string
     */
    public void setSearch(String search) {
        this.search = search;
    }

    /**
     * search attribute getter
     * @return seatch string
     */
    public String getSearch() {
        return search;
    }

    /**
     * faculty attribute setter
     * @param faculty
     */
    public void setFaculty(FacultyDto faculty) {
        this.faculty = faculty;
    }

    /**
     * faculty attribute getter
     * @return faculty
     */
    public FacultyDto getFaculty() {
        return faculty;
    }

    /**
     * building of department attribute setter
     * @param building
     */
    public void setBuilding(DepartBuildingDto building) {
        this.building = building;
    }

    /**
     * building of department attribute getter
     * @return building
     */
    public DepartBuildingDto getBuilding() {
        return building;
    }

    /**
     * list of department staff members setter
     * @param depart_staff list
     */
    public void setDepart_staff(List<StaffDto> depart_staff) {
        this.depart_staff = depart_staff;
    }

    /**
     * list of department staff members getter
     * @return list of staff members
     */
    public List<StaffDto> getDepart_staff() {
        return depart_staff;
    }

    /**
     * list of department stages setter
     * @param Stages
     */
    public void setStages(List<StageDto> Stages) {
        this.Stages = Stages;
    }

    /**
     *list of deoartment stages attribute getter
     * @return list of stages
     */
    public List<StageDto> getStages() {
        return Stages;
    }

    /**
     * course of department attribute setter
     * @param courses
     */
    public void setCourses(CourseDepartDto courses) {
        this.courses = courses;
    }

    /**
     * courses of department attribute getter
     * @return courses
     */
    public CourseDepartDto getCourses() {
        return courses;
    }

    /**
     * class main constructor
     */
    public DepartmentDto() {
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
     * @return code string
     */
    public String getCode() {
        return code;
    }

    /**
     * constructor of code, name and id
     * @param code
     * @param name
     * @param id
     */
    public DepartmentDto(String code, String name, int id) {
        this.code = code;
        this.name = name;
        this.id = id;
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
     * list of buildings attribute setter
     * @param buildings list
     */
    public void setBuildings(List<BuildingDto> buildings) {
        this.buildings = buildings;
    }

    /**
     * list of buildings attribute getter
     * @return buildings list
     */
    public List<BuildingDto> getBuildings() {
        return buildings;
    }


    private StageDto stage;
    private DepartBuildingDto building;

    public void setStage(StageDto stage) {
        this.stage = stage;
    }

    public StageDto getStage() {
        return stage;
    }

}
